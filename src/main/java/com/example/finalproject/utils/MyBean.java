package com.example.finalproject.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
public class MyBean {
    @Value("${myapp.default-image}")
    private String defaultImage;
}