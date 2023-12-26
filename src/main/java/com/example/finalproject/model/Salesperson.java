package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Salesperson {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private String address;
    private String phone;
    private String gender;
    private String image;
    private int salary;
    private boolean status;
    private boolean hasChangedPassword;
    private String dateCreated;
    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    @JsonIgnore
    private RetailStore retailStore;

    @OneToMany(mappedBy = "salesperson")
    @JsonIgnore
    private List<Customer> customers;

    @OneToMany(mappedBy = "salesperson")
    @JsonIgnore
    private List<Transaction> transactions;
}
