package com.cpssurplus.services

import com.cpssurplus.domains.entities.CatalogueItem
import com.cpssurplus.enums.CountryCode
import com.cpssurplus.repositories.CatalogueItemRepository
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

import javax.transaction.Transactional

@Service
class UploadService {

    @Autowired
    CatalogueItemRepository catalogueItemRepository

    @Transactional
    Integer savePriceList(MultipartFile excelFile, CountryCode location) {
        HSSFWorkbook workbook = new HSSFWorkbook(excelFile.getInputStream())
        HSSFSheet worksheet = workbook.getSheetAt(0)

        List<CatalogueItem> itemsList = []

        switch (location) {
            case CountryCode.UZ:
                itemsList = fillUzbekistanStock(worksheet)
                break
            case CountryCode.BE:
                itemsList = fillBelgiumStock(worksheet)
        }

        catalogueItemRepository.saveAll(itemsList)

        itemsList.size()
    }

    private static List<CatalogueItem> fillBelgiumStock(HSSFSheet worksheet) {
        List<CatalogueItem> itemsList = []
        def rowNums = worksheet.getPhysicalNumberOfRows() - 9
        rowNums.times {
            def index = it + 10
            HSSFRow sheetRow = worksheet.getRow(index)
            CatalogueItem catalogueRow = new CatalogueItem(
                    partNumber: sheetRow.getCell(1).getStringCellValue(),
                    description: sheetRow.getCell(3).getStringCellValue(),
                    price: sheetRow.getCell(4).getNumericCellValue() as BigDecimal,
                    qty: sheetRow.getCell(5).getNumericCellValue() as Integer,
                    weight: sheetRow.getCell(7).getNumericCellValue() as BigDecimal,
                    dimensions: sheetRow.getCell(9).getStringCellValue(),
                    note: sheetRow.getCell(8).getStringCellValue(),
                    location: CountryCode.BE
            )

            itemsList += catalogueRow
        }
        itemsList
    }

    private static List<CatalogueItem> fillUzbekistanStock(HSSFSheet worksheet) {
        List<CatalogueItem> itemsList = []
        def rowNums = worksheet.getPhysicalNumberOfRows() - 4
        rowNums.times {
            def index = it + 5
            HSSFRow sheetRow = worksheet.getRow(index)
            CatalogueItem catalogueRow = new CatalogueItem(
                    partNumber: sheetRow.getCell(7).getStringCellValue(),
                    description: sheetRow.getCell(8).getStringCellValue(),
                    price: sheetRow.getCell(2).getNumericCellValue() as BigDecimal,
                    qty: sheetRow.getCell(11).getNumericCellValue() as Integer,
                    weight: sheetRow.getCell(10).getNumericCellValue() as BigDecimal,
                    dimensions: null,
                    note: null,
                    location: CountryCode.UZ
            )

            itemsList += catalogueRow
        }
        itemsList
    }
}
