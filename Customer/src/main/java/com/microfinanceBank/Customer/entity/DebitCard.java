package com.microfinanceBank.Customer.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.microfinanceBank.Customer.enums.CardStatus;
import com.microfinanceBank.Customer.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DebitCard {

    @Id
//    @SequenceGenerator(name = "DebitCard" , sequenceName = "DEBIT_CARD_ID_SEQ")
//    @GeneratedValue(generator = "DebitCard")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "debitCard",cascade = {
            CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH
    })
    @JsonManagedReference
    private Set<Account> accounts;

    @Column(nullable = false,unique = true)
    private String cardNo;

    @Column( nullable = false)
    private int cvvNo;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Enumerated(EnumType.STRING)
    private CardStatus cardStatus;

    @Column( nullable = false)
    @CreationTimestamp
    private LocalDate issuedDate;

    @Column( nullable = false)
    private LocalDate expireDate;

    @UpdateTimestamp
    private Date lastActivity;

    public void addAccount(Account account){
        if (account!=null) {
            if (this.accounts == null)
                this.accounts = new HashSet<>();
        this.accounts.add(account);
        account.setDebitCard(this);
        }
    }


}
