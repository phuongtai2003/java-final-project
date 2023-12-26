package com.example.finalproject.controller;

import com.example.finalproject.model.Manager;
import com.example.finalproject.model.Salesperson;
import com.example.finalproject.repository.EmployeeService;
import com.example.finalproject.repository.LoginService;
import com.example.finalproject.utils.JsonTokenUtil;
import com.example.finalproject.utils.ResponseData;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    private EmployeeService employeeService;
    private LoginService loginService;
    public LoginController(@Autowired EmployeeService employeeService, @Autowired LoginService loginService){
        this.employeeService = employeeService;
        this.loginService = loginService;
    }
    @GetMapping("/")
    public String loginScreen(Model model){
        model.addAttribute("retailStores", loginService.getAllRetailStores());
        return "login";
    }
    @GetMapping("/login")
    public String index(Model model){
        model.addAttribute("retailStores", loginService.getAllRetailStores());
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        Manager manager = employeeService.loginManager(loginRequest.getUsername()+"@gmail.com", loginRequest.getPassword());
        Salesperson salesperson = employeeService.loginSalesperson(loginRequest.getUsername()+"@gmail.com", loginRequest.getPassword());

        Map<String, String> response = new HashMap<>();

        if (manager == null && salesperson == null) {
            response.put("action", "invalid");
        } else {
            String token = JsonTokenUtil.generateToken(loginRequest.getUsername()+"@gmail.com", loginRequest.getUsername(), manager == null ? "salesperson" : "admin");
            response.put("token", token);
            response.put("action", "valid");
        }
        return ResponseEntity.ok(response.get("action"));
    }

    @PostMapping("/loginStore")
    @ResponseBody
    public ResponseData loginWithStore(@RequestBody RegularLoginRequest loginRequest) {
        Manager manager = employeeService.loginManagerByStore(loginRequest.getUsername()+"@gmail.com", loginRequest.getPassword(), loginRequest.getRetailStoreId());
        Salesperson salesperson = employeeService.loginSalespersonByStore(loginRequest.getUsername()+"@gmail.com", loginRequest.getPassword(), loginRequest.getRetailStoreId());
        Map<String, String> response = new HashMap<>();

        if (manager == null && salesperson == null) {
            response.put("action", "invalid");
        } else {
            String token = JsonTokenUtil.generateToken(loginRequest.getUsername()+"@gmail.com", loginRequest.getUsername(), manager == null ? "salesperson" : "admin", loginRequest.getRetailStoreId());
            response.put("token", token);
            response.put("action", "valid");
            if(salesperson != null){
                if(!salesperson.isStatus()){
                    return new ResponseData("Please login using the link", "invalid", false, 400, null, null);
                }
                else{
                    return new ResponseData("Login Success", salesperson, true, 200, response.get("token"), "salesperson");
                }
            }
            return new ResponseData("Login Success", manager, true, 200, response.get("token"), "admin");
        }
        return new ResponseData("Login Failed", "invalid", false, 400, null, null);
    }
    @GetMapping("/loginNewAccount")
    public String loginNewAccount(@RequestParam(name = "token") String token,Model model){
        boolean isValid = JsonTokenUtil.tokenStillValid(token);
        if(isValid){
            model.addAttribute("token", token);
            return "employee/new-account-login";
        }
        else{
            return "redirect:/expired.html";
        }
    }

    @PostMapping("/loginNewAccount")
    @ResponseBody
    public ResponseData loginNewAccount(@RequestBody LoginNewAccountRequest loginRequest){
        Salesperson salesperson = employeeService.loginSalesperson(loginRequest.getUsername()+"@gmail.com", loginRequest.getPassword());
        System.out.println(salesperson);
        if(!JsonTokenUtil.tokenStillValid(loginRequest.getToken())){
            return new ResponseData("Token Expired", "invalid", false, 400, null, null);
        }

        String token;
        if (salesperson == null) {
            return new ResponseData("Login Failed", "invalid", false, 400, null, null);
        } else {
            salesperson.setStatus(true);
            employeeService.updateSalespersonStatus(salesperson);
            token = JsonTokenUtil.generateToken(loginRequest.getUsername()+"@gmail.com", loginRequest.getUsername(),  "salesperson", salesperson.getRetailStore().getId());
        }
        return new ResponseData("Login Success", salesperson, true, 200, token, "salesperson");
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
class LoginRequest{
    private String username;
    private String password;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
class RegularLoginRequest{
    private String username;
    private String password;
    private int retailStoreId;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
class LoginNewAccountRequest{
    private String username;
    private String password;
    private String token;
}