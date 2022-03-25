package com.msgrJava.entities;

import com.msgrJava.ENUMS.RequestMessage;
import com.msgrJava.ENUMS.ResponseMessage;
import com.msgrJava.exceptions.chatException.ChatError;
import com.msgrJava.exceptions.chatException.MessageError;

import java.sql.Time;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EntityChat {

    private Long id;
    //todo implement this for several users in one chat
    private List<EntityUser> users;
    private LinkedList<Message> messages;
    //todo create a one WILD map where users who've read messages are noted
    private Map<Long, List<EntityUser>> unreadMessages;

    //Constructor
    public EntityChat() {}

    //Methods
    public ResponseMessage createNewMessage(EntityUser user, String message) throws ChatError {
        try {
            new Message(user, message);
            return ResponseMessage.CREATED;
        } catch (MessageError e ){
            throw new ChatError("Failed to create new message(EntityChat class): MessageError " + e.getMessage());
        } catch (Exception e) {
            throw new ChatError("Failed to create new message(EntityChat class): Exception" + e.getMessage());
        }
    }

    //todo finish this
    public LinkedList<Message> getMessages(RequestMessage request) throws ChatError {
            switch (request) {
                case UNREADONLY:
                    return new LinkedList<Message>();
                case ALLMESSAGES:
                    return new LinkedList<Message>();
                case MODIFIEDMESSAGES:
                    return new LinkedList<Message>();
                default:
                    throw new ChatError("Unexpected request during getMessage in Entity chat");
            }
    }


    //todo finish this
    public ResponseMessage modifyMessage(EntityUser user, Long messageId , String corerectedMessage) throws ChatError {
        try {
            //messages.get(messageId);

            return ResponseMessage.MODIFIED;
      /*  } catch (MessageError e) {
            throw new ChatError("Failed to modify new message(EntityChat class): MessageError " + e.getMessage());

       */
        } catch (Exception e) {
            throw new ChatError("Failed to modify new message(EntityChat class): Exception" + e.getMessage());
        }
    }




    private class Message {
        private Long id;
        private EntityUser sender;
        private String message;
        private Time created;
        private boolean modified;
        private Time timeModified;
        private boolean deleted;
        private Time timeDeleted;
        private Map<Time, String> history;
        private Map<EntityUser, Boolean> messageIsRead;

        Message (EntityUser sender, String message) throws MessageError {
            try {
                this.message = message;
                this.sender = sender;
                created = Time.valueOf(LocalTime.now());
            } catch (Exception e) {
                throw new MessageError("Failed to create new message(Message class): " + e.getMessage());
            }
        }

        //todo create get method for message with implementation of messageIsRead function;

        ResponseMessage modifyMessage(String message) throws MessageError {
            //Initialize history for message, if doesn't exist
            if (history == null) history = new LinkedHashMap<>();
            Time modifyTime = Time.valueOf(LocalTime.now());
            try {
                history.put(modifyTime, this.message);
                this.message = message;
                this.timeModified = modifyTime;
                modified = true;
                return ResponseMessage.MODIFIED;
            } catch (Exception e) {
                throw new MessageError("Failed to modify message with id " + id + ": " + e.getMessage());
            }
        }

        ResponseMessage deleteMessage() throws MessageError {
            try {
                Time deleteTime = Time.valueOf(LocalTime.now());
                history.put(deleteTime,  "Message was deleted at " + deleteTime);
                message = "Message was deleted at " + deleteTime;
                timeDeleted = deleteTime;
                deleted = true;
                return ResponseMessage.DELETED;
            } catch (Exception e) {
                throw new MessageError("Failed to delete message with id " + id + ": " + e.getMessage());
            }
        }
    }

}
