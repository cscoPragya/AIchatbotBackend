package net.AIChatbotBackend.service;

import net.AIChatbotBackend.models.User;
import net.AIChatbotBackend.repository.userRepoForLoginSignup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class userServiceForloginSignup {

    //is class ka kaam hoga controller se aane bali requets ko userrepo tak bhejna
    //jo ki actual db se connected hoga
    @Autowired
    userRepoForLoginSignup userRepo;

    public Optional<User> getUserByEmail(String email){
        //yha return type optional rakha kyuki user exists ho bhi skta hai nahi bhi
     return  userRepo.findByEmail(email);
    }

    public void saveUser(User userDetails){
        userRepo.save(userDetails);
    }
}

