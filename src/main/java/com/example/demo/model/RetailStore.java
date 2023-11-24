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
public class RetailStore {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;
    private String storeName;
    private String address;
    private int staffNum;
    private String createDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "retailStore")
    private List<Salesperson> salespeople;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "retailStore")
    private List<Inventory> inventories;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "retailStore")
    private List<GoodDeliveryNote> goodDeliveryNotes;
}
