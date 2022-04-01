package com.msgrJava.repository;

import com.msgrJava.entities.EntityChat;
import org.springframework.data.repository.CrudRepository;

public interface RepoChat extends CrudRepository<EntityChat, Long> {

    EntityChat getEntityChatById(Long id);
    EntityChat findEntityChatByChatName(String userTag);


}
