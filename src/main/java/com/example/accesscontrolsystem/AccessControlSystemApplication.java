package com.example.accesscontrolsystem;

import com.example.accesscontrolsystem.model.UserModel;
import com.example.accesscontrolsystem.repository.UserRepository;
import com.example.accesscontrolsystem.service.AcsService;
import com.example.accesscontrolsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class AccessControlSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccessControlSystemApplication.class, args);
    }


}
