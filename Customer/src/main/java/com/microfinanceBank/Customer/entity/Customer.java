package com.microfinanceBank.Customer.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.microfinanceBank.Customer.enums.Status;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable=false)
    private String email;
    @Column(nullable = false,unique = true,updatable = false)
    private String keycloakId;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    private String password;
    @OneToMany(cascade =CascadeType.ALL,mappedBy = "customer",fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Account> accounts;
    private String imageUrl;
    private String contactNumber;
    @OneToOne(cascade =CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_address_id")
    private Address address;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private CustomerDetails customerDetails;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private Date creationDate;
    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private Time time;

    public void addAccount(Account account){
        if (account!=null){
            if (this.accounts==null){
                accounts=new HashSet<>();
            }
            accounts.add(account);
            account.setCustomer(this);
        }
    }
}
