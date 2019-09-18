package com.cpssurplus.domains.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "customer")
class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id

    @NotNull
    @Size(max = 512)
    @Column(name = 'email', nullable = false, unique = true)
    String email

    @Size(max = 128)
    @Column(name = 'phone')
    String phone

    @Min(0l)
    @Column(name = 'orders_count')
    Integer ordersCount = 0
}
