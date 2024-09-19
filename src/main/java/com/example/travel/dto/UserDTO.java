package com.example.travel.dto;

import com.example.travel.model.User;
import com.example.travel.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public class UserDTO {
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserDTO(String email) {
        this.email = email;
    }

    public String getUser() {
        return this.email;
    }
}
