package com.example.finalproject.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStoreRequest {
    @NotBlank(message = "Store name cannot be empty")
    private String storeName;
    @NotBlank(message = "Store address cannot be empty")
    private String storeAddress;
    @NotBlank(message = "Manager name cannot be empty")
    private String managerName;
    @NotBlank(message = "Manager phone cannot be empty")
    private String managerPhone;
    @NotBlank(message = "Manager gender cannot be empty")
    private String managerGender;
    private MultipartFile managerImage;
}
