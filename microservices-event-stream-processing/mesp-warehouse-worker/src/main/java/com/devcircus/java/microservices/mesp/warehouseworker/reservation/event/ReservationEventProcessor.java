package demo.reservation.event;

import demo.reservation.ReservationStateFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Profile;

@EnableAutoConfiguration
@EnableBinding(ReservationEventSink.class)
@Profile({"cloud", "development", "docker"})
public class ReservationEventProcessor {

    private ReservationStateFactory stateFactory;

    public ReservationEventProcessor(ReservationStateFactory stateFactory) {
        this.stateFactory = stateFactory;
    }

    @StreamListener(ReservationEventSink.INPUT)
    public void streamListener(ReservationEvent reservationEvent) {
        stateFactory.apply(reservationEvent);
    }
}
