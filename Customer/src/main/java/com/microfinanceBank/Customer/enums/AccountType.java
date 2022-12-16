package com.microfinanceBank.Customer.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public enum AccountType {
    SAVINGS,CURRENT,FIXED_DEPOSIT

}
