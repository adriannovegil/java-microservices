package com.devcircus.java.microservices.mes.orderservice.invoice;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface InvoiceRepository extends PagingAndSortingRepository<Invoice, String> {
}
