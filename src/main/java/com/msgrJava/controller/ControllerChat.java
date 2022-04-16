package com.msgrJava.controller;

import com.msgrJava.exceptions.chatException.ChatError;
import com.msgrJava.exceptions.chatException.MessageError;
import com.msgrJava.exceptions.userExceptions.UserNotFoundError;
import com.msgrJava.service.ServiceChat;
import com.msgrJava.service.ServiceMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ControllerChat {

    @Autowired
    private ServiceChat chatService;
    private ServiceMessage messageService;


    //TODO REMOVE THIS METHOD. TESTING PURPOSE ONLY
    /*
    @PostMapping("/testMessage")
    public ResponseEntity testMessage() {
        System.out.println("TEST MESSAGE request received. Trying to create message in chat #3" +
                " from user #5. Message content is \"TEST MeSsAgE O_O o_o -_-\"");
        try {
            messageService.createNewMessage(5L, 3L, "TEST MeSsAgE O_O o_o -_-");
            return ResponseEntity.ok("Created, check the db.");
        } catch (MessageError e) {
            System.out.println("Unable");
            return ResponseEntity.badRequest().body("Unable");
        }
    }
     */

    //Methods for getting messages from chat
    //Getting all messages from requested chat
    @GetMapping("/getAllChatMessages")
    public ResponseEntity getChatAllMessages(@RequestParam Long userId, Long chatId) {
        try {
            System.out.println("Request for getting all messages from chat with id '" + chatId +
                    "' from user with id '" + userId + "'  received");
            //You should return LinkedList<ModelMessage>!
            return ResponseEntity.ok(chatService.getAllMessages(userId, chatId));
        } catch (UserNotFoundError e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("User exception: " + e.getMessage());
        } catch (ChatError e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Chat exception: " + e.getMessage());
        } catch (MessageError e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Message exception: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Unhandled exception: " + e.getMessage());
        }
    }

    //Checking if new messages were added. In case the were - send them back
    @GetMapping("/getNewChatMessages")
    public ResponseEntity getChatUnreadMessages(@RequestParam Long userId, Long chatId, Long lastReadMessageId) {
        try {
            System.out.println("Request for getting unread messages from chat with id '" + chatId +
                    "' from user with id '" + userId + "'  received(last read message id is '" + lastReadMessageId
                    + "'.");
            //You should return LinkedList<ModelMessage>!
            return ResponseEntity.ok(chatService.getUnreadMessages(userId, chatId, lastReadMessageId));
        } catch (UserNotFoundError e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("User exception: " + e.getMessage());
        } catch (ChatError e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Chat exception: " + e.getMessage());
        } catch (MessageError e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Message exception: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Unhandled exception: " + e.getMessage());
        }
    }


    //Methods for working with chat entity
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

    @PostMapping("addUser")
    public ResponseEntity addUser(@RequestParam long chatId, long senderId, long invitedId) {
        try {
            System.out.println("Request for adding user " + invitedId + " to chat with id "
                    + chatId + " from user" + " with id " + senderId + " received");
            return ResponseEntity.ok(chatService.addUser(chatId, senderId, invitedId));
        } catch (ChatError | UserNotFoundError e) {
            return ResponseEntity.badRequest().body("Unable to add user: " + e.getMessage());
        }
    }

    @PostMapping("/removeUser")
    public ResponseEntity removeUser(@RequestParam Long chatId, Long senderId, Long userToDeleteId) {
        try {
            System.out.println("Request for deleting user " + userToDeleteId + " from chat with id "
                    + chatId + " from user" + " with id " + senderId + " received");
            return ResponseEntity.ok(chatService.removeUser(chatId, senderId,userToDeleteId));
        } catch (ChatError | UserNotFoundError e) {
            return ResponseEntity.badRequest().body("Unable to delete user: " + e.getMessage());
        }
    }


    @DeleteMapping("/deleteChat")
    public ResponseEntity deleteChat(@RequestParam Long chatId, Long sendersId) {
        try {
            System.out.println("Request for DELETING chat with id " + chatId + " from user" +
                    " with id " + sendersId + " received");
            return ResponseEntity.ok(chatService.deleteChat(chatId, sendersId));
        } catch (ChatError e) {
            return ResponseEntity.badRequest().body("Unable to delete: " + e.getMessage());
        }
    }

    //===============================================================================================
    //===============================================================================================
    //Methods for working with message entities in chat entity
    @PostMapping("/newMessage")
    public ResponseEntity addNewMessage(@RequestParam long chatId, long senderId, String message) {
        try {
            System.out.println("Post new message request received: senderId " + senderId +
                    ", chatId " + chatId + ", message null:" + (message == null));
            //todo continue from here!
            chatService.createNewMessage(chatId, senderId, message);
            return ResponseEntity.ok("Message posted!");
        } catch (MessageError e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ChatError e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (UserNotFoundError e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
