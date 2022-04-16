package com.msgrJava.model;


import com.msgrJava.entities.EntityChat;
import com.msgrJava.entities.EntityMessage;
import com.msgrJava.entities.EntityUser;
import java.util.List;

public class ModelChat {

    private Long id;
    private String chatName;
    private EntityUser creatorEntity;
    private List<EntityUser> usersList;

    private List<EntityMessage> messages;
    boolean democracy;

    public static ModelChat toModelChat(EntityChat userChat) {
        ModelChat chat = new ModelChat();
        chat.id = userChat.getId();
        chat.chatName = userChat.getChatName();
        chat.creatorEntity = userChat.getCreatorEntity();
        chat.usersList = userChat.getUsersList();
        chat.messages = userChat.getMessages();
        chat.democracy = userChat.isDemocracy();
        return chat;
    }
}
