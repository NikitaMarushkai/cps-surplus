package com.cpssurplus.repositories

import com.cpssurplus.domains.entities.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomersRepository extends JpaRepository<Customer, Integer> {

    Customer findByEmail(String email)

}