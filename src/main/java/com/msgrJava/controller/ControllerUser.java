package com.msgrJava.controller;


import com.msgrJava.entities.EntityUser;
import com.msgrJava.exceptions.clientSide.RegistrationDataError;
import com.msgrJava.exceptions.clientSide.UserAlreadyExistsException;
import com.msgrJava.exceptions.clientSide.UserNotFoundException;
import com.msgrJava.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//todo THE CONTROLLER CLASS IS FOR SENDING DATA AND RECEIVING REQUESTS

@RestController
@RequestMapping("/user")
public class ControllerUser {

        @Autowired
        private ServiceUser userService;


        @PostMapping("registration")
        public ResponseEntity registration(@RequestBody EntityUser newUser) {
                try {
                        System.out.println("User registration request received: " + newUser.toString());
                        userService.registration(newUser);
                        System.out.println("Successful registration!");
                        return ResponseEntity.ok("Successful registration!");
                } catch (RegistrationDataError | UserAlreadyExistsException e) {
                        System.out.println("Registration error: " + e.getMessage());
                        return ResponseEntity.badRequest().body(e.getMessage());
                } catch (Exception e) {
                        System.out.println("Unhandled: " + e.getMessage());
                        return ResponseEntity.badRequest().body("Error has happen: " + e.getMessage());
                }
        }

        @GetMapping("/findbyid")
        public ResponseEntity getOneUser(@RequestParam long id) {
                try {
                        System.out.println("Request for User with id " + id + " received");
                        return ResponseEntity.ok(userService.getOne(id));
                } catch (UserNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                        return ResponseEntity.badRequest().body("User was not found =(");
                } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        return ResponseEntity.badRequest().body("Error has happen: " + e.getMessage());
                }
        }

        @DeleteMapping("/delete")
        public ResponseEntity deleteUser(@RequestParam Long id) {
                try {
                        System.out.println("Request for DELETING User with id " + id + " received");
                        return ResponseEntity.ok(userService.delete(id));
                } catch (UserNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                        return ResponseEntity.badRequest().body("User to delete with id " + id + " was not found =(");
                } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        return ResponseEntity.badRequest().body("Error has happen: " + e.getMessage());
                }
        }
}
