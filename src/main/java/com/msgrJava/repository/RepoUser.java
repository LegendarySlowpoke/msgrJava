package com.msgrJava.repository;

import com.msgrJava.entities.EntityUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RepoUser extends CrudRepository<EntityUser, Long> {

    EntityUser getUserEntityById(Long id);
    EntityUser findByPhoneNumber(String phoneNumber);
    EntityUser findByUserTAG(String tag);
    EntityUser findByEmail(String email);

}
