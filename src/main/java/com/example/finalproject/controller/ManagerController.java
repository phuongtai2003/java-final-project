package com.example.finalproject.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.finalproject.model.Manager;
import com.example.finalproject.model.Salesperson;
import com.example.finalproject.model.request.UpdateEmployeeRequest;
import com.example.finalproject.repository.ManagerService;
import com.example.finalproject.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    private ManagerService managerService;
    private PasswordEncoder passwordEncoder;
    private Cloudinary cloudinary;

    public ManagerController(@Autowired Cloudinary cloudinary,@Autowired ManagerService managerService, @Autowired PasswordEncoder passwordEncoder) {
        this.managerService = managerService;
        this.passwordEncoder = passwordEncoder;
        this.cloudinary = cloudinary;
    }

    @PostMapping("/update")
    public ResponseData updateManager(UpdateEmployeeRequest request) throws IOException {
        Manager manager = managerService.getManagerById(request.getEmployeeId());
        if(manager == null){
            return new ResponseData("Employee not found!", null, false, 400, null, null);
        }
        if(request.getPassword().isEmpty() || request.getNewPassword().isEmpty() || request.getNewPasswordConfirm().isEmpty()){
            return new ResponseData("Please fill all the fields!", null, false, 400, null, null);
        }
        if(!request.getNewPassword().equals(request.getNewPasswordConfirm())){
            return new ResponseData("New password and confirm password must be the same!", null, false, 400, null, null);
        }
        if(!passwordEncoder.matches(request.getPassword(), manager.getPassword())){
            return new ResponseData("Wrong password!", null, false, 400, null, null);
        }
        if(passwordEncoder.matches(request.getNewPassword(), manager.getPassword())){
            return new ResponseData("New password must be different from old password!", null, false, 400, null,null);
        }
        MultipartFile file = request.getImage();
        String imageUrl = manager.getImage();
        if(file != null && !file.isEmpty()){
            File uploadedFile = convertMultiPartToFile(file);
            Map uploadResult = cloudinary.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
            uploadedFile.delete();
            imageUrl = (String) uploadResult.get("secure_url");
        }
        manager.setImage(imageUrl);
        manager.setPassword(request.getNewPassword());
        Manager updatedManager = managerService.updateManager(manager);
        if(updatedManager == null){
            return new ResponseData("Something went wrong!", null, false, 400, null, null);
        }
        return new ResponseData("Manager updated successfully!", updatedManager, true, 200, null, null);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
