package com.msgrJava.controller;

import com.msgrJava.exceptions.chatException.ChatError;
import com.msgrJava.service.ServiceChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ControllerChat {
    //todo Maybe we should not need ServiceUser here...
    //@Autowired
    //private ServiceUser userService;
    @Autowired
    private ServiceChat chatService;

    @PostMapping("/create")
    public ResponseEntity createChat(@RequestParam long creatorId, long invitedId, String chatName, String message) {
        try {
            System.out.println("Chat registration request received: creatorId " + creatorId +
                    ", invitedId " + invitedId + ", chatName null:" + (chatName == null)
                    + ", message null:" + (message == null));
            chatService.createNewChat(creatorId, invitedId, chatName, message);
            System.out.println("Chat created!");
            return ResponseEntity.ok("Chat created!");
        } catch (ChatError e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/changeName")
    public ResponseEntity changeChatName(@RequestParam long chatId, long senderId, String chatName) {
        try {
            System.out.println("Request for changing chat name (chat id: " + chatId + ") with '" +
                            chatName + "' from user with id " + senderId + " received");
            return ResponseEntity.ok(chatService.changeChatName(chatId, senderId, chatName));
        } catch (ChatError e) {
            return ResponseEntity.badRequest().body("Unable to change chat name: " + e.getMessage());
        }
    }

    //todo adding user method should be implemented one day...
    // public ResponseEntity addUserToChat(@RequestParam long chatId, long userId) {}

    @PostMapping("/changeDemocracy")
    public ResponseEntity changeDemocracy(@RequestParam long chatId, long senderId) {
        try {
            System.out.println("Request for changing chat democracy (chat id: " + chatId + ")" +
                    " from user with id " + senderId + " received");
            return ResponseEntity.ok(chatService.changeDemocracy(chatId, senderId));
        } catch (ChatError e) {
            return ResponseEntity.badRequest().body("Unable to change chat democracy: " + e.getMessage());
        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity deleteChat(@RequestParam long chatId, long sendersId) {
        try {
            System.out.println("Request for DELETING chat with id " + chatId + " from user" +
                    " with id " + sendersId + " received");
            return ResponseEntity.ok(chatService.delete(chatId, sendersId));
        } catch (ChatError e) {
            return ResponseEntity.badRequest().body("Unable to delete: " + e.getMessage());
        }
    }
}
