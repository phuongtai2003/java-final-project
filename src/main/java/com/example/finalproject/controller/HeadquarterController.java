package com.example.finalproject.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.finalproject.model.HeadquarterStaff;
import com.example.finalproject.model.Manager;
import com.example.finalproject.model.RetailStore;
import com.example.finalproject.model.request.*;
import com.example.finalproject.repository.HeadquarterService;
import com.example.finalproject.utils.CloudinaryConfig;
import com.example.finalproject.utils.JsonTokenUtil;
import com.example.finalproject.utils.MyBean;
import com.example.finalproject.utils.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/headquarter")
public class HeadquarterController {
    private HeadquarterService headquarterService;
    private MyBean myBean;
    private Cloudinary cloudinary;
    public HeadquarterController(@Autowired  HeadquarterService headquarterService, @Autowired Cloudinary cloudinary, @Autowired MyBean myBean) {
        this.headquarterService = headquarterService;
        this.myBean = myBean;
        this.cloudinary = cloudinary;
    }
    @GetMapping("")
    public String headquarter(Model model) {
        model.addAttribute("stores", headquarterService.getAllManagers());
        return "headquarter/headquarter";
    }
    @GetMapping("/add")
    public String add() {
        return "headquarter/add";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("manager", headquarterService.getManagerById(id));
        return "headquarter/add";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, @Valid CreateStoreRequest request, Model model) throws IOException {
        if (request.getManagerName().isEmpty() || request.getManagerPhone().isEmpty() || request.getManagerGender().isEmpty() || request.getStoreName().isEmpty() || request.getStoreAddress().isEmpty()) {
            model.addAttribute("errorMessage", "Please fill all the fields!");
            model.addAttribute("request", request);
            model.addAttribute("manager", headquarterService.getManagerById(id));
            return "headquarter/add";
        }
        MultipartFile file = request.getManagerImage();
        String imageUrl = myBean.getDefaultImage();
        if (file != null && !file.isEmpty()) {
            File uploadedFile = convertMultiPartToFile(file);
            Map uploadResult = cloudinary.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
            uploadedFile.delete();
            imageUrl = (String) uploadResult.get("secure_url");
        }

        RetailStore store = headquarterService.getStoreByManager(headquarterService.getManagerById(id));

        Manager manager = headquarterService.getManagerById(id);
        manager.setName(request.getManagerName());
        manager.setPhone(request.getManagerPhone());
        manager.setGender(request.getManagerGender());
        manager.setImage(imageUrl);

        store.setStoreName(request.getStoreName());
        store.setAddress(request.getStoreAddress());

        headquarterService.updateStore(manager, store);
        model.addAttribute("successMessage", "Store updated successfully!");
        return "redirect:/headquarter";
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseData delete(@PathVariable("id") int id) {
        boolean res = headquarterService.deleteStore(id);
        if(res){
            return new ResponseData("Delete Success", null, true, 200, null, null);
        }
        else{
            return new ResponseData("Delete Failed", null, false, 400, null, null);
        }
    }

    @PostMapping("/add")
    public String addStore(@Valid CreateStoreRequest request, Model model) throws IOException {
        if(request.getManagerName().isEmpty() || request.getManagerPhone().isEmpty() || request.getManagerGender().isEmpty() || request.getStoreName().isEmpty() || request.getStoreAddress().isEmpty()){
            model.addAttribute("errorMessage", "Please fill all the fields!");
            model.addAttribute("request", request);
            return "headquarter/add";
        }
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);
        MultipartFile file = request.getManagerImage();
        String imageUrl = myBean.getDefaultImage();
        if(file != null && !file.isEmpty()){
            File uploadedFile = convertMultiPartToFile(file);
            Map uploadResult = cloudinary.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
            uploadedFile.delete();
            imageUrl = (String) uploadResult.get("secure_url");
        }

        RetailStore retailStore = new RetailStore(-1, request.getStoreName(), request.getStoreAddress(), 0, formattedDate, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Manager manager = new Manager(-1, request.getManagerName(), "admin@gmail.com", "admin", request.getManagerPhone(), request.getManagerGender(), imageUrl, formattedDate, null);
        headquarterService.addNewStore(retailStore, manager);
        model.addAttribute("successMessage", "Store added successfully!");
        return "headquarter/add";
    }
    @GetMapping("/login")
    public String login() {
        return "headquarter/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseData login(@RequestBody LoginHeadquarterRequest request){
        HeadquarterStaff res = headquarterService.loginHeadquarter(request.getUsername()+"@gmail.com", request.getPassword());
        if(res == null){
            return new ResponseData("Login Failed", null, false, 400, null, null);
        }
        else{
            String token = JsonTokenUtil.generateToken(res.getEmail(), request.getUsername(), "headquarter");
            return new ResponseData("Login Success", res, true, 200, token, "headquarter");
        }
    }

    @PostMapping("/register")
    @ResponseBody
    public HeadquarterStaff register(@RequestBody CreateHeadquarterRequest request){
        return headquarterService.registerHeadquarter(request.getEmail(), request.getPassword(), request.getName(), request.getGender());
    }

    @GetMapping("/inventory")
    public String inventory() {
        return "headquarter/headquarter";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "headquarter/headquarter";
    }

    @GetMapping("/analysis")
    public String analysis() {
        return "headquarter/headquarter";
    }
}
