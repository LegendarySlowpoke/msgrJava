package com.msgrJava.controller;


import com.msgrJava.crypt.PassHasher;
import com.msgrJava.entities.EntityUser;
import com.msgrJava.exceptions.userExceptions.RegistrationDataError;
import com.msgrJava.exceptions.userExceptions.UserAlreadyExistsException;
import com.msgrJava.exceptions.userExceptions.UserNotFoundException;
import com.msgrJava.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// THE CONTROLLER CLASS IS FOR SENDING DATA AND RECEIVING REQUESTS

@RestController
@RequestMapping("/user")
public class ControllerUser {

        @Autowired
        private ServiceUser userService;


        @PostMapping("registration")
        public ResponseEntity registration(@RequestBody EntityUser newUser) {
                try {
                        //todo should find better place for encrypting
                        newUser.setPassHash(PassHasher.encryptThisString(newUser.getPassHash()));
                        System.out.println("User registration request received: " + newUser.toString() +
                                "\n\t\t" + newUser.getInfoPreInit());
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
        public ResponseEntity getById(@RequestParam long id) {
                try {
                        System.out.println("Request for User with id " + id + " received");
                        return ResponseEntity.ok(userService.getById(id));
                } catch (UserNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                        return ResponseEntity.badRequest().body("User was not found =(");
                } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        return ResponseEntity.badRequest().body("Error has happen: " + e.getMessage());
                }
        }

        @GetMapping("/findbyphonenumber")
        public ResponseEntity getByPhoneNumber(@RequestParam String phone) {
                try {
                        System.out.println("Request for User with phone " + phone + " received");
                        return ResponseEntity.ok(userService.getByPhone(phone));
                } catch (UserNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                        return ResponseEntity.badRequest().body("User was not found =(");
                } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        return ResponseEntity.badRequest().body("Error has happen: " + e.getMessage());
                }
        }

        @GetMapping("/findByUserTAG")
        public ResponseEntity findByUserTAG(@RequestParam String userTag) {
                try {
                        System.out.println("Request for User with userTag " + userTag + " received");
                        return ResponseEntity.ok(userService.getByUserTAG(userTag));
                } catch (UserNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                        return ResponseEntity.badRequest().body("User was not found =(");
                } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        return ResponseEntity.badRequest().body("Error has happen: " + e.getMessage());
                }
        }

        @GetMapping("/findByEmail")
        public ResponseEntity findByEmail(@RequestParam String email) {
                try {
                        System.out.println("Request for User with email " + email + " received");
                        return ResponseEntity.ok(userService.getByEmail(email));
                } catch (UserNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                        return ResponseEntity.badRequest().body("User was not found =(");
                } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        return ResponseEntity.badRequest().body("Error has happen: " + e.getMessage());
                }
        }

        //todo remote deleting option should be enabled only for OWNER of this id.
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
