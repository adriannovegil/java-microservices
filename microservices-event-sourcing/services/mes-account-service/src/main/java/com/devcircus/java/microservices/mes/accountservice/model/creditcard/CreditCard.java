package com.devcircus.java.microservices.mes.accountservice.model.creditcard;

import com.devcircus.java.microservices.mes.accountservice.model.BaseEntity;
import java.io.Serializable;
import javax.persistence.*;

@Entity
public class CreditCard extends BaseEntity implements Serializable {

    private Long id;
    private String number;

    @Enumerated(EnumType.STRING)
    private CreditCardType type;

    public CreditCard() {
    }

    public CreditCard(String number, CreditCardType type) {
        this.number = number;
        this.type = type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public CreditCardType getType() {
        return type;
    }

    public void setType(CreditCardType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CreditCard{"
                + "id=" + id
                + ", number='" + number.replaceAll("([\\d]{4})(?!$)", "****-") + '\''
                + ", type='" + type + '\''
                + '}';
    }
}
