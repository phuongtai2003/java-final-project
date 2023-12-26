package com.example.finalproject.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String sku;
    private String barcode;
    private int importPrice;
    private int retailPrice;
    private String dateCreated;
    @OneToMany(mappedBy = "product")
    private List<ProductImage> images;

    @OneToMany(mappedBy = "product")
    private List<TransactionDetail> transactions;

    @OneToMany(mappedBy = "product")
    private List<Inventory> inventories;
    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @OneToMany(mappedBy = "product")
    private List<GoodDeliveryNoteDetail> goodDeliveryNoteDetails;

    @OneToMany(mappedBy = "product")
    private List<GoodReceivedDetail> goodReceivedDetails;
}
