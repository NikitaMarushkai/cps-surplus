package com.cpssurplus.controllers

import com.cpssurplus.domains.entities.Customer
import com.cpssurplus.domains.entities.Order
import com.cpssurplus.enums.CountryCode
import com.cpssurplus.enums.OrderStatus
import com.cpssurplus.services.CustomersService
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

    @Autowired
    CustomersService customersService

    @GetMapping("/upload")
    String upload(Model model, @RequestParam(required = false) uploadCount) {
        model.addAttribute("countries", CountryCode.values())
        if (uploadCount) {
            model.addAttribute('itemsCount', uploadCount)
        }
        return 'admin/upload'
    }

    @GetMapping("/orders")
    String orders(Model model) {
        List<Order> orders = orderService.getOrders()
        model.addAttribute("orders", orders)
        return 'admin/dashboard'
    }

    @GetMapping("/customers")
    String customers(Model model) {
        List<Customer> customers = customersService.getCustomers()
        model.addAttribute('customers', customers)
        return 'admin/customers'
    }

    @PostMapping("/uploadPrice")
    String readExcel(@RequestParam("excelFile") MultipartFile excelFile, CountryCode country) {
        def uploaded = uploadService.savePriceList(excelFile, country)
        return "redirect:/admin/upload?uploadCount=${uploaded}"
    }

    @GetMapping("/changeOrderStatus")
    String changeOrderStatus(@RequestParam Integer id, @RequestParam OrderStatus newStatus) {
        Order order = orderService.getOrder(id)
        orderService.processOrder(order, newStatus)
        return "redirect:/admin/orders"
    }
}
