package com.cpssurplus.services

import com.cpssurplus.domains.entities.CatalogueItem
import com.cpssurplus.domains.entities.Customer
import com.cpssurplus.domains.entities.Order
import com.cpssurplus.domains.forms.OrderForm
import com.cpssurplus.domains.forms.SearchForm
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

    Order getOrder(Integer id) {
        ordersRepository.getOne(id)
    }

    List<Order> getOrders(List<Integer> orderIds = []) {
        orderIds ? ordersRepository.findAllById(orderIds) : ordersRepository.findAll()
    }

    @Transactional
    Order createOrder(OrderForm orderForm) {
        Customer orderOwner = getOrRegisterCustomer(orderForm.email, orderForm.phone, orderForm.name)
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
                comment: orderForm.comment
        )
        ordersRepository.save(order)
        //TODO: Increment customer's orders count here or after processing
    }

    private Customer getOrRegisterCustomer(String email, String phone, String name) {
        Customer customer = customersRepository.findByEmail(email)
        if (!customer) {
            customer = new Customer(
                    email: email,
                    phone: phone,
                    name: name
            )
            customersRepository.save(customer)
        }

        customer
    }

}
