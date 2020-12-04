package demo.config;

import demo.account.Account;
import demo.account.AccountStatus;
import demo.event.AccountEvent;
import demo.event.AccountEventType;
import demo.function.*;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.net.URI;
import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<AccountStatus, AccountEventType> {

    final private Logger log = Logger.getLogger(StateMachineConfig.class);

    @Override
    public void configure(StateMachineStateConfigurer<AccountStatus, AccountEventType> states) {
        try {
            // Describe the initial condition of the account state machine
            states.withStates()
                    .initial(AccountStatus.ACCOUNT_CREATED)
                    .states(EnumSet.allOf(AccountStatus.class));
        } catch (Exception e) {
            throw new RuntimeException("State machine configuration failed", e);
        }
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<AccountStatus, AccountEventType> transitions) {
        try {
            // Describe state machine transitions for accounts
            transitions.withExternal()
                    .source(AccountStatus.ACCOUNT_CREATED)
                    .target(AccountStatus.ACCOUNT_PENDING)
                    .event(AccountEventType.ACCOUNT_CREATED)
                    .action(createAccount())
                    .and()
                    .withExternal()
                    .source(AccountStatus.ACCOUNT_PENDING)
                    .target(AccountStatus.ACCOUNT_CONFIRMED)
                    .event(AccountEventType.ACCOUNT_CONFIRMED)
                    .action(confirmAccount())
                    .and()
                    .withExternal()
                    .source(AccountStatus.ACCOUNT_CONFIRMED)
                    .target(AccountStatus.ACCOUNT_ACTIVE)
                    .event(AccountEventType.ACCOUNT_ACTIVATED)
                    .action(activateAccount())
                    .and()
                    .withExternal()
                    .source(AccountStatus.ACCOUNT_ACTIVE)
                    .target(AccountStatus.ACCOUNT_ARCHIVED)
                    .event(AccountEventType.ACCOUNT_ARCHIVED)
                    .action(archiveAccount())
                    .and()
                    .withExternal()
                    .source(AccountStatus.ACCOUNT_ACTIVE)
                    .target(AccountStatus.ACCOUNT_SUSPENDED)
                    .event(AccountEventType.ACCOUNT_SUSPENDED)
                    .action(suspendAccount())
                    .and()
                    .withExternal()
                    .source(AccountStatus.ACCOUNT_ARCHIVED)
                    .target(AccountStatus.ACCOUNT_ACTIVE)
                    .event(AccountEventType.ACCOUNT_ACTIVATED)
                    .action(unarchiveAccount())
                    .and()
                    .withExternal()
                    .source(AccountStatus.ACCOUNT_SUSPENDED)
                    .target(AccountStatus.ACCOUNT_ACTIVE)
                    .event(AccountEventType.ACCOUNT_ACTIVATED)
                    .action(unsuspendAccount());
        } catch (Exception e) {
            throw new RuntimeException("Could not configure state machine transitions", e);
        }
    }

    private AccountEvent applyEvent(StateContext<AccountStatus, AccountEventType> context,
            AccountFunction accountFunction) {
        AccountEvent accountEvent = null;

        // Log out the progress of the state machine replication
        log.info("Replicate event: " + context.getMessage().getPayload());

        // The machine is finished replicating when an AccountEvent is found in the message header
        if (context.getMessageHeader("event") != null) {
            accountEvent = (AccountEvent) context.getMessageHeader("event");
            log.info("State machine replicated: " + accountEvent.getType());

            // Apply the provided function to the AccountEvent
            accountFunction.apply(accountEvent);
        }

        return accountEvent;
    }

    @Bean
    public Action<AccountStatus, AccountEventType> createAccount() {
        return context -> applyEvent(context, new CreateAccount(context));
    }

    @Bean
    public Action<AccountStatus, AccountEventType> confirmAccount() {
        return context -> {
            // Map the account action to a Java 8 lambda function
            ConfirmAccount accountFunction;

            accountFunction = new ConfirmAccount(context, event -> {
                // Get the account resource for the event
                Traverson traverson = new Traverson(
                        URI.create(event.getLink("account").getHref()),
                        MediaTypes.HAL_JSON
                );

                // Follow the command resource to activate the account
                Account account = traverson.follow("commands")
                        .follow("activate")
                        .toEntity(Account.class)
                        .getBody();

                log.info(event.getType() + ": " + event.getLink("account").getHref());

                return account;
            });

            applyEvent(context, accountFunction);
        };
    }

    @Bean
    public Action<AccountStatus, AccountEventType> activateAccount() {
        return context -> applyEvent(context,
                new ActivateAccount(context, event -> {
                    log.info(event.getType() + ": " + event.getLink("account").getHref());
                    // Get the account resource for the event
                    Traverson traverson = new Traverson(
                            URI.create(event.getLink("account").getHref()),
                            MediaTypes.HAL_JSON
                    );

                    return traverson.follow("self")
                            .toEntity(Account.class)
                            .getBody();
                }));
    }

    @Bean
    public Action<AccountStatus, AccountEventType> archiveAccount() {
        return context -> applyEvent(context,
                new ArchiveAccount(context, event -> {
                    log.info(event.getType() + ": " + event.getLink("account").getHref());
                    // Get the account resource for the event
                    Traverson traverson = new Traverson(
                            URI.create(event.getLink("account").getHref()),
                            MediaTypes.HAL_JSON
                    );

                    return traverson.follow("self")
                            .toEntity(Account.class)
                            .getBody();
                }));
    }

    @Bean
    public Action<AccountStatus, AccountEventType> suspendAccount() {
        return context -> applyEvent(context,
                new SuspendAccount(context, event -> {
                    log.info(event.getType() + ": " + event.getLink("account").getHref());
                    // Get the account resource for the event
                    Traverson traverson = new Traverson(
                            URI.create(event.getLink("account").getHref()),
                            MediaTypes.HAL_JSON
                    );

                    return traverson.follow("self")
                            .toEntity(Account.class)
                            .getBody();
                }));
    }

    @Bean
    public Action<AccountStatus, AccountEventType> unarchiveAccount() {
        return context -> applyEvent(context,
                new UnarchiveAccount(context, event -> {
                    log.info(event.getType() + ": " + event.getLink("account").getHref());
                    // Get the account resource for the event
                    Traverson traverson = new Traverson(
                            URI.create(event.getLink("account").getHref()),
                            MediaTypes.HAL_JSON
                    );

                    return traverson.follow("self")
                            .toEntity(Account.class)
                            .getBody();
                }));
    }

    @Bean
    public Action<AccountStatus, AccountEventType> unsuspendAccount() {
        return context -> applyEvent(context,
                new UnsuspendAccount(context, event -> {
                    log.info(event.getType() + ": " + event.getLink("account").getHref());
                    // Get the account resource for the event
                    Traverson traverson = new Traverson(
                            URI.create(event.getLink("account").getHref()),
                            MediaTypes.HAL_JSON
                    );

                    return traverson.follow("self")
                            .toEntity(Account.class)
                            .getBody();
                }));
    }
}
