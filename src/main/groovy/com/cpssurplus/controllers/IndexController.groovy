package com.cpssurplus.controllers

import com.cpssurplus.domains.forms.ContactForm
import com.cpssurplus.domains.forms.SearchForm
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexController {

    @GetMapping("/")
    String index(Model model) {
        model.addAttribute('contactForm', new ContactForm())
        model.addAttribute('searchForm', new SearchForm())
        return "index"
    }
}
