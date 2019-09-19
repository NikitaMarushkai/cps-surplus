package com.cpssurplus.controllers

import com.cpssurplus.domains.entities.Order
import com.cpssurplus.enums.CountryCode
import com.cpssurplus.services.OrderService
import com.cpssurplus.services.UploadService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile

@Controller
@RequestMapping("/admin")
class AdminController {

    @Autowired
    UploadService uploadService

    @Autowired
    OrderService orderService

    @GetMapping
    String index(Model model) {
        model.addAttribute("countries", CountryCode.values())
        return 'admin/admin'
    }

    @GetMapping("/orders")
    String orders(Model model) {
        List<Order> orders = orderService.getOrders()
        model.addAttribute("orders", orders)
        return 'admin/dashboard'
    }

    @PostMapping("/import")
    String readExcel(@RequestParam("excelFile") MultipartFile excelFile, CountryCode country) {
        uploadService.savePriceList(excelFile, country)
        //todo: return number of saved items
        return "redirect:/admin"
    }
}
