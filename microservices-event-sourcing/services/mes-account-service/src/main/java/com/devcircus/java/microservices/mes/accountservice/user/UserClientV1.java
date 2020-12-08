package com.devcircus.java.microservices.mes.accountservice.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("user-service")
public interface UserClientV1 {

    @RequestMapping(method = RequestMethod.GET, value = "/uaa/v1/me")
    User getAuthenticatedUser();
}
