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
public class Transaction {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;
    private int totalPrice;
    private int customerPaid;
    private int transactionChange;
    private String dateCreated;
    @ManyToOne()
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
    @ManyToOne()
    @JoinColumn(name = "salesperson_id", referencedColumnName = "id")
    @JsonIgnore
    private Salesperson salesperson;
    @OneToMany(mappedBy = "transaction")
    @JsonIgnore
    private List<TransactionDetail> transactionDetails;
}
