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
public class Distributor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    private String phone;
    private String description;

    @OneToMany(mappedBy = "distributor")
    private List<GoodReceived> goodReceivedList;
}
