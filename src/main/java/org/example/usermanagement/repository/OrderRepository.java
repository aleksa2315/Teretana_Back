package org.example.usermanagement.repository;

import org.example.usermanagement.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCreatedById(Long userId);
    Page<Order> findAllByCreatedById(Long userId, Pageable pageable);
    Page<Order> findAll(Pageable pageable);
}
