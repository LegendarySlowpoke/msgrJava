package com.msgrJava.entities;

import com.msgrJava.ENUMS.ResponseMessage;
import com.msgrJava.exceptions.chatException.MessageError;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="chat")
public class EntityMessage {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @OneToMany
        @JoinColumn(name = "sender_id", nullable = false)
        private List<EntityUser> sender;
        private String message;
        private Time created;
        private boolean modified;
        private Time timeModified;
        private boolean deleted;
        private Time timeDeleted;

        //todo Should be implemented or ommited
        //private Map<Time, String> history;
        //private Map<EntityUser, Boolean> messageIsRead;

    public EntityMessage() {
    }

    public List<EntityUser> getSender() {
        return sender;
    }

    EntityMessage (EntityUser sender, String message) throws MessageError {
            try {
                this.message = message;
                if (sender == null) this.sender = new LinkedList<EntityUser>();
                this.sender.add(sender);
                created = Time.valueOf(LocalTime.now());
            } catch (Exception e) {
                throw new MessageError("Failed to create new message(Message class): " + e.getMessage());
            }
        }

        //todo create get method for message with implementation of messageIsRead function;

        ResponseMessage modifyMessage(String message) throws MessageError {
            //Initialize history for message, if doesn't exist
           // if (history == null) history = new LinkedHashMap<>();
            Time modifyTime = Time.valueOf(LocalTime.now());
            try {
            //    history.put(modifyTime, this.message);
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
           //     history.put(deleteTime,  "Message was deleted at " + deleteTime);
                message = "Message was deleted at " + deleteTime;
                timeDeleted = deleteTime;
                deleted = true;
                return ResponseMessage.DELETED;
            } catch (Exception e) {
                throw new MessageError("Failed to delete message with id " + id + ": " + e.getMessage());
            }
        }
    }
