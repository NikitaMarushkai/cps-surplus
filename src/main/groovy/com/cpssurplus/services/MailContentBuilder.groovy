package com.cpssurplus.services

import com.cpssurplus.domains.entities.Order
import com.cpssurplus.domains.forms.ContactForm
import com.cpssurplus.domains.forms.OrderForm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

/**
 * Created by unlim_000 on 24.02.2017.
 */

@Service
class MailContentBuilder {

    private TemplateEngine templateEngine

    @Autowired
    MailContentBuilder(TemplateEngine templateEngine){
        this.templateEngine = templateEngine
    }

    String buildContactRequest(ContactForm contactForm){
        Context context = new Context()
        context.setVariable("email", contactForm.email)
        context.setVariable("subject", contactForm.subject)
        context.setVariable("name", contactForm.name)
        context.setVariable("message", contactForm.message)
        return templateEngine.process("mail/mailTemplate", context)
    }

    String buildNewOrderRequest(Order order, OrderForm orderForm) {
        Context context = new Context()
        context.setVariable("orderId", order.id)
        context.setVariable("email", orderForm.email)
        context.setVariable("phone", orderForm.phone)
        context.setVariable("name", orderForm.name)
        context.setVariable("partNumber", order.partNumber)
        context.setVariable("qty", order.qty)
        context.setVariable("shippingAddress", orderForm.shippingAddress)
        context.setVariable("comment", order.comment)
        return templateEngine.process("mail/newOrderMailTemplate", context)
    }

    String buildNewCustomerOrderConfirmation(Order order, OrderForm orderForm) {
        Context context = new Context()
        context.setVariable("name", orderForm.name)
        context.setVariable("order", order.id)
        context.setVariable("partNr", order.partNumber)
        context.setVariable("qty", order.qty)
        context.setVariable("shippingAddress", orderForm.shippingAddress)
        return templateEngine.process("mail/customerOrderConfirmationTemplate", context)
    }
}
