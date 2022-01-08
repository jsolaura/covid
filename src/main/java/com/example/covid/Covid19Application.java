package com.example.covid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Covid19Application {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(Covid19Application.class, args);
    }
}
