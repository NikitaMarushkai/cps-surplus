package com.cpssurplus.services

import com.cpssurplus.domains.entities.CatalogueItem
import com.cpssurplus.domains.entities.Customer
import com.cpssurplus.domains.entities.Order
import com.cpssurplus.domains.forms.OrderForm
import com.cpssurplus.domains.forms.SearchForm
import com.cpssurplus.enums.OrderStatus
import com.cpssurplus.repositories.CatalogueItemRepository
import com.cpssurplus.repositories.CustomersRepository
import com.cpssurplus.repositories.OrdersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.transaction.Transactional

@Service
class OrderService {

    @Autowired
    CustomersRepository customersRepository
    @Autowired
    OrdersRepository ordersRepository
    @Autowired
    CatalogueService catalogueService
    @Autowired
    CatalogueItemRepository catalogueItemRepository

    Order getOrder(Integer id) {
        ordersRepository.getOne(id)
    }

    List<Order> getOrders(List<Integer> orderIds = []) {
        orderIds ? ordersRepository.findAllByIdOrderByDateCreatedDesc(orderIds) :
                ordersRepository.findAllByOrderByDateCreatedDesc()
    }

    @Transactional
    void processOrder(Order order, OrderStatus newStatus) {
        Customer orderCustomer = order.customer
        order.status = newStatus
        if (newStatus == OrderStatus.PAYED) {
            orderCustomer.ordersCount += 1
            customersRepository.save(orderCustomer)
        } else if (newStatus == OrderStatus.REJECTED) {
            CatalogueItem orderItem = order.part
            orderItem.qty += order.qty
            catalogueItemRepository.save(orderItem)
            ordersRepository.delete(order)
            return
        }

        ordersRepository.save(order)
    }

    @Transactional
    Order createOrder(OrderForm orderForm) {
        Customer orderOwner = getOrRegisterCustomer(orderForm)
        CatalogueItem item = catalogueService.getItem(orderForm.partId)
        if (!orderOwner) {
            return null
        }
        if (orderForm.partNumber != item.partNumber) {
            item = catalogueService.searchItems(new SearchForm(
                    partNumber: orderForm.partNumber
            )).first()
        }
        Order order = new Order(
                part: item,
                partNumber: item.partNumber,
                qty: orderForm.qty,
                customer: orderOwner,
                shippingAddress: orderForm.shippingAddress,
                comment: orderForm.comment
        )
        item.qty -= orderForm.qty
        catalogueItemRepository.save(item)
        ordersRepository.save(order)
    }

    private Customer getOrRegisterCustomer(OrderForm orderForm) {
        Customer customer = customersRepository.findByEmail(orderForm.email)
        if (!customer) {
            customer = new Customer(
                    email: orderForm.email,
                    phone: orderForm.phone,
                    name: orderForm.name
            )
        }

        customer.subscribeToNewsletter = customer.subscribeToNewsletter ? true : orderForm.subscribe
        customersRepository.save(customer)

        customer
    }

}
