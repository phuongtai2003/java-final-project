package com.example.demo.model;

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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "salesperson_id", referencedColumnName = "id")
    private Salesperson salesperson;
    @OneToMany(mappedBy = "transaction")
    private List<TransactionDetail> transactionDetails;
}
