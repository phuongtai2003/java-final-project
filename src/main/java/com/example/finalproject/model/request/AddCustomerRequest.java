package com.example.finalproject.model.request;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddCustomerRequest {
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private Integer salespersonId;
}
