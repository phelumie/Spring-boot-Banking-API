package com.microfinanceBank.Transaction.enums;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public enum  TransactionType {
    WITHDRAW,DEPOSIT,TRANSFER
}
