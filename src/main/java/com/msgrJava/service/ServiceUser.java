package com.msgrJava.service;


import com.msgrJava.entities.EntityUser;
import com.msgrJava.exceptions.clientSide.UserNotFoundException;
import com.msgrJava.exceptions.clientSide.UserAlreadyExistsException;
import com.msgrJava.model.ModelUser;
import com.msgrJava.repository.RepoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//todo SERVICEUSER IS FOR WORKING WITH USERS DATA ON THE SERVER SIDE

@Service
public class ServiceUser {

    @Autowired
    private RepoUser userRepo;

    public EntityUser registration(EntityUser user) throws UserAlreadyExistsException {
        if (userRepo.findByUserTAG(user.getUserTAG()) != null) {
            throw new UserAlreadyExistsException("This tag is already used =(");
        }
        if (userRepo.findByPhoneNumber(user.getPhoneNumber()) != null) {
            throw new UserAlreadyExistsException("This phone is already registered =(");
        }
        return userRepo.save(user);
    }

    public ModelUser getOne(Long id) throws UserNotFoundException {
        EntityUser user = userRepo.getUserEntityById(id);
        if (user == null) {
            return ModelUser.toModel(user);
        }
        throw new UserNotFoundException("User not found =(");
    }

    //todo Should be improved or changed: deleting with not id only should be implemented
    public long delete(Long id) throws UserNotFoundException {
        try {
            userRepo.deleteById(id);
            return id;
        } catch (IllegalArgumentException e) {
            throw new UserNotFoundException("User with id " + id + " was not found, unable to delete!");
        }

    }
}
