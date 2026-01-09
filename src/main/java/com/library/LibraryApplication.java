package com.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EntityScan(basePackages = {"com.library.entity"})
public class LibraryApplication {
    public static void main(String[] args) {

        SpringApplication.run(LibraryApplication.class, args);
    }
}
