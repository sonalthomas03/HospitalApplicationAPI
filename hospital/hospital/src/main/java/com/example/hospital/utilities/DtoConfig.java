package com.example.hospital.utilities;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DtoConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
