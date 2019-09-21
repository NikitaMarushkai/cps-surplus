package com.cpssurplus.services

import com.cpssurplus.domains.entities.Customer
import com.cpssurplus.repositories.CustomersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CustomersService {

    @Autowired
    CustomersRepository customersRepository

    List<Customer> getCustomers(Integer[] ids = []) {
        if (ids) {
            customersRepository.findAllById(ids)
        } else {
            customersRepository.findAll()
        }
    }

}
