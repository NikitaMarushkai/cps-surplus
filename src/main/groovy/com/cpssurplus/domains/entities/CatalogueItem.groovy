package com.cpssurplus.domains.entities

import com.cpssurplus.enums.CountryCode

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "catalogue_item")
class CatalogueItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id

    @NotNull
    @Size(max = 512)
    @Column(name = "part_number", nullable = false)
    String partNumber

    @Size(max = 512)
    @Column(name = "description")
    String description

    @NotNull
    @Min(0l)
    @Column(name = "price", nullable = false)
    BigDecimal price

    @NotNull
    @Min(0l)
    @Column(name = "qty", nullable = false)
    Integer qty

    @Column(name = "weight", nullable = false)
    @Min(0l)
    BigDecimal weight

    @Size(max = 512)
    @Column(name = "dimensions")
    String dimensions

    @Size(max = 512)
    @Column(name = "note")
    String note

    @NotNull
    @Column(name = "location", nullable = false)
    @Enumerated(EnumType.STRING)
    CountryCode location
}
