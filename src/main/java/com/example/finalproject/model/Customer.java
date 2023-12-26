package com.example.finalproject.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String phone;
    private String address;
    @ManyToOne
    @JoinColumn(name = "salesperson_id", referencedColumnName = "id")
    private Salesperson salesperson;

    @OneToMany(mappedBy = "customer")
    private List<Transaction> transactions;
}