package com.devcircus.java.microservices.mesp.paymentweb.payment.repository;

import demo.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findPaymentByOrderId(@Param("orderId") Long orderId);
}
