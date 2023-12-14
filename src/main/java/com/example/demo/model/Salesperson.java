package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Salesperson {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private String address;

    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}", message = "Phone must be in the format xxx-xxx-xxxx")
    private String phone;
    private String gender;
    private String image;
    private int salary;
    private boolean status;
    private String dateCreated;
    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private RetailStore retailStore;

    @OneToMany(mappedBy = "salesperson", cascade = CascadeType.ALL)
    private List<Customer> customers;

    @OneToMany(mappedBy = "salesperson", cascade = CascadeType.ALL)
    private List<Transaction> transactions;
}
