package com.cpssurplus.controllers

import com.cpssurplus.domains.forms.SearchForm
import com.cpssurplus.services.CatalogueService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/parts")
class SearchController {

    @Autowired
    CatalogueService catalogueService

    @GetMapping
    String index(Model model) {
        model.addAttribute('items', [])
        model.addAttribute('searchForm', new SearchForm())
        return 'catalogue'
    }

    @PostMapping("/search")
    String search(Model model, @ModelAttribute SearchForm searchForm) {
        def items = catalogueService.searchItems(searchForm)
        model.addAttribute('items', items)
        model.addAttribute('searchForm', new SearchForm())
        return 'catalogue'
    }
}
