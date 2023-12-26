package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString(exclude = {"salespeople", "inventories", "goodDeliveryNotes"})
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

    @OneToMany(mappedBy = "retailStore")
    @JsonIgnore
    private List<Salesperson> salespeople;

    @OneToMany(mappedBy = "retailStore")
    private List<Inventory> inventories;

    @OneToMany(mappedBy = "retailStore")
    private List<GoodDeliveryNote> goodDeliveryNotes;
}
