package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"store"})
public class Manager {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String gender;
    private String image;
    private String datePromoted;
    @OneToOne()
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    @JsonIgnore
    private RetailStore store;
}
