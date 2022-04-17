package com.msgrJava.entities;

import com.msgrJava.ENUMS.RequestMessage;
import com.msgrJava.ENUMS.ResponseMessage;
import com.msgrJava.exceptions.chatException.ChatError;
import com.msgrJava.exceptions.chatException.MessageError;
import com.msgrJava.repository.RepoMessage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "chat")
public class EntityChat {

    //Chat id number
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Chat name
    //@JoinColumn(name = "chatName")
    private String chatName;

    //Creator id number
    @OneToOne
    //@JoinColumn(name= "creatorEntity")
    private EntityUser creatorEntity;
    //@JoinColumn(name= "lastMessageId")
    private Long lastMessageId;
    //List of all users in chat
    @ManyToMany
    //@JoinColumn(name = "userList")
    private List<EntityUser> usersList = Collections.synchronizedList(new LinkedList<>());

    //List of all messages in chat
    @OneToMany
    //@JoinColumn(name="messages", nullable = false)
    private List<EntityMessage> messages = Collections.synchronizedList(new LinkedList<>());

    //Grants administrative functions to all Users in chat
    //@JoinColumn(name="democracy")
    boolean democracy;


    //====================================================================================
    //====================================================================================
    //Constructors
    public EntityChat() {}

    /*
    public EntityChat(EntityUser creatorUser, EntityUser invitedUser, String message, String chatName) throws ChatError {
        if (chatName == null || chatName.equals("noChatNaMe")) chatName = "Chat";

        this.creatorEntity = creatorUser;
        usersList.add(invitedUser);
        this.lastMessageId = 0L;
        this.chatName = chatName;
        //todo remove this
        if (message == null || message.equals("firstMessageIsEmPtY")) {
            message = "User " + creatorUser.getUserTAG() + " invited you to chat!";
        }
        //messages = new LinkedList<>();
        EntityMessage messageEntity = createNewMessage(creatorUser, message);
        lastMessageId = messageEntity.getId();
        messages.add(messageEntity);
    }
     */
    public EntityChat(EntityUser creatorUser, EntityUser invitedUser, String chatName) throws ChatError {
        if (chatName == null || chatName.equals("noChatNaMe")) chatName = "Chat";

        this.creatorEntity = creatorUser;
        usersList.add(invitedUser);
        this.lastMessageId = 0L;
        this.chatName = chatName;

    }



    //====================================================================================
    //====================================================================================
    //Methods for working with this chat
    public ResponseMessage changeChatName(String chatName) throws ChatError {
        try {
            this.chatName = chatName;
            return ResponseMessage.MODIFIED;
        } catch (Exception e) {
            throw new ChatError("Failes to change chat name: " + e.getMessage());
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
    //Methods for getting Messages from chat
    public List<EntityMessage> getAllMessages() throws ChatError {
        return this.messages;
    }

    public LinkedList<EntityMessage> getUnreadMessages(Long lastReceivedMessageId) throws ChatError {
        LinkedList<EntityMessage> unreadMessages = new LinkedList<>();
        //check if there is no unread messages
        if (!this.lastMessageId.equals(lastReceivedMessageId)) {
            try {
                for (int i = lastReceivedMessageId.intValue() + 1;
                     i < (this.lastMessageId - lastReceivedMessageId); i++) {
                    unreadMessages.add(messages.get(i));
                }
                return unreadMessages;
            } catch (Exception e) {
                e.printStackTrace();
                throw new ChatError("Unhandled exception " + e.getMessage());
            }
        }
        return unreadMessages;
    }

    //====================================================================================
    //====================================================================================
    //Methods for working with messages in this chat
    public EntityMessage createNewMessage(EntityUser user, String message) throws ChatError {
        try {
            EntityMessage messageEntity = new EntityMessage(user, this, message, (lastMessageId + 1));
            this.lastMessageId = messageEntity.getChatPositionId();
            return messageEntity;
        } catch (MessageError e ){
            throw new ChatError("Failed to create new message(EntityChat class): MessageError " + e.getMessage());
        } catch (Exception e) {
            throw new ChatError("Failed to create new message(EntityChat class): Exception" + e.getMessage());
        }
    }

    //todo and this (finish)
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

    public void setChatName(String chatName) {
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

    public List<EntityMessage> getMessages() {
        return messages;
    }

    public boolean isDemocracy() {
        return democracy;
    }

    public void setDemocracy(boolean democracy) {
        this.democracy = democracy;
    }

    public String toStringFull() {
        return "EntityChat{" +
                "id=" + id +
                ", chatName='" + chatName + '\'' +
                ", creatorEntity=" + creatorEntity +
                ", lastMessageId=" + lastMessageId +
                ", usersList=" + usersList +
                ", messages=" + messages +
                ", democracy=" + democracy +
                '}';
    }
}
