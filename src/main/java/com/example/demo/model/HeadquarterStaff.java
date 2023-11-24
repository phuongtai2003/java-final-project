package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HeadquarterStaff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;
    private String gender;
    private String image;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "headquarterStaff")
    private List<GoodDeliveryNote> deliveryNotes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "headquarterStaff")
    private List<GoodReceived> receivedNotes;
}
