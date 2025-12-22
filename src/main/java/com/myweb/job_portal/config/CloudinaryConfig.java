package com.myweb.job_portal.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dufj4uflm");
        config.put("api_key", "362312464898855");
        config.put("api_secret", "HvrjD_eOD-9JBqEgEaX-gpmY64w");
        return new Cloudinary(config);
    }
}
