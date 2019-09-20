package com.cpssurplus.domains.entities

import com.cpssurplus.enums.OrderStatus
import org.joda.time.DateTime

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "orders")
class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id

    @NotNull
    @Column(name = 'date_created')
    DateTime dateCreated = DateTime.now()

    @ManyToOne
    @JoinColumn(name = 'part_id', referencedColumnName = 'id', nullable = false)
    CatalogueItem part

    @NotNull
    @Size(max = 512)
    @Column(name = 'part_number', nullable = false)
    String partNumber

    @NotNull
    @Min(0l)
    @Column(name = 'qty', nullable = false)
    Integer qty

    @ManyToOne
    @JoinColumn(name = 'customer_id', referencedColumnName = 'id', nullable = false)
    Customer customer

    @Size(max = 2048)
    @Column(name = 'comment')
    String comment

    @NotNull
    @Column(name = 'status')
    @Enumerated(EnumType.STRING)
    OrderStatus status = OrderStatus.NEW

}
