package com.msgrJava.service;

import com.msgrJava.entities.EntityChat;
import com.msgrJava.entities.EntityUser;
import com.msgrJava.exceptions.chatException.ChatError;
import com.msgrJava.repository.RepoChat;
import com.msgrJava.repository.RepoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceChat {

    @Autowired
    private RepoChat chatRepo;
    @Autowired
    RepoUser userRepo;

    public EntityChat createNewChat(Long idCreator, Long idInvited, String chatName, String message) throws ChatError {
        //Checking if incoming data is ok
        if (idCreator == null || idInvited == null) {
            throw new ChatError("Users id number wasn't provided!");
        }
        //Searching for users entities
        EntityUser creatorUser = userRepo.getUserEntityById(idCreator);
        EntityUser invitedUser = userRepo.getUserEntityById(idInvited);
        if (creatorUser == null) throw new ChatError("Unable to find creator entity!");
        if (invitedUser == null) throw new ChatError("Unable to find invited users entity!");

        return chatRepo.save(new EntityChat(creatorUser, invitedUser, chatName, message));
    }

    public String changeChatName(Long chatId, Long senderId, String chatName) throws ChatError {
        try {
            EntityChat chatToChangeName = chatRepo.getEntityChatById(chatId);
            if (chatToChangeName == null) throw new ChatError("Specified chat wasn't found!");
            if (chatName == null) chatName = "Chat";
            if (chatToChangeName.getCreatorEntity() == null) {
                chatToChangeName.setDemocracy(true);
                chatToChangeName.changeChatName(chatName);
                chatRepo.save(chatToChangeName);
                return "Chat name was changed on " + chatToChangeName.getChatName() + ". There is no creator in this " +
                        "chat, democracy set to true!";
            } else if (chatToChangeName.getCreatorEntity().getId().equals(senderId) || chatToChangeName.isDemocracy()) {
                chatToChangeName.changeChatName(chatName);
                chatRepo.save(chatToChangeName);
                return "Chat name was changed on " + chatToChangeName.getChatName();
            } else {
                throw new ChatError("You don't have permission to change name of this chat!");
            }
        } catch (Exception e) {
            throw new ChatError(e.getMessage());
        }
    }

    public String changeDemocracy(long chatId, long senderId) throws ChatError {
        try {
            EntityChat chatToChangeDemocracy = chatRepo.getEntityChatById(chatId);
            if (chatToChangeDemocracy == null) throw new ChatError("Specified chat wasn't found!");
            if (chatToChangeDemocracy.getCreatorEntity() == null) {
                chatToChangeDemocracy.setDemocracy(true);
                chatRepo.save(chatToChangeDemocracy);
                return "Chat with id " + chatId + ": creator doesn't exist, " +
                        "democracy changed to " + chatToChangeDemocracy.isDemocracy() + ".";
            }
            if (chatToChangeDemocracy.getCreatorEntity() == userRepo.getUserEntityById(senderId)) {
                chatToChangeDemocracy.setDemocracy(!chatToChangeDemocracy.isDemocracy());
                chatRepo.save(chatToChangeDemocracy);
                return "Chat with id " + chatId + ": democracy changed to " + chatToChangeDemocracy.isDemocracy() + ".";
            } else {
                throw new ChatError("You can't change democracy, ask chat creator " +
                        chatToChangeDemocracy.getCreatorEntity().getUserTAG() + " to change it");
            }
        } catch (Exception e) {
            throw new ChatError(e.getMessage());
        }
    }

    //todo should be checked
    public String delete(long chatId, long sendersId) throws ChatError {
        try {
            EntityChat chatToDelete = chatRepo.getEntityChatById(chatId);
            if (chatToDelete == null) throw new ChatError("Specified chat wasn't found!");
            if (chatToDelete.getCreatorEntity() == null) {
                if (chatToDelete.isDemocracy()) {
                    chatRepo.deleteById(chatId);
                    return "Chat with id " + chatId + " was deleted!";
                } else {
                    throw new ChatError("Chat creator wasn't found, to delete chat set " +
                            "democracy to true.");
                }
            } else {
                if (chatToDelete.getCreatorEntity().getId() == sendersId) {
                    chatRepo.deleteById(chatId);
                    return "Chat with id " + chatId + " was deleted!";
                } else {
                    throw new ChatError("You don't have permission to delete this chat!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ChatError(e.getMessage());
        }
    }

}
