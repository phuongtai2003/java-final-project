package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Salesperson salesperson;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Transaction> transactions;
}