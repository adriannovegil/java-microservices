package demo.event;

import demo.account.Account;
import demo.account.AccountStatus;

public enum AccountEventType {
    ACCOUNT_CREATED,
    ACCOUNT_CONFIRMED,
    ACCOUNT_ACTIVATED,
    ACCOUNT_SUSPENDED,
    ACCOUNT_ARCHIVED
}
