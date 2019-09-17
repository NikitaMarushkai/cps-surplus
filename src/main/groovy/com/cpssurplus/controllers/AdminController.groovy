package com.cpssurplus.controllers

import com.cpssurplus.enums.CountryCode
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

    @GetMapping
    String index(Model model) {
        model.addAttribute("countries", CountryCode.values())
        return 'admin'
    }

    @PostMapping("/import")
    String readExcel(@RequestParam("file") MultipartFile excelFile, CountryCode country) {
        uploadService.savePriceList(excelFile, country)
        //todo: return number of saved items
        return "redirect:/admin"
    }
}
