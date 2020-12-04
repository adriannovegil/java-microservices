package demo.reservation.domain;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;

public class Reservations extends Resources<Reservation> {

    public Reservations() {
    }

    public Reservations(Iterable<Reservation> content, Link... links) {
        super(content, links);
    }
}
