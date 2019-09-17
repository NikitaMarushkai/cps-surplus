package com.cpssurplus.controllers

import com.cpssurplus.domains.forms.SearchForm
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/parts")
class SearchController {

    @GetMapping
    String index() {
        return 'catalogue'
    }

    @PostMapping("/search")
    String search(@ModelAttribute SearchForm searchForm) {
        return "redirect:/"
    }
}
