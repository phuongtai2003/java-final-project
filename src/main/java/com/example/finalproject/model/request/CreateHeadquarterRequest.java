package com.example.finalproject.model.request;

import lombok.Data;

@Data
public class CreateHeadquarterRequest {
    private String email;
    private String gender;
    private String password;
    private String name;
}
