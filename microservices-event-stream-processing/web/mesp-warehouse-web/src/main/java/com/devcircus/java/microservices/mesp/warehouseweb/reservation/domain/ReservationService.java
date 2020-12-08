package com.devcircus.java.microservices.mesp.warehouseweb.reservation.domain;

import com.devcircus.java.microservices.mesp.warehouseweb.reservation.event.ReservationEvent;
import com.devcircus.java.microservices.mesp.warehouseweb.reservation.event.ReservationEventService;
import com.devcircus.java.microservices.mesp.warehouseweb.reservation.event.ReservationEventType;
import com.devcircus.java.microservices.mesp.warehouseweb.reservation.repository.ReservationRepository;
import org.springframework.util.Assert;

import java.util.List;

@org.springframework.stereotype.Service
public class ReservationService extends Service<Reservation, Long> {

    private final ReservationRepository reservationRepository;
    private final ReservationEventService reservationEventService;

    public ReservationService(ReservationRepository reservationRepository, ReservationEventService reservationEventService) {
        this.reservationRepository = reservationRepository;
        this.reservationEventService = reservationEventService;
    }

    public Reservation create(Reservation reservation) {

        // Save the reservation to the repository
        reservation = reservationRepository.saveAndFlush(reservation);

        return reservation;
    }

    public Reservation get(Long id) {
        return reservationRepository.findOne(id);
    }

    public Reservation update(Reservation reservation) {
        Assert.notNull(reservation.getIdentity(), "Reservation id must be present in the resource URL");
        Assert.notNull(reservation, "Reservation request body cannot be null");

        Assert.state(reservationRepository.exists(reservation.getIdentity()),
                "The reservation with the supplied id does not exist");

        Reservation currentReservation = get(reservation.getIdentity());
        currentReservation.setStatus(reservation.getStatus());
        currentReservation.setOrderId(reservation.getOrderId());
        currentReservation.setProductId(reservation.getProductId());
        currentReservation.setWarehouse(reservation.getWarehouse());
        currentReservation.setInventory(reservation.getInventory());

        return reservationRepository.saveAndFlush(currentReservation);
    }

    public boolean delete(Long id) {
        Assert.state(reservationRepository.exists(id),
                "The reservation with the supplied id does not exist");
        this.reservationRepository.delete(id);
        return true;
    }

    public List<Reservation> create(List<Reservation> reservations) {
        Assert.notEmpty(reservations, "Reservation list must not be empty");
        List<Reservation> reservationList = this.reservationRepository.save(reservations);

        // Trigger reservation created events
        reservationList
                .forEach(r -> r.sendAsyncEvent(new ReservationEvent(ReservationEventType.RESERVATION_CREATED, r)));

        return reservationList;
    }

    public Reservations findReservationsByOrderId(Long orderId) {
        return new Reservations(reservationRepository.findReservationsByOrderId(orderId));
    }
}
