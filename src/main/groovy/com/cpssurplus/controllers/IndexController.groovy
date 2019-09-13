package com.cpssurplus.controllers

import com.cpssurplus.domains.forms.ContactForm
import com.cpssurplus.domains.forms.SearchForm
import com.cpssurplus.services.MailClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class IndexController {

    @Autowired
    private MailClient mailClient

    @GetMapping("/")
    String index(Model model) {
        model.addAttribute('contactForm', new ContactForm())
        model.addAttribute('searchForm', new SearchForm())
        return "index"
    }

    @PostMapping("/sendMail")
    String processMailForm(@ModelAttribute ContactForm contactForm) {
        mailClient.prepareAndSend(contactForm);
        return "redirect:/";
    }
}
