package com.msgrJava.repository;

import com.msgrJava.entities.EntityChat;
import com.msgrJava.entities.EntityMessage;
import org.springframework.data.repository.CrudRepository;

public interface RepoMessage extends CrudRepository<EntityMessage, Long> {
    EntityMessage getEntityMessageById(long id);
    EntityMessage[] getEntityMessageByChatId(EntityChat chat);

}
