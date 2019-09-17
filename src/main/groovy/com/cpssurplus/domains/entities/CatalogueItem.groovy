package com.cpssurplus.domains.entities

import com.cpssurplus.enums.CountryCode

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "catalogue_item")
class CatalogueItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id

    @NotNull
    @Column(name = "part_number", nullable = false)
    String partNumber

    @Column(name = "description")
    String description

    @NotNull
    @Column(name = "price", nullable = false)
    BigDecimal price

    @NotNull
    @Column(name = "qty", nullable = false)
    Integer qty

    @NotNull
    @Column(name = "weight", nullable = false)
    BigDecimal weight

    @Column(name = "dimensions")
    String dimensions

    @Column(name = "note")
    String note

    @Column(name = "location")
    CountryCode location
}
