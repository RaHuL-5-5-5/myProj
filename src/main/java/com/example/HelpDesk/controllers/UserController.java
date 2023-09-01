package com.example.HelpDesk.controllers;
import com.example.HelpDesk.model.User;
import com.example.HelpDesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.HelpDesk.passwords.MD5;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@CrossOrigin
@RestController

public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/signin")
    public ResponseEntity<Object> signIn(@RequestBody User user) {
        try {

            String encodedPass = MD5.getMd5(user.getPassword());
            Optional<User> userData = userRepository.findByEmailAndPassword(user.getEmail(), encodedPass);
            if (userData.isPresent()) {
                userData.get().setPassword(null);

                return new ResponseEntity<>(userData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("The user does not exist.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        System.out.println("here");
        System.out.println(user.getName()+" "+user.getEmail()+" "+user.getPassword());
        try {
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                return new ResponseEntity<>("The user already exisits", HttpStatus.CONFLICT);
            }

            String encodedPass = MD5.getMd5(user.getPassword());

            User _user = userRepository.save(
                    new User(user.getName(), user.getEmail(), encodedPass));

            _user.setPassword(null);

//            // Send email
//            String subject = "Welcome to TCS";
//            String body = "Hello " + _user.getName() + ",\n\n"
//                    + "Welcome to TCS. We are glad to have yo+u on board.\n\n";
//
//            EmailDetails emailDetails = new EmailDetails(_user.getEmail(), body, subject, null);
//            String status = emailService.sendSimpleMail(emailDetails);
//
//           System.out.println("Email Status: " + status);
            System.out.println("here");
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
