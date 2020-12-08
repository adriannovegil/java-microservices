package com.devcircus.java.microservices.mes.accountservice.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1")
public class AccountControllerV1 {

    private final AccountServiceV1 accountService;

    /**
     * 
     * @param accountService 
     */
    @Autowired
    public AccountControllerV1(AccountServiceV1 accountService) {
        this.accountService = accountService;
    }

    /**
     * 
     * @return
     * @throws Exception 
     */
    @RequestMapping(path = "/accounts")
    public ResponseEntity getUserAccount() throws Exception {
        return Optional.ofNullable(accountService.getUserAccounts())
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new Exception("Accounts for user do not exist"));
    }
}
