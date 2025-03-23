package net.AIChatbotBackend.controllers;

import jakarta.validation.Valid;
import net.AIChatbotBackend.models.User;
import net.AIChatbotBackend.service.CustomUserDetailsService;
import net.AIChatbotBackend.service.userServiceForloginSignup;
import net.AIChatbotBackend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/public")
public class controllerForUserLoginSignup {

    @Autowired
    private userServiceForloginSignup userService;
    @Autowired
    CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    private PasswordEncoder passwordEncoder;


    // Signup API
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> userSignup(@Valid @RequestBody User userDetails) {
        Optional<User> userExists = userService.getUserByEmail(userDetails.getEmail());

        if (userExists.isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message","user already exists!"));
        }

        // Password Hashing
        userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
//userDetails.setRoles("user");
        userService.saveUser(userDetails);
        return ResponseEntity.ok(Map.of("message","Signup complete!"));
    }

    //Login API (Without JWT)
    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody User userDetails) {
        try {
            // Authenticate user using Spring Security
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDetails.getEmail(), userDetails.getPassword())
            );

            // Load user from UserDetailsService using email
            UserDetails loadedUser = userDetailsService.loadUserByUsername(userDetails.getEmail());

            // Generate JWT token using email
            String token = jwtUtil.generateToken(loadedUser.getUsername());

            return ResponseEntity.ok(token);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body("Invalid credentials!");
        }
    }}