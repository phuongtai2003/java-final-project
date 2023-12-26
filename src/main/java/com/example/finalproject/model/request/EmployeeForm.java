package com.example.finalproject.model.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeForm {
    private String employeeName;

    private String employeeUsername;

    private String employeeAddress;

    private String employeePhone;

    private Integer employeeSalary;

    private String employeeGender;

    private MultipartFile employeeImage;
    private Integer storeId;
}
