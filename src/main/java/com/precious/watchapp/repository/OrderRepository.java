package com.precious.watchapp.repository;

import com.precious.watchapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByEmailAndOtp(String email, String otp);
}
