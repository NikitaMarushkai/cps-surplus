package com.cpssurplus.services

import com.cpssurplus.domains.entities.Order
import com.cpssurplus.domains.forms.ContactForm
import com.cpssurplus.domains.forms.OrderForm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.mail.javamail.MimeMessagePreparator
import org.springframework.stereotype.Service

@Service
class MailClient {

    private JavaMailSender mailSender
    private MailContentBuilder contentBuilder

    @Autowired
    MailClient(JavaMailSender mailSender, MailContentBuilder contentBuilder){
        this.mailSender = mailSender
        this.contentBuilder = contentBuilder
    }

    void prepareAndSend(ContactForm contactForm) throws MailException {
        MimeMessagePreparator messagePreparator = {
            MimeMessageHelper messageHelper = new MimeMessageHelper(it)
            messageHelper.setFrom(contactForm.email)
            messageHelper.setTo("francis@parts-on-line.be")
            messageHelper.setSubject("CNH-surplus request " + contactForm.subject)
            String content = contentBuilder.buildContactRequest(contactForm)
            messageHelper.setText(content, true)
        }

        mailSender.send(messagePreparator)
    }

    void sendOrderNotification(OrderForm orderForm, Order order) throws MailException {
        MimeMessagePreparator messagePreparator = {
            MimeMessageHelper messageHelper = new MimeMessageHelper(it)
            messageHelper.setFrom(orderForm.email)
            messageHelper.setTo("francis@parts-on-line.be")
            messageHelper.setSubject("CNH-surplus new order #" + order.id)
            String content = contentBuilder.buildNewOrderRequest(order, orderForm)
            messageHelper.setText(content, true)
        }

        mailSender.send(messagePreparator)
    }

    void sendCustomerOrderNotification(OrderForm orderForm, Order order) throws MailException {
        MimeMessagePreparator messagePreparator = {
            MimeMessageHelper messageHelper = new MimeMessageHelper(it)
            messageHelper.setFrom("francis@parts-on-line.be")
            messageHelper.setTo(orderForm.email)
            messageHelper.setSubject("Order confirmation #" + order.id)
            String content = contentBuilder.buildNewCustomerOrderConfirmation(order, orderForm)
            messageHelper.setText(content, true)
        }

        mailSender.send(messagePreparator)
    }
}
