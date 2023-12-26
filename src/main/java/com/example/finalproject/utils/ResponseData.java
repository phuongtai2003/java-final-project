package com.example.finalproject.utils;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResponseData {
    private String message;
    private Object data;
    private boolean success;
    private int code;
    private String token;
    private String role;
}
