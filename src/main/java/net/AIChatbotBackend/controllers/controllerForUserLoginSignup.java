package net.AIChatbotBackend.controllers;

import net.AIChatbotBackend.models.User;
import net.AIChatbotBackend.service.userServiceForloginSignup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/public")
public class controllerForUserLoginSignup {

    @Autowired
    private userServiceForloginSignup userService;


    @Autowired
    private PasswordEncoder passwordEncoder;


    // Signup API
    @PostMapping("/signup")
    public ResponseEntity<String> userSignup(@RequestBody User userDetails) {
        Optional<User> userExists = userService.getUserByEmail(userDetails.getEmail());

        if (userExists.isPresent()) {
            return ResponseEntity.badRequest().body("User already exists!");
        }

        // Password Hashing
        userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));

        userService.saveUser(userDetails);
        return ResponseEntity.ok("Sign up complete!");
    }

    //Login API (Without JWT)
    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody User userDetails) {
        Optional<User> userExists = userService.getUserByEmail(userDetails.getEmail());

        if (userExists.isPresent()) {
            User user = userExists.get();

            //Password verification
            if (passwordEncoder.matches(userDetails.getPassword(), user.getPassword())) {
                return ResponseEntity.ok("Login successful! Welcome " + user.getEmail());
            } else {
                return ResponseEntity.status(401).body("Invalid password!");
            }
        } else {
            return ResponseEntity.status(404).body("User not found!");
        }
    }
}
