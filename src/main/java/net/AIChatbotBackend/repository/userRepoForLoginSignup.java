package net.AIChatbotBackend.repository;

import net.AIChatbotBackend.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface userRepoForLoginSignup extends MongoRepository<User, String> {
    //we have to add the functions that are not present in this interface already(like, our custom function)
    //just we have to follow some instructions by which we have to create those functions
    //baki implementation  interface apne aap kardega
    //jaise ki we can find the documents with the help of id but, if we want to find the
    //documents with email then we would just add a method decalaration as
    // findByEmail() may be (by some naming convention, don't worry would learn!)

public Optional<User> findByEmail(String email);
}
