package com.ntech.dispatchloadbalancer.repo;

import com.ntech.dispatchloadbalancer.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
}
