package com.example.finalproject.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GoodDeliveryNoteDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int quantity;

    @ManyToOne()
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    private GoodDeliveryNote goodDeliveryNote;

    @ManyToOne()
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}
