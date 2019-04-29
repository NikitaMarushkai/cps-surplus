package com.cpssurplus.services

import com.cpssurplus.domains.forms.ContactForm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.mail.javamail.MimeMessagePreparator
import org.springframework.stereotype.Service

@Service
class MailClient {

    private JavaMailSender mailSender;
    private MailContentBuilder contentBuilder;

    @Autowired
    MailClient(JavaMailSender mailSender, MailContentBuilder contentBuilder){
        this.mailSender = mailSender;
        this.contentBuilder = contentBuilder;
    }

    void prepareAndSend(ContactForm contactForm){
        MimeMessagePreparator messagePreparator = {
            MimeMessageHelper messageHelper = new MimeMessageHelper(it);
            messageHelper.setFrom(contactForm.email);
            messageHelper.setTo("unlimited54321@mail.ru");
            messageHelper.setSubject("CNH-surplus request " + contactForm.subject);
            String content = contentBuilder.build(contactForm);
            messageHelper.setText(content, true);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e){
            e.printStackTrace();
        }
    }
}
