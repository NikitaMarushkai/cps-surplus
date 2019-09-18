package com.cpssurplus.repositories

import com.cpssurplus.domains.entities.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrdersRepository extends JpaRepository<Order, Integer> {

}