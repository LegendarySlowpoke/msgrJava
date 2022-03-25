package com.msgrJava.service;

import com.msgrJava.entities.EntityUser;
import com.msgrJava.exceptions.clientSide.RegistrationDataError;
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

    public EntityUser registration(EntityUser user) throws UserAlreadyExistsException, RegistrationDataError {
        //Missed or wrong registration info exceptions
        //TAG
        if (user.getUserTAG() == null || user.getName() == null) {
            throw new RegistrationDataError("UserTAG and Name fields should be filled in.");
        }
        if (!user.getUserTAG().matches("[A-Za-z0-9]+") ||
                user.getUserTAG().length() < 4 || user.getUserTAG().length() > 15) {
            throw new RegistrationDataError("User TAG should contain only digits & latin alphabetic letters." +
                    " Tag length should be from 4 to 15 symbols! " + (user.getUserTAG().matches("[A-Za-z0-9]+   ") +
                    " " + (user.getUserTAG().length() < 4) + " " + (user.getUserTAG().length() > 15)));
        }
        //Name
        if (!user.getName().matches("[A-Za-z]+") ||
                user.getUserTAG().length() < 2 || user.getUserTAG().length() > 25) {
            throw new RegistrationDataError("User name should contain only latin alphabetic letters." +
                    " Name length should be from 2 to 25 symbols!");
        }
        //Phone number
        //removing spaces in phoneNumber
        //user.setPhoneNumber(user.getPhoneNumber().replace(" ", ""));
        String phoneChecker = user.getPhoneNumber();
        if (phoneChecker != null) {
            if (!phoneChecker.matches("[0-9]+") || phoneChecker.length() < 6 || phoneChecker.length() > 15) {
                throw new RegistrationDataError("User phone number " + phoneChecker +
                        " should contain digits only and its length should be" +
                        "from 6 to 15 digits." + " Phone checker is null=" + (phoneChecker==null) + " " +
                        (!phoneChecker.matches("[0-9]+")) + " " + (phoneChecker.length() < 6) + " "
                                +  (phoneChecker.length() > 15));
            }
        }

        //User already exists issues
        if (userRepo.findByUserTAG(user.getUserTAG()) != null) {
            throw new UserAlreadyExistsException("This tag is already used =(");
        }
        if (user.getPhoneNumber() != null) {
            if (userRepo.findByPhoneNumber(user.getPhoneNumber()) != null) {
                throw new UserAlreadyExistsException("This phone is already registered =(");
            }
        }
        if (user.getEmail() != null) {
            if (userRepo.findByEmail(user.getEmail()) != null) {
                throw new UserAlreadyExistsException("This email is already registered =( user.getEmail()) != null is " +
                        (user.getEmail() != null) + " userRepo.findByEmail(user.getEmail()) is " +
                        userRepo.findByEmail(user.getEmail()));
            }
        }
        return userRepo.save(user);
    }


    public ModelUser getOne(Long id) throws UserNotFoundException {
        EntityUser user = userRepo.getUserEntityById(id);
        if (user != null) {
            System.out.println("ServiceUser: id=" + user.getId());
            return ModelUser.toModel(user);
        } else {
            System.out.println("ServiceUser: id=" + id + " not found");
            throw new UserNotFoundException("User not found =(");
        }
    }

    //todo Should be improved or changed: deleting with not id only should be implemented
    public String delete(Long id) throws UserNotFoundException {
        try {
            userRepo.deleteById(id);
            return "User with id " + id + " was deleted.";
        } catch (Exception e) {
            throw new UserNotFoundException("User with id " + id + " was not found, unable to delete!" +
                    "\n\t\t" + e.getMessage());
        }

    }
}
