package com.msgrJava.entities;

import com.msgrJava.ENUMS.RequestMessage;
import com.msgrJava.ENUMS.ResponseMessage;
import com.msgrJava.exceptions.chatException.ChatError;
import com.msgrJava.exceptions.chatException.MessageError;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "chat")
public class  EntityChat {

    //Chat info
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Chat name
    @JoinColumn(name = "chatName")
    private String chatName;
    private Time timeCreated;

    //Creators info
    @OneToOne
    @JoinColumn(name= "creatorEntity")
    private EntityUser creatorEntity;
    //List of all users in chat
    @ManyToMany
    @JoinColumn(name = "userList")
    private List<EntityUser> usersList = new ArrayList<>();

    //List of all messages in chat
    @OneToMany (mappedBy="chat")
    private List<EntityMessage> messages;

    //Grants administrative functions to all Users in chat
    @JoinColumn(name="democracy")
    boolean democracy;


    //====================================================================================
    //====================================================================================
    //Constructors
    public EntityChat() {}

    public EntityChat(EntityUser creatorUser, EntityUser invitedUser, String chatName, String message) throws ChatError {
        if (chatName == null || chatName.equals("noChatNaMe")) chatName = "Chat";
        this.timeCreated = Time.valueOf(LocalTime.now());
        this.creatorEntity = creatorUser;
        usersList.add(invitedUser);
        this.chatName = chatName;

        if (message == null || message.equals("firstMessageIsEmPtY")) {
            message = "User " + creatorUser.getUserTAG() + " invited you to chat!";
        }
        createNewMessage(creatorUser, message);
    }



    //====================================================================================
    //====================================================================================
    //Methods for working with this chat
    public ResponseMessage changeChatName(String chatName) throws ChatError {
        try {
            setChatName(chatName);
            return ResponseMessage.MODIFIED;
        } catch (Exception e) {
            throw new ChatError("Failed to change chat name: " + e.getMessage());
        }
    }

    public ResponseMessage addUserToChat(EntityUser visitedUser) throws ChatError {
        try {
            this.usersList.add(visitedUser);
            return ResponseMessage.MODIFIED;
        } catch (Exception e) {
            throw new ChatError("Unable to add user with id " + visitedUser.getId());
        }
    }

    public ResponseMessage removeUserFromChat(EntityUser userToRemove) throws ChatError {
        try {
            this.usersList.remove(userToRemove);
            return ResponseMessage.MODIFIED;
        } catch (Exception e) {
            throw new ChatError("Unable to remove user with id " + userToRemove.getId());
        }
    }


    //====================================================================================
    //====================================================================================
    //Methods for working with messages in this chat
    public EntityMessage createNewMessage(EntityUser user, String message) throws ChatError {
        try {
            return new EntityMessage(user, this, message);
        } catch (MessageError e ){
            throw new ChatError("Failed to create new message(EntityChat class): MessageError " + e.getMessage());
        } catch (Exception e) {
            throw new ChatError("Failed to create new message(EntityChat class): Exception" + e.getMessage());
        }
    }

    //todo finish this
    public LinkedList<EntityMessage> getMessages(RequestMessage request) throws ChatError {
            switch (request) {
                case UNREADONLY:
                    return new LinkedList<EntityMessage>();
                case ALLMESSAGES:
                    return new LinkedList<EntityMessage>();
                case MODIFIEDMESSAGES:
                    return new LinkedList<EntityMessage>();
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


    //SETTERS AND GETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChatName() {
        return chatName;
    }

    private void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public EntityUser getCreatorEntity() {
        return creatorEntity;
    }

    public void setCreatorEntity(EntityUser creatorEntity) {
        this.creatorEntity = creatorEntity;
    }

    public List<EntityUser> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<EntityUser> usersList) {
        this.usersList = usersList;
    }

    public boolean isDemocracy() {
        return democracy;
    }

    public void setDemocracy(boolean democracy) {
        this.democracy = democracy;
    }
}
