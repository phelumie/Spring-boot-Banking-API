package com.microfinanceBank.Employee.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Branch  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade=CascadeType.ALL,mappedBy = "branch",fetch = FetchType.EAGER )
    @JsonManagedReference
    private List<Employee> employees;
//    @ManyToOne
//    @JoinColumn(name = "manager_id")
//    private Employee manager;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    public void addAddress(Address address) {
        this.address=address;
        address.setBranch(this);
    }

}
