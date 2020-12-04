package demo.event;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Profile;

@EnableAutoConfiguration
@EnableBinding(Sink.class)
@Profile({"cloud", "development", "docker"})
public class AccountEventStream {

    private EventService eventService;

    public AccountEventStream(EventService eventService) {
        this.eventService = eventService;
    }

    @StreamListener(Sink.INPUT)
    public void streamListener(AccountEvent accountEvent) {
        eventService.apply(accountEvent);
    }
}
