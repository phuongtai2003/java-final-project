package com.example.demo.model;

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
    private String email;
    private String password;
    private String address;
    private String phone;
    private String gender;
    private String image;
    private int salary;
    private boolean status;
    private String dateCreated;
    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private RetailStore retailStore;

    @OneToMany(mappedBy = "salesperson", cascade = CascadeType.ALL)
    private List<Customer> customers;

    @OneToMany(mappedBy = "salesperson", cascade = CascadeType.ALL)
    private List<Transaction> transactions;
}
