package com.example.finalproject.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.finalproject.model.Manager;
import com.example.finalproject.model.RetailStore;
import com.example.finalproject.model.Salesperson;
import com.example.finalproject.model.request.EmployeeForm;
import com.example.finalproject.model.request.UpdateEmployeeRequest;
import com.example.finalproject.repository.EmployeeService;
import com.example.finalproject.repository.ManagerService;
import com.example.finalproject.repository.RetailStoreService;
import com.example.finalproject.utils.EmailService;
import com.example.finalproject.utils.JsonTokenUtil;
import com.example.finalproject.utils.MyBean;
import com.example.finalproject.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    private EmployeeService employeeService;
    private RetailStoreService retailStoreService;
    private MyBean myBean;
    private Cloudinary cloudinary;
    private EmailService emailService;

    private PasswordEncoder passwordEncoder;

    public EmployeeController(@Autowired PasswordEncoder passwordEncoder , @Autowired EmailService emailService, @Autowired RetailStoreService retailStoreService, @Autowired EmployeeService employeeService, @Autowired Cloudinary cloudinary, @Autowired MyBean myBean) throws IOException {
        this.employeeService = employeeService;
        this.myBean = myBean;
        this.cloudinary = cloudinary;
        this.retailStoreService = retailStoreService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("")
    public String test(){
        return "employee/employee";
    }
    @GetMapping("/employees/{storeId}")
    @ResponseBody
    public List<Salesperson> getEmployees(@PathVariable int storeId){
        return employeeService.getSalespersonByStoreId(storeId);
    }
    @GetMapping("/edit/{employeeId}")
    public String edit(@PathVariable int employeeId, Model model){
        Salesperson salesperson = employeeService.getSalespersonById(employeeId);
        if(salesperson == null){
            model.addAttribute("errorMessage", "Employee not found!");
            return "employee/employee";
        }
        model.addAttribute("employee", salesperson);
        return "employee/add";
    }
    @PostMapping("/update")
    @ResponseBody
    public ResponseData updateSalesperson(UpdateEmployeeRequest request) throws IOException {
        Salesperson salesperson = employeeService.getSalespersonById(request.getEmployeeId());
        if(salesperson == null){
            return new ResponseData("Employee not found!", null, false, 400, null, null);
        }
        if(request.getPassword().isEmpty() || request.getNewPassword().isEmpty() || request.getNewPasswordConfirm().isEmpty()){
            return new ResponseData("Please fill all the fields!", null, false, 400, null, null);
        }
        if(!request.getNewPassword().equals(request.getNewPasswordConfirm())){
            return new ResponseData("New password and confirm password must be the same!", null, false, 400, null, null);
        }
        if(!passwordEncoder.matches(request.getPassword(), salesperson.getPassword())){
            return new ResponseData("Wrong password!", null, false, 400, null, null);
        }
        if(passwordEncoder.matches(request.getNewPassword(), salesperson.getPassword())){
            return new ResponseData("New password must be different from old password!", null, false, 400, null,null);
        }
        MultipartFile file = request.getImage();
        String imageUrl = salesperson.getImage();
        if(file != null && !file.isEmpty()){
            File uploadedFile = convertMultiPartToFile(file);
            Map uploadResult = cloudinary.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
            uploadedFile.delete();
            imageUrl = (String) uploadResult.get("secure_url");
        }
        salesperson.setImage(imageUrl);
        salesperson.setPassword(request.getNewPassword());
        salesperson.setHasChangedPassword(true);
        Salesperson updatedSalesperson = employeeService.updateSalesperson(salesperson);
        if(updatedSalesperson == null){
            return new ResponseData("Something went wrong!", null, false, 400, null, null);
        }
        return new ResponseData("Employee updated successfully!", updatedSalesperson, true, 200, null, null);
    }
    @PostMapping("/edit/{employeeId}")
    public String edit(@PathVariable int employeeId, EmployeeForm request, Model model) throws IOException {
        Salesperson salesperson = employeeService.getSalespersonById(employeeId);
        if(salesperson == null){
            model.addAttribute("errorMessage", "Employee not found!");
            return "employee/employee";
        }
        if(request.getStoreId() == null || request.getEmployeeName().isEmpty() || request.getEmployeeUsername().isEmpty() || request.getEmployeeAddress().isEmpty() || request.getEmployeePhone().isEmpty() || request.getEmployeeSalary() == null || request.getEmployeeGender().isEmpty()){
            model.addAttribute("errorMessage", "Please fill all the fields!");
            model.addAttribute("employee", salesperson);
            return "employee/add";
        }
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);
        MultipartFile file = request.getEmployeeImage();
        String imageUrl = salesperson.getImage();
        if(file != null && !file.isEmpty()){
            File uploadedFile = convertMultiPartToFile(file);
            Map uploadResult = cloudinary.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
            uploadedFile.delete();
            imageUrl = (String) uploadResult.get("secure_url");
        }
        RetailStore store = retailStoreService.getStoreById(request.getStoreId());
        if (store == null){
            model.addAttribute("errorMessage", "Store not found!");
            model.addAttribute("employee", salesperson);
            return "employee/add";
        }
        salesperson.setName(request.getEmployeeName());
        salesperson.setEmail(request.getEmployeeUsername()+"@gmail.com");
        salesperson.setPassword(request.getEmployeeUsername());
        salesperson.setStatus(false);
        salesperson.setHasChangedPassword(false);
        salesperson.setAddress(request.getEmployeeAddress());
        salesperson.setPhone(request.getEmployeePhone());
        salesperson.setGender(request.getEmployeeGender());
        salesperson.setImage(imageUrl);
        salesperson.setSalary(request.getEmployeeSalary());
        salesperson.setRetailStore(store);
        if(employeeService.updateSalesperson(salesperson) == null){
            model.addAttribute("errorMessage", "Something went wrong!");
            model.addAttribute("employee", salesperson);
            return "employee/add";
        }
        model.addAttribute("successMessage", "Employee updated successfully!");
        model.addAttribute("employee", salesperson);
        return "redirect:/employee";
    }
    @GetMapping("/add")
    public String add(){
        return "employee/add";
    }
    @PostMapping("/add")
    public String addEmployee(EmployeeForm request, Model model) throws IOException {
        if(request.getStoreId() == null || request.getEmployeeName().isEmpty() || request.getEmployeeUsername().isEmpty() || request.getEmployeeAddress().isEmpty() || request.getEmployeePhone().isEmpty() || request.getEmployeeSalary() == null || request.getEmployeeGender().isEmpty()){
            model.addAttribute("errorMessage", "Please fill all the fields!");
            model.addAttribute("request", request);
            return "employee/add";
        }
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);
        MultipartFile file = request.getEmployeeImage();
        String imageUrl = myBean.getDefaultImage();
        if(file != null && !file.isEmpty()){
            File uploadedFile = convertMultiPartToFile(file);
            Map uploadResult = cloudinary.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
            uploadedFile.delete();
            imageUrl = (String) uploadResult.get("secure_url");
        }
        RetailStore store = retailStoreService.getStoreById(request.getStoreId());
        if (store == null){
            model.addAttribute("errorMessage", "Store not found!");
            model.addAttribute("request", request);
            return "employee/add";
        }
        Salesperson salesperson = new Salesperson(-1, request.getEmployeeName(), request.getEmployeeUsername()+"@gmail.com", request.getEmployeeUsername(), request.getEmployeeAddress(), request.getEmployeePhone(), request.getEmployeeGender(), imageUrl, request.getEmployeeSalary(), false, false, formattedDate, store, new ArrayList<>(), new ArrayList<>());
        if(employeeService.addSalesperson(salesperson) == null){
            model.addAttribute("errorMessage", "Something went wrong!");
            model.addAttribute("request", request);
            return "employee/add";
        }
        model.addAttribute("successMessage", "Employee added successfully!");
        model.addAttribute("request", request);
        return "employee/add";
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    @DeleteMapping("/delete/{employeeId}")
    @ResponseBody
    public ResponseData delete(@PathVariable int employeeId){
        Salesperson salesperson = employeeService.getSalespersonById(employeeId);
        if(salesperson == null){
            return new ResponseData("Employee not found!", null, false, 400, null, null);
        }
        if(employeeService.deleteEmployee(salesperson.getId()) == null){
            return new ResponseData("Employee not found!", null, false, 400, null, null);
        }
        return new ResponseData("Employee deleted successfully!", null, true, 200, null, null);
    }
    @GetMapping("/sendEmail/{employeeId}")
    @ResponseBody
    public ResponseData sendEmail(@PathVariable int employeeId){
        Salesperson salesperson = employeeService.getSalespersonById(employeeId);
        if(salesperson == null){
            return new ResponseData("Employee not found!", null, false, 400, null, null);
        }
        String token = JsonTokenUtil.generateNewAccountToken(salesperson.getEmail(), salesperson.getEmail().split("@")[0]);
        emailService.sendEmail(salesperson.getEmail(), "Account Information", "Username: "+salesperson.getEmail()+"\nPassword: "+salesperson.getEmail().split("@")[0]+"\nPlease click this link to login: http://localhost:8080/loginNewAccount?token="+token);
        return new ResponseData("Email sent successfully!", null, true, 200, null, null);
    }
}