package com.example.finalproject.model.request;

import com.example.finalproject.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {
    private String name;

    private String description;
    private String sku;

    private Integer importPrice;

    private Integer retailPrice;

    private List<Integer> categories;
    private List<MultipartFile> images;
    private Integer storeId;
}
