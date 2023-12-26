package com.example.finalproject.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GoodReceivedDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "good_received_id", referencedColumnName = "id")
    private GoodReceived goodReceived;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}
