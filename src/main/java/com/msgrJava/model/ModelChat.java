package com.msgrJava.model;


import com.msgrJava.entities.EntityChat;

import java.util.ArrayList;
import java.util.List;


//@JsonSerialize
public class ModelChat {

    private Long id;
    private String chatName;
    private ModelUser creatorModel;
    private ModelUser invitedModel;

    boolean democracy;

    public static ModelChat toModelChat(EntityChat userChat) {
        ModelChat chat = new ModelChat();
        chat.setId(userChat.getId());
        chat.setChatName(userChat.getChatName());
        chat.setCreatorModel(ModelUser.toModel(userChat.getCreatorEntity()));
        chat.setInvitedModel(ModelUser.toModel(userChat.getInvitedUser()));
        chat.setDemocracy(userChat.isDemocracy());
        return chat;
    }

    public static List<ModelChat> toModelChatList(List<EntityChat> entityChatList) {
        List<ModelChat> result = new ArrayList<>();
        for (EntityChat entityChat : entityChatList) {
            result.add(toModelChat(entityChat));
        }

        return result;
    }

    @Override
    public String toString() {
        return "ModelChat{" +
                "id=" + id +
                ", chatName='" + chatName + '\'' +
                ", creatorModel=" + creatorModel.infoString() +
                ", invitedModel=" + invitedModel.infoString() +
                ", democracy=" + democracy +
                '}';
    }

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

    public ModelUser getCreatorModel() {
        return creatorModel;
    }

    public void setCreatorModel(ModelUser creatorModel) {
        this.creatorModel = creatorModel;
    }

    public ModelUser getInvitedModel() {
        return invitedModel;
    }

    public void setInvitedModel(ModelUser invitedModel) {
        this.invitedModel = invitedModel;
    }

    public boolean isDemocracy() {
        return democracy;
    }

    public void setDemocracy(boolean democracy) {
        this.democracy = democracy;
    }
}
