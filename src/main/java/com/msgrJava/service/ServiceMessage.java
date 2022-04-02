package com.msgrJava.service;

import com.msgrJava.entities.EntityChat;
import com.msgrJava.entities.EntityMessage;
import com.msgrJava.entities.EntityUser;
import com.msgrJava.exceptions.chatException.MessageError;
import com.msgrJava.repository.RepoChat;
import com.msgrJava.repository.RepoMessage;
import com.msgrJava.repository.RepoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceMessage {

    @Autowired
    private RepoMessage messageRepo;
    @Autowired
    private RepoUser userRepo;
    @Autowired
    private RepoChat chatRepo;

    public EntityMessage createNewMessage(Long idCreator, Long idChat, String message) throws MessageError {
        //Checking incoming data
        if (idCreator == null || idCreator == 0) throw new MessageError("idCreator is null/0");
        if (idChat == null || idChat == 0) throw new MessageError("chatName is null/0");
        if (message == null) throw new MessageError("message is null");

        //Searching and checking user id and chat id in db
        EntityUser creatorEntity = userRepo.getUserEntityById(idCreator);
        EntityChat chatEntity = chatRepo.getEntityChatById(idChat);
        if (creatorEntity == null) throw new MessageError("Couldn't find entity with user id "  + idCreator);
        if (chatEntity == null) throw new MessageError("Couldn't find entity with chat id "  + idChat);

        return messageRepo.save(new EntityMessage(creatorEntity, chatEntity, message));
    }
}
