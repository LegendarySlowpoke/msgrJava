package com.msgrJava.controller;


import com.msgrJava.crypt.PassHasher;
import com.msgrJava.entities.EntityUser;
import com.msgrJava.exceptions.chatException.ChatError;
import com.msgrJava.exceptions.userExceptions.RegistrationDataError;
import com.msgrJava.exceptions.userExceptions.UserAlreadyExistsError;
import com.msgrJava.exceptions.userExceptions.UserLogInError;
import com.msgrJava.exceptions.userExceptions.UserNotFoundError;
import com.msgrJava.model.ModelUserOwner;
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

        //Methods for ModelOwner
        //todo add mac address(hash)
        @PostMapping("/registration")
        public ResponseEntity registration(@RequestBody EntityUser newUser) {
                try {
                        newUser.setPassHash(PassHasher.encryptThisString(newUser.getPassHash()));
                        System.out.println("User registration request received: " + newUser.toString() +
                                "\n\t\t" + newUser.getInfoPreInit());
                        userService.registration(newUser);
                        System.out.println("Successful registration!\nUser: "
                                + userService.getByUserTAG(newUser.getUserTAG()).toString() + "\n");
                        return ResponseEntity.ok(userService.getByUserTAG(newUser.getUserTAG()));
                } catch (RegistrationDataError | UserAlreadyExistsError e) {
                        System.out.println("Registration error: " + e.getMessage());
                        return ResponseEntity.badRequest().body(e.getMessage());
                } catch (Exception e) {
                        System.out.println("Unhandled: " + e.getMessage());
                        return ResponseEntity.badRequest().body("Error has happen: " + e.getMessage());
                } catch (UserNotFoundError e) {
                        e.printStackTrace();
                        return ResponseEntity.badRequest().body("Internal server error!");
                }
        }

        //todo add mac address
        @GetMapping("/login")
        public ResponseEntity userLogIn(@RequestParam String userTag, String pass, String deviceIdHash) {
                try {
                        System.out.println("Request for login User with tag " + userTag + " received" +
                                "\n\t\t\tpassHash: " + pass);
                        //todo change next 3 lines into 1
                        ModelUserOwner owner = userService.logIn(userTag, PassHasher.encryptThisString(pass),
                                deviceIdHash);
                        owner.printInfo();
                        return ResponseEntity.ok(owner);
                } catch (UserNotFoundError e) {
                        System.out.println(e.getMessage());
                        return ResponseEntity.badRequest().body(e.getMessage());
                } catch (UserLogInError e) {
                        System.out.println(e.getMessage());
                        return ResponseEntity.badRequest().body(e.getMessage());
                }
        }

        @GetMapping("/checkLogin")
        public ResponseEntity checkLogin(@RequestParam String userId, String deviceIdHash) {
                try {
                        System.out.println();
                        userService.logInCheck(userId, deviceIdHash);
                        return ResponseEntity.ok("kokoko");
                } catch (UserLogInError e) {
                        return ResponseEntity.badRequest().body(e.getMessage());
                } catch (Exception e) {
                        e.printStackTrace();
                        return ResponseEntity.badRequest().body((e.getMessage()));
                }
        }

        //todo remote deleting option should be enabled only for OWNER of this id.
        @DeleteMapping("/delete")
        public ResponseEntity deleteUser(@RequestParam Long id) {
                try {
                        System.out.println("Request for DELETING User with id " + id + " received");
                        return ResponseEntity.ok(userService.delete(id));
                } catch (UserNotFoundError e) {
                        System.out.println("Error: " + e.getMessage());
                        return ResponseEntity.badRequest().body("User to delete with id " + id + " was not found =(");
                } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        return ResponseEntity.badRequest().body("Error has happen: " + e.getMessage());
                }
        }

        //Getting chat list for ModelOwner
        @GetMapping("/getChatList")
        public ResponseEntity getChatList(@RequestParam Long id) {
                try {
                        System.out.println("Request for getting chatList for modelOwner received");
                        return ResponseEntity.ok(userService.getOwnerChatList(id));
                } catch (ChatError e){
                        e.printStackTrace();
                        return ResponseEntity.badRequest().body("Chat error: " + e.getMessage());
                } catch (UserNotFoundError e) {
                        e.printStackTrace();
                        return ResponseEntity.badRequest().body("User not found: " + e.getMessage());
                }
        }

        //Searching methods for users
        @GetMapping("/findbyid")
        public ResponseEntity getById(@RequestParam long id) {
                try {
                        System.out.println("Request for User with id " + id + " received");
                        return ResponseEntity.ok(userService.getById(id));
                } catch (UserNotFoundError e) {
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
                } catch (UserNotFoundError e) {
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
                } catch (UserNotFoundError e) {
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
                } catch (UserNotFoundError e) {
                        System.out.println("Error: " + e.getMessage());
                        return ResponseEntity.badRequest().body("User was not found =(");
                } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        return ResponseEntity.badRequest().body("Error has happen: " + e.getMessage());
                }
        }

}
