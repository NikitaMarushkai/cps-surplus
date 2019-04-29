package com.cpssurplus.services;

import com.cpssurplus.domains.forms.ContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * Created by unlim_000 on 24.02.2017.
 */

@Service
class MailContentBuilder {

    private TemplateEngine templateEngine;

    @Autowired
    MailContentBuilder(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    public String build(ContactForm contactForm){
        Context context = new Context();
        context.setVariable("email", contactForm.email);
        context.setVariable("subject", contactForm.subject);
        context.setVariable("name", contactForm.name);
        context.setVariable("message", contactForm.message);
        return templateEngine.process("mailTemplate", context);
    }
}
