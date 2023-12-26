package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@ToString(exclude = {"retailStore", "product"})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Inventory {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(name = "retail_store_id", referencedColumnName = "id")
    @JsonIgnore
    private RetailStore retailStore;

    @ManyToOne()
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @JsonIgnore
    private Product product;

    private int quantity;
}
