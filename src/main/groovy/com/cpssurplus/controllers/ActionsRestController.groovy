package com.cpssurplus.controllers

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
        try {
            orderService.createOrder(orderForm)
        } catch (Exception e) {
            e.printStackTrace()
            return 'Error occured on order processing. Please contact us to order directly.'
        }

        return 'OK'
    }

    @PostMapping("/sendMail")
    String processMailForm(@ModelAttribute ContactForm contactForm) {
        try {
            mailClient.prepareAndSend(contactForm);
        } catch(Exception e) {
            switch (e) {
                case { it instanceof MailException }:
                    return "There is something wromg with our mail client. Please contact us using contacts section."
                    break
                default:
                    return "Unknown internal error on processing your request. Please contact us using contacts section."
            }
        }
        return "OK";
    }

}
