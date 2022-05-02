package com.msgrJava.repository;

import com.msgrJava.entities.EntityChat;
import com.msgrJava.entities.EntityUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RepoChat extends CrudRepository<EntityChat, Long> {

    EntityChat getEntityChatById(Long id);
    EntityChat findEntityChatByChatName(String userTag);
    List<EntityChat> findByCreatorEntity(EntityUser user);
    List<EntityChat> findByInvitedUser(EntityUser user);



}
