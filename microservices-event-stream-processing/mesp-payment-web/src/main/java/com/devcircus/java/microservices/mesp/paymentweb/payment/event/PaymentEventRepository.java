package demo.payment.event;

import demo.event.EventRepository;

public interface PaymentEventRepository extends EventRepository<PaymentEvent, Long> {
}
