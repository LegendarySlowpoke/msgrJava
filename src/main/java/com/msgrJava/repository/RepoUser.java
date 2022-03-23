package com.msgrJava.repository;

import com.msgrJava.entities.EntityUser;
import org.springframework.data.repository.CrudRepository;

public interface RepoUser extends CrudRepository<EntityUser, Long> {

    EntityUser findByUserId(Long id);
    EntityUser findByUserPhone(int phoneNumber);
    EntityUser findByUserTag(String tag);

}
