package com.cpssurplus.services

import com.cpssurplus.domains.entities.CatalogueItem
import com.cpssurplus.enums.CountryCode
import com.cpssurplus.repositories.CatalogueItemRepository
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.DataFormatter
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
        DataFormatter formatter = new DataFormatter()

        List<CatalogueItem> itemsList = []

        switch (location) {
            case CountryCode.UZ:
                itemsList = fillUzbekistanStock(worksheet, formatter)
                break
            case CountryCode.BE:
                itemsList = fillBelgiumStock(worksheet, formatter)
                break
            case CountryCode.AE:
                itemsList = fillDubaiStock(worksheet, formatter)
        }

        catalogueItemRepository.saveAll(itemsList)

        itemsList.size()
    }

    private static List<CatalogueItem> fillDubaiStock(HSSFSheet worksheet, DataFormatter formatter) {
        List<CatalogueItem> itemsList = []
        def rowNums = worksheet.getPhysicalNumberOfRows() - 2
        rowNums.times {
            def index = it + 3
            HSSFRow sheetRow = worksheet.getRow(index)
            if (formatter.formatCellValue(sheetRow?.getCell(4))) {
                CatalogueItem catalogueRow = new CatalogueItem(
                        partNumber: formatter.formatCellValue(sheetRow.getCell(4)),
                        description: sheetRow.getCell(5)?.getStringCellValue(),
                        price: sheetRow.getCell(7).getNumericCellValue() as BigDecimal,
                        qty: sheetRow.getCell(6).getNumericCellValue() as Integer,
                        location: CountryCode.AE.toString()
                )
                itemsList += catalogueRow
            }
        }
        itemsList
    }

    private static List<CatalogueItem> fillBelgiumStock(HSSFSheet worksheet, DataFormatter formatter) {
        List<CatalogueItem> itemsList = []
        def rowNums = worksheet.getPhysicalNumberOfRows() - 9
        rowNums.times {
            def index = it + 10
            HSSFRow sheetRow = worksheet.getRow(index)
            if (formatter.formatCellValue(sheetRow.getCell(1))) {
                CatalogueItem catalogueRow = new CatalogueItem(
                        partNumber: formatter.formatCellValue(sheetRow.getCell(1)),
                        description: sheetRow.getCell(3)?.getStringCellValue(),
                        price: sheetRow.getCell(4).getNumericCellValue() as BigDecimal,
                        qty: sheetRow.getCell(5).getNumericCellValue() as Integer,
                        weight: sheetRow.getCell(7)?.getNumericCellValue() as BigDecimal,
                        dimensions: sheetRow.getCell(9)?.getStringCellValue(),
                        note: sheetRow.getCell(8)?.getStringCellValue(),
                        location: CountryCode.BE.toString()
                )

                itemsList += catalogueRow
            }
        }
        itemsList
    }

    private static List<CatalogueItem> fillUzbekistanStock(HSSFSheet worksheet, DataFormatter formatter) {
        List<CatalogueItem> itemsList = []
        def rowNums = worksheet.getPhysicalNumberOfRows() - 4
        rowNums.times {
            def index = it + 5
            HSSFRow sheetRow = worksheet.getRow(index)
            if (formatter.formatCellValue(sheetRow.getCell(7))) {
                CatalogueItem catalogueRow = new CatalogueItem(
                        partNumber: formatter.formatCellValue(sheetRow.getCell(7)),
                        description: sheetRow.getCell(8)?.getStringCellValue(),
                        price: sheetRow.getCell(9).getNumericCellValue() as BigDecimal,
                        qty: sheetRow.getCell(11).getNumericCellValue() as Integer,
                        weight: sheetRow.getCell(10)?.getNumericCellValue() as BigDecimal,
                        dimensions: null,
                        note: null,
                        location: CountryCode.UZ.toString()
                )

                itemsList += catalogueRow
            }
        }
        itemsList
    }
}
