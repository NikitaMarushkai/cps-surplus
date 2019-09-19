package com.cpssurplus.controllers

import com.cpssurplus.domains.entities.Order
import com.cpssurplus.domains.forms.ContactForm
import com.cpssurplus.domains.forms.OrderForm
import com.cpssurplus.services.MailClient
import com.cpssurplus.services.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailException
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/async")
class ActionsRestController {

    @Autowired
    OrderService orderService

    @Autowired
    private MailClient mailClient

    @PostMapping("/order")
    String order(@ModelAttribute OrderForm orderForm) {
        Order order
        try {
            order = orderService.createOrder(orderForm)
        } catch (Exception e) {
            e.printStackTrace()
            return 'Error occured on order processing. Please contact us to order directly.'
        }

        try {
            mailClient.sendOrderNotification(orderForm, order)
        } catch(Exception e) {
            e.printStackTrace()
        }

        try {
            mailClient.sendCustomerOrderNotification(orderForm, order)
        } catch(Exception e) {
            e.printStackTrace()
            return "Your order have been processed, but we were unable to send you a confirmation email. Don't worry, " +
                    "we'll get in touch with you soon."
        }

        return 'OK'
    }

    @PostMapping("/sendMail")
    String processMailForm(@ModelAttribute ContactForm contactForm) {
        try {
            mailClient.prepareAndSend(contactForm);
        } catch(MailException mailException) {
            mailException.printStackTrace()
            return "There is something wromg with our mail client. Please contact us using contacts section."
        } catch (Exception e) {
            e.printStackTrace()
            return "Unknown internal error on processing your request. Please contact us using contacts section."
        }
        return "OK";
    }

}
