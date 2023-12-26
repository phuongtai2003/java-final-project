package com.example.finalproject.model.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmployeeRequest {
    private Integer employeeId;
    private MultipartFile image;
    private String password;
    private String newPassword;
    private String newPasswordConfirm;
}
