package com.microfinanceBank.Customer.repository;

import com.microfinanceBank.Customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query(value = "Select * from customer c where c.email=?1",nativeQuery = true)
    Optional<Customer> findByEmail(String email);
    Customer findByKeycloakId(String id);

    @Query("select count(c)>0 from Customer c ")
    boolean checkIfExistsByEmail(@Param("email") String email);
    boolean existsByEmail(String email);
    @Query("select c from Customer as c " +
            "join fetch c.customerDetails  cd " +
            "where day(cd.dob) = day(CURRENT_DATE) " +
            "and MONTH(cd.dob) = MONTH(CURRENT_DATE)")
    Collection<Customer> getCustomersByBirthday();
}
