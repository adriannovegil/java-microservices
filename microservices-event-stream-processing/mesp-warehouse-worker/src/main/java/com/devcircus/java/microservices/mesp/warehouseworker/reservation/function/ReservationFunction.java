package demo.reservation.function;

import demo.reservation.domain.Reservation;
import demo.reservation.domain.ReservationStatus;
import demo.reservation.event.ReservationEvent;
import demo.reservation.event.ReservationEventType;
import org.apache.log4j.Logger;
import org.springframework.statemachine.StateContext;

import java.util.function.Function;

public abstract class ReservationFunction {

    final private Logger log = Logger.getLogger(ReservationFunction.class);
    final protected StateContext<ReservationStatus, ReservationEventType> context;
    final protected Function<ReservationEvent, Reservation> lambda;

    public ReservationFunction(StateContext<ReservationStatus, ReservationEventType> context,
            Function<ReservationEvent, Reservation> lambda) {
        this.context = context;
        this.lambda = lambda;
    }

    public Reservation apply(ReservationEvent event) {
        // Execute the lambda function
        Reservation result = lambda.apply(event);
        context.getExtendedState().getVariables().put("reservation", result);
        log.info("Reservation function: " + event.getType());
        return result;
    }
}
