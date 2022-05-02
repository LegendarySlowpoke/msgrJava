package com.msgrJava.entities;

import com.msgrJava.ENUMS.ResponseMessage;
import com.msgrJava.exceptions.chatException.ChatError;
import com.msgrJava.exceptions.chatException.MessageError;

import javax.persistence.*;
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
    private String chatName;


    //Creator id number
    @OneToOne
    private EntityUser creatorEntity;
    @OneToOne
    private EntityUser invitedUser;

    private Long lastMessageNumb = 0L;

    @OneToMany(mappedBy = "chat")
    private List<EntityMessage> messageList = Collections.synchronizedList(new LinkedList<>());

    //Grants administrative functions to all Users in chat
    //@JoinColumn(name="democracy")
    private boolean democracy;


    //====================================================================================
    //====================================================================================
    //Constructors
    public EntityChat() {}

    public EntityChat(EntityUser creatorUser, EntityUser invitedUser, String chatName) throws ChatError {
        if (chatName == null || chatName.equals("noChatNaMe")) chatName = "Chat";

        this.creatorEntity = creatorUser;
        this.invitedUser = invitedUser;
        this.lastMessageNumb = 0L;
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


    //====================================================================================
    //====================================================================================
    //Methods for working with messages in this chat
    public EntityMessage createNewMessage(EntityUser user, String message) throws ChatError {
        try {
            EntityMessage messageEntity = new EntityMessage(user, this, message, (lastMessageNumb + 1));
            this.lastMessageNumb = messageEntity.getChatPositionId();
            messageList.add(messageEntity);
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

    public EntityUser getInvitedUser() {return invitedUser;}

    public void setInvitedUser(EntityUser invitedUser) {this.invitedUser = invitedUser;}

    public Long getLastMessageNumb() {return lastMessageNumb;}

    public void setLastMessageNumb(Long lastMessageNumb) {this.lastMessageNumb = lastMessageNumb;}

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
                ", lastMessageId=" + lastMessageNumb +
                ", invitedUser=" + invitedUser +
                ", democracy=" + democracy +
                '}';
    }
}
