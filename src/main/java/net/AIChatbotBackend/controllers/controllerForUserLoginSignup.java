package net.AIChatbotBackend.controllers;

import net.AIChatbotBackend.models.User;
import net.AIChatbotBackend.service.userServiceForloginSignup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class controllerForUserLoginSignup {

    //ab yha pe actual api's handle ki jayengi
    @Autowired
    userServiceForloginSignup userService;
    @PostMapping("/signup")
    public String userSignup(@RequestBody User userDetails){
        Optional<User> UserExists= userService.getUserByEmail(userDetails.getEmail());
        if(UserExists.isPresent()){
            return "User already exists!";
        }else{
            userService.saveUser(userDetails);
            return "Sign up complete!";
        }
    }

    @PostMapping("/login")
    public String userLogin(@RequestBody User userDetails){
        Optional<User> userExists= userService.getUserByEmail(userDetails.getEmail());
        if(userExists.isPresent()){
            return "Login successful!";
        }else{
            return "Invalid username or password!";
        }
    }
}
