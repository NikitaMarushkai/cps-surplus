package com.cpssurplus.repositories

import com.cpssurplus.domains.entities.CatalogueItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CatalogueItemRepository extends JpaRepository<CatalogueItem, Integer> {

    //TODO
    @Query(value = "select * from catalogue_item where replace(part_number, '-', '') = ?1 or description ilike '%?2%'", nativeQuery = true)
    List<CatalogueItem> findByPartNumberOrDescription(String partNumber, String description)

}