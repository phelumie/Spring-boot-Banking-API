package com.microfinanceBank.Customer.repository;

import com.microfinanceBank.Customer.entity.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.transaction.annotation.Transactional;


public interface DebitCardRepository extends JpaRepository<DebitCard,Long> {

}
