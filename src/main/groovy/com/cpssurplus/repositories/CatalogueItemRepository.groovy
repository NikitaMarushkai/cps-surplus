package com.cpssurplus.repositories

import com.cpssurplus.domains.entities.CatalogueItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CatalogueItemRepository extends JpaRepository<CatalogueItem, Integer> {

}