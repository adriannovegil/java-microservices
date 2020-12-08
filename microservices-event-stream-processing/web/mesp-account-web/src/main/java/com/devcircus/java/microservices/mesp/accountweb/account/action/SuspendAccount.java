package com.devcircus.java.microservices.mesp.accountweb.account.action;

import com.devcircus.java.microservices.mesp.accountweb.account.domain.Account;
import com.devcircus.java.microservices.mesp.accountweb.account.domain.AccountModule;
import com.devcircus.java.microservices.mesp.accountweb.account.domain.AccountService;
import com.devcircus.java.microservices.mesp.accountweb.account.domain.AccountStatus;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import static com.devcircus.java.microservices.mesp.accountweb.account.domain.AccountStatus.ACCOUNT_ACTIVE;
import static com.devcircus.java.microservices.mesp.accountweb.account.domain.AccountStatus.ACCOUNT_SUSPENDED;
import com.devcircus.java.microservices.mesp.accountweb.account.event.AccountEvent;
import com.devcircus.java.microservices.mesp.accountweb.account.event.AccountEventType;

@Service
@Transactional
public class SuspendAccount extends Action<Account> {

    private final Logger log = Logger.getLogger(this.getClass());

    public Account apply(Account account) {
        Assert.isTrue(account.getStatus() != ACCOUNT_SUSPENDED, "The account is already suspended");
        Assert.isTrue(account.getStatus() == ACCOUNT_ACTIVE, "An inactive account cannot be suspended");

        AccountService accountService = account.getModule(AccountModule.class)
                .getDefaultService();

        AccountStatus status = account.getStatus();

        // Suspend the account
        account.setStatus(AccountStatus.ACCOUNT_SUSPENDED);
        account = accountService.update(account);

        try {
            // Trigger the account suspended event
            account.sendAsyncEvent(new AccountEvent(AccountEventType.ACCOUNT_SUSPENDED, account));
        } catch (Exception ex) {
            log.error("Account could not be suspended", ex);

            // Rollback the operation
            account.setStatus(status);
            account = accountService.update(account);
        }

        return account;
    }
}
