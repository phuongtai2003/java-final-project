package com.example.finalproject.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GoodDeliveryNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int totalPrice;
    private String status;
    private String dateCreated;

    @ManyToOne
    @JoinColumn(name = "retailStore_id", referencedColumnName = "id")
    private RetailStore retailStore;

    @ManyToOne
    @JoinColumn(name = "headquarterStaff_id", referencedColumnName = "id")
    private HeadquarterStaff headquarterStaff;

    @OneToMany(mappedBy = "goodDeliveryNote")
    private List<GoodDeliveryNoteDetail> goodDeliveryNoteDetails;
}
