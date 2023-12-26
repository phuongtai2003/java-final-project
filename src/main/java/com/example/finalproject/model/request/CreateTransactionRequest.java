package com.example.finalproject.model.request;

import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionRequest {
    private Integer customerId;
    private Integer salespersonId;
    private Integer customerPayment;
    private List<CartItem> products;
}

