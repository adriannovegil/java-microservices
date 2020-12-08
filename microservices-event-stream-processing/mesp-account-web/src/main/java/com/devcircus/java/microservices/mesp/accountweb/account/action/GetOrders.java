package com.devcircus.java.microservices.mesp.accountweb.account.action;

import com.devcircus.java.microservices.mesp.accountweb.account.domain.Account;
import com.devcircus.java.microservices.mesp.accountweb.order.domain.OrderModule;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GetOrders extends Action<Account> {

    private OrderModule orderModule;

    public GetOrders(OrderModule orderModule) {
        this.orderModule = orderModule;
    }

    public Orders apply(Account account) {
        // Get orders from the order service
        return orderModule.getDefaultService()
                .findOrdersByAccountId(account.getIdentity());
    }
}
