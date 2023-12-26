package com.example.finalproject.controller;

import com.example.finalproject.utils.JsonTokenUtil;
import com.example.finalproject.utils.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/token")
public class TokenController {
    @PostMapping("/checkToken")
    public ResponseData checkToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        boolean valid = JsonTokenUtil.validateToken(token);
        if(!valid){
            return new ResponseData("Invalid Token", null, false, 400, null, null);
        }
        String email = JsonTokenUtil.getEmailFromToken(token);
        String username = JsonTokenUtil.getUsernameFromToken(token);
        String role = JsonTokenUtil.getRoleFromToken(token);
        if(role.equals("admin") || role.equals("salesperson")){
            int storeId = JsonTokenUtil.getStoreIdFromToken(token);
            HashMap<String, String> response = new HashMap<>();
            response.put("email", email);
            response.put("username", username);
            response.put("role", role);
            response.put("storeId", String.valueOf(storeId));
            return new ResponseData("Valid Token", response, true, 200, null, null);

        }
        HashMap<String, String> response = new HashMap<>();
        response.put("email", email);
        response.put("username", username);
        response.put("role", role);
        return new ResponseData("Valid Token", response, true, 200, null, null);
    }

}
