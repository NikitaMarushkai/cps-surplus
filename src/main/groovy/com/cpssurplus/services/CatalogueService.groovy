package com.cpssurplus.services

import com.cpssurplus.domains.entities.CatalogueItem
import com.cpssurplus.domains.forms.SearchForm
import com.cpssurplus.repositories.CatalogueItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CatalogueService {

    @Autowired
    CatalogueItemRepository catalogueItemRepository

    List<CatalogueItem> searchItems(SearchForm searchForm) {
        catalogueItemRepository.findByPartNumberOrDescription(searchForm.partNumber.replaceAll('-', ''),
                searchForm.partName)
    }

}
