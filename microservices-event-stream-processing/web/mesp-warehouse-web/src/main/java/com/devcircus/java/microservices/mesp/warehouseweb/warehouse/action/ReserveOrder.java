package com.devcircus.java.microservices.mesp.warehouseweb.warehouse.action;

import com.devcircus.java.microservices.mesp.warehouseweb.reservation.domain.Reservation;
import com.devcircus.java.microservices.mesp.warehouseweb.reservation.domain.ReservationService;
import com.devcircus.java.microservices.mesp.warehouseweb.reservation.event.ReservationEvent;
import com.devcircus.java.microservices.mesp.warehouseweb.reservation.event.ReservationEventType;
import com.devcircus.java.microservices.mesp.warehouseweb.warehouse.domain.Warehouse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional
public class ReserveOrder extends Action<Warehouse> {

    private final Logger log = Logger.getLogger(ReserveOrder.class);
    private final ReservationService reservationService;

    public ReserveOrder(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public void apply(Warehouse warehouse, Order order) {
        // Create reservations for each order item
        List<Reservation> reservations = order.getLineItems().stream()
                .map(item -> IntStream.rangeClosed(1, item.getQuantity())
                .mapToObj(a -> new Reservation(item.getProductId(), order.getIdentity(), warehouse)))
                .flatMap(a -> a)
                .collect(Collectors.toList());

        // Save the reservations
        reservations = reservationService.create(reservations);

        // Trigger reservation requests for each order item
        reservations.forEach(r -> {
            ReservationEvent event = new ReservationEvent(ReservationEventType.RESERVATION_REQUESTED, r);
            event.add(order.getLink("self").withRel("order"));
            r.sendAsyncEvent(event);
        });
    }
}
