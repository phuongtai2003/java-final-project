package com.example.finalproject.model.request;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Integer productId;
    private Integer quantity;
    private String productName;
    private Integer productPrice;
}
