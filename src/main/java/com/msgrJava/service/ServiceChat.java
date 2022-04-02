package com.msgrJava.service;

import com.msgrJava.entities.EntityChat;
import com.msgrJava.entities.EntityUser;
import com.msgrJava.exceptions.chatException.ChatError;
import com.msgrJava.exceptions.chatException.MessageError;
import com.msgrJava.exceptions.userExceptions.UserNotFoundException;
import com.msgrJava.repository.RepoChat;
import com.msgrJava.repository.RepoMessage;
import com.msgrJava.repository.RepoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceChat {

    @Autowired
    private RepoChat chatRepo;
    @Autowired
    private RepoUser userRepo;
    @Autowired
    private RepoMessage messageRepo;

    //Methods for working with chat
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

    //method for adding user to chat
    public String addUser(long chatId, long senderId, long invitedId) throws ChatError, UserNotFoundException {
        EntityChat chatToEdit = chatRepo.getEntityChatById(chatId);
        EntityUser senderEntity = userRepo.getUserEntityById(senderId);
        EntityUser invitedEntity = userRepo.getUserEntityById(invitedId);
        if (chatToEdit == null) throw new ChatError("Chat with id " + chatId + " wasn't found.");
        if (senderEntity == null) throw new UserNotFoundException("User(sender) with id " +
                senderId + " wasn't found.");
        if (invitedEntity == null) throw new UserNotFoundException("User(invited) with id " +
                invitedId + " wasn't found.");

        try {
            //Checking if senderID == chats creatorId || if democracy == true
            if (chatToEdit.getCreatorEntity() != senderEntity) {
                if (!chatToEdit.isDemocracy()) throw new ChatError("Only chat creator can add users!" +
                        " To enable function for all other users - ask chat creator " +
                        chatToEdit.getCreatorEntity().getUserTAG() + " to change democracy to true.");
            }
            chatToEdit.addUserToChat(invitedEntity);
            chatRepo.save(chatToEdit);
            return "User " + invitedEntity.getUserTAG() + " was added to chat " + chatToEdit.getChatName();
        } catch (Exception e) {
            throw new ChatError("Unable to add user to chat: " + e.getMessage());
        }
    }

    public String removeUser(long chatId, long senderId, long userToDelete) throws ChatError, UserNotFoundException {
        EntityChat chatToEdit = chatRepo.getEntityChatById(chatId);
        EntityUser senderEntity = userRepo.getUserEntityById(senderId);
        EntityUser userToDeleteEntity = userRepo.getUserEntityById(userToDelete);
        if (chatToEdit == null) throw new ChatError("Chat with id " + chatId + " wasn't found.");
        if (senderEntity == null) throw new UserNotFoundException("User(sender) with id " +
                senderId + " wasn't found.");
        if (userToDeleteEntity == null) throw new UserNotFoundException("User(to delete) with id " +
                userToDelete + " wasn't found.");
        try {
            //Checking if senderID == chats creatorId || if democracy == true
            if (chatToEdit.getCreatorEntity() != senderEntity) {
                if (!chatToEdit.isDemocracy()) throw new ChatError("Only chat creator can delete users!" +
                        " To enable function for all other users - ask chat creator " +
                        chatToEdit.getCreatorEntity().getUserTAG() + " to change democracy to true.");
            }
            chatToEdit.removeUserFromChat(userToDeleteEntity);
            chatRepo.save(chatToEdit);
            return "User " + userToDeleteEntity.getUserTAG() + " was removed from chat " + chatToEdit.getChatName();
        } catch (Exception e) {
            throw new ChatError("Unable to delete user from chat: " + e.getMessage());
        }
    }

    public String deleteChat(long chatId, long sendersId) throws ChatError {
        try {
            EntityChat chatToDelete = chatRepo.getEntityChatById(chatId);
            if (chatToDelete == null) throw new ChatError("Chat with id " + chatId + " wasn't found.");
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

    //Methods for working with messages in this chat;
    public String createNewMessage(long chatId, long senderId, String message) throws ChatError, MessageError,
            UserNotFoundException {

            EntityChat chatEntity = chatRepo.getEntityChatById(chatId);
            EntityUser senderEntity = userRepo.getUserEntityById(senderId);
            if (chatEntity == null) throw new ChatError("Chat with id " + chatId + " wasn't found.");
            if (senderEntity == null) throw new ChatError("User with id " + senderId + " wasn't found.");
            if (message == null) throw new MessageError("Received message is null");

            //Create and save messageEntity to repository
            try {
                /*
                EntityMessage createdMessage = chatEntity.createNewMessage(senderEntity, message);
                System.out.println(createdMessage.getMessageFullInfo());
                messageRepo.save(createdMessage);
                 */
                messageRepo.save(chatEntity.createNewMessage(senderEntity, message));
                System.out.println("Saved to message repo");

                //Saving change made in chatEntity
                chatRepo.save(chatEntity);
                System.out.println("Saved to chat repo");
                return "Message was posted!";
            } catch (Exception e) {
                System.out.println("Unknown error: " + e.getMessage());
                //e.printStackTrace();
                throw new ChatError("Unknown error:");// + e.getMessage());
            }
    }
}
