package com.cpssurplus.controllers

import com.cpssurplus.domains.entities.Order
import com.cpssurplus.domains.forms.OrderForm
import com.cpssurplus.domains.forms.SearchForm
import com.cpssurplus.services.CatalogueService
import com.cpssurplus.services.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/parts")
class PartsController {

    @Autowired
    CatalogueService catalogueService

    @Autowired
    OrderService orderService

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

    @GetMapping("/detailed")
    String detailed(Model model, @RequestParam Integer id, @RequestParam Integer orderId) {
        model.addAttribute('order', orderId ? orderService.getOrder(orderId) : null)
        model.addAttribute('orderForm', new OrderForm())
        model.addAttribute("item", catalogueService.getItem(id))
        //TODO: display 'order successfull' in detailed view
        return 'detailed'
    }

    @PostMapping("/order")
    String order(Model model, @ModelAttribute OrderForm orderForm) {
        Order order = null
        try {
            order = orderService.createOrder(orderForm)
        } catch (Exception e) {

        }
        return "redirect:/detailed?id=${orderForm.partId}&orderId=${order?.id}"
    }
}
