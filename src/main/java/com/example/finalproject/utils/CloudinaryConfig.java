package com.example.finalproject.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dhpxifsfm",
                "api_key", "357415218746838",
                "api_secret", "faZtm3aJ8BAxoJ-ZG6psQCbqD7E",
                "secure", true));
    }
}

