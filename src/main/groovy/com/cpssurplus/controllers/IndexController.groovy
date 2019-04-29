package com.cpssurplus.controllers

import com.cpssurplus.domains.forms.ContactForm
import com.cpssurplus.services.MailClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class IndexController {

    @Autowired
    private MailClient mailClient

    @GetMapping("/")
    String index(Model model) {
        model.addAttribute('contactForm', new ContactForm())
        return "index"
    }

    @PostMapping("/mail")
    String processMailForm(Model model, @ModelAttribute ContactForm contactForm) {
        mailClient.prepareAndSend(contactForm);
        model.addAttribute("contactform", new ContactForm());
        return "redirect:/";
    }
}
