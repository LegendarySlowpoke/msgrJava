package com.msgrJava.controller;


import com.msgrJava.entities.EntityUser;
import com.msgrJava.exceptions.clientSide.UserAlreadyExistsException;
import com.msgrJava.exceptions.clientSide.UserNotFoundException;
import com.msgrJava.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//todo THE CONTROLLER CLASS IS FOR SENDING DATA AND RECEIVING REQUESTS

@RestController
@RequestMapping("/edit_profile")
public class ControllerUser {

        @Autowired
        private ServiceUser userService;


        @PostMapping("/registration")
        public ResponseEntity registration(@RequestBody EntityUser newUser) {
                try {
                        //todo CONTINUE FROM HERE!
                        userService.registration(newUser);
                        return ResponseEntity.ok("Successful registration!");
                } catch (UserAlreadyExistsException e) {
                        return ResponseEntity.badRequest().body(e.getMessage());
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body("Error has happen: " + e.getMessage());
                }
        }

        @GetMapping("/findbyid")
        public ResponseEntity getOneUser(@RequestParam long id) {
                try {
                        return ResponseEntity.ok(userService.getOne(id));
                } catch (UserNotFoundException e) {
                        return ResponseEntity.badRequest().body("User was not found =(");
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body("Error has happen: " + e.getMessage());
                }
        }

        @DeleteMapping("/delete{id}")
        public ResponseEntity deleteUser(@PathVariable Long id) {
                try {
                        return ResponseEntity.ok(userService.delete(id));
                } catch (UserNotFoundException e) {
                        return ResponseEntity.badRequest().body("User to delete was not found =(");
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body("Error has happen: " + e.getMessage());
                }
        }
}
