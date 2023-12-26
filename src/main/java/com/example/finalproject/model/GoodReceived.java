package com.example.finalproject.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GoodReceived {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int totalPrice;
    private String dateCreated;

    @ManyToOne
    @JoinColumn(name = "distributor_id", referencedColumnName = "id")
    private Distributor distributor;

    @ManyToOne
    @JoinColumn(name = "headquarter_staff_id", referencedColumnName = "id")
    private HeadquarterStaff headquarterStaff;

    @OneToMany(mappedBy = "goodReceived")
    private List<GoodReceivedDetail> goodReceivedDetails;
}
