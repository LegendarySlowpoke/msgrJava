package com.msgrJava.service;

import com.msgrJava.entities.EntityChat;
import com.msgrJava.entities.EntityUser;
import com.msgrJava.exceptions.chatException.ChatError;
import com.msgrJava.exceptions.userExceptions.RegistrationDataError;
import com.msgrJava.exceptions.userExceptions.UserLogInError;
import com.msgrJava.exceptions.userExceptions.UserNotFoundError;
import com.msgrJava.exceptions.userExceptions.UserAlreadyExistsError;
import com.msgrJava.model.ModelChat;
import com.msgrJava.model.ModelUser;
import com.msgrJava.model.ModelUserOwner;
import com.msgrJava.repository.RepoChat;
import com.msgrJava.repository.RepoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//todo SERVICEUSER IS FOR WORKING WITH USERS DATA ON THE SERVER SIDE

@Service
public class ServiceUser {

    @Autowired
    private RepoUser userRepo;
    @Autowired
    private RepoChat chatRepo;

    public ModelUserOwner logIn(String userTag, String userPass, String deviceIdHash) throws UserNotFoundError, UserLogInError {
        EntityUser user = userRepo.findByUserTAG(userTag);
        if (user == null) {
            throw new UserNotFoundError("User with this TAG wasn't found!");
        }
        if (user.passIsOk(userPass)) {
            user.setDeviceIdHash(deviceIdHash);
            userRepo.save(user);
            return ModelUserOwner.toModelOwner(user);
        } else {
            throw new UserLogInError("Incorrect password");
        }

    }

    public boolean logInCheck(String id, String deviceIdHash) throws UserLogInError {
        if (userRepo.getUserEntityById(Long.valueOf(id)).getDeviceIdHash().equals(deviceIdHash)) {
            return true;
        } else {
            throw new UserLogInError("Please, login again.");
        }
    }


    public EntityUser registration(EntityUser user) throws UserAlreadyExistsError, RegistrationDataError {
        //Missed or wrong registration info exceptions
        //TAG
        if (user.getUserTAG() == null || user.getName() == null) {
            throw new RegistrationDataError("UserTAG and Name fields should be filled in.");
        }
        if (!user.getUserTAG().matches("[A-Za-z0-9]+") ||
                user.getUserTAG().length() < 4 || user.getUserTAG().length() > 15) {
            throw new RegistrationDataError("User TAG should contain only digits & latin alphabetic letters." +
                    " Tag length should be from 4 to 15 symbols! "); /* + (user.getUserTAG().matches("[A-Za-z0-9]+   ") +
                    " " + (user.getUserTAG().length() < 4) + " " + (user.getUserTAG().length() > 15)));*/
        }
        //Name
        if (!user.getName().matches("[A-Za-z]+") ||
                user.getUserTAG().length() < 2 || user.getUserTAG().length() > 25) {
            throw new RegistrationDataError("User name should contain only latin alphabetic letters." +
                    " Name length should be from 2 to 25 symbols!");
        }
        //Phone number
        //removing spaces in phoneNumber
        String phoneChecker = user.getPhoneNumber();
        if (phoneChecker != null) {
            user.setPhoneNumber(user.getPhoneNumber().replace(" ", ""));
            if (!phoneChecker.matches("\\+?\\d+") || phoneChecker.replace("+", "").length() < 6
                    || phoneChecker.length() > 15) {
                throw new RegistrationDataError("User phone number " + phoneChecker +
                        " should contain digits only and its length should be" +
                        "from 6 to 15 digits."); /* + " Phone checker is null=" + (phoneChecker==null) + " " +
                        (!phoneChecker.matches("[0-9]+")) + " " + (phoneChecker.length() < 6) + " "
                                +  (phoneChecker.length() > 15));
                                */
            }
        }

        //User already exists issues
        if (userRepo.findByUserTAG(user.getUserTAG()) != null) {
            throw new UserAlreadyExistsError("This tag is already used =(");
        }
        if (user.getPhoneNumber() != null) {
            if (userRepo.findByPhoneNumber(user.getPhoneNumber()) != null) {
                throw new UserAlreadyExistsError("This phone is already registered =(");
            }
        }
        if (user.getEmail() != null) {
            if (userRepo.findByEmail(user.getEmail()) != null) {
                throw new UserAlreadyExistsError("This email is already registered.");/* user.getEmail()) != null is " +
                        (user.getEmail() != null) + " userRepo.findByEmail(user.getEmail()) is " +
                        userRepo.findByEmail(user.getEmail())); */
            }
        }
        return userRepo.save(user);
    }


    //TODO change it!
    //Getting entities (ONLY FOR USING IN SERVER PART)
    //public List<ModelChat> getOwnerChatList(Long id, String idDeviceHash) throws UserNotFoundError, ChatError,
    public List<ModelChat> getOwnerChatList(Long id, String idDeviceHash) throws UserNotFoundError, ChatError,
            UserLogInError {
        //todo DELETE THIS STRING DEBUG ONLY System.out.println("\t\tServiceUser: getOwnerChatList(): id:" + id + ", idDeviceHash:" + idDeviceHash);
        EntityUser user = userRepo.getUserEntityById(id);
        if (user == null) {
            System.out.println("ServiceUser(getOwnerChatList()): id=" + id + " not found");
            throw new UserNotFoundError("User not found");
        } else {
            if (!user.getDeviceIdHash().equals(idDeviceHash)) {
                throw new UserLogInError("Please, login again!");
            }
            List<ModelChat> chatList = new ArrayList<>();
            if (user.getUserChats() != null) {
                for (EntityChat userChat : user.getUserChats()) {
                    if (userChat == null) {
                        throw new ChatError("Unable to load chat");
                    }
                    chatList.add(ModelChat.toModelChat(userChat));
                }
            }
            //Initialize list, searching chats where user is creator
            List<EntityChat> chatEntityList = chatRepo.findByCreatorEntity(user);

            //Searching & adding chats where user is invited
            chatEntityList.addAll(chatRepo.findByInvitedUser(user));

            List<ModelChat> modelChatList = ModelChat.toModelChatList(chatEntityList);

            //todo Checking models, should be deleted after checking
            System.out.println("\t\t\tchatEntityList info: size=" + chatEntityList.size());
            for (ModelChat modelChat : modelChatList) {
                System.out.println("\t\t\t\t" + modelChat.toString());
            }

            return modelChatList;
            //return ModelChat.toModelChatList(chatEntityList);
        }
    }

    //Getting models
    public ModelUser getById(Long id) throws UserNotFoundError {
        EntityUser user = userRepo.getUserEntityById(id);
        if (user != null) {
            System.out.println("ServiceUser: id=" + user.getId());
            return ModelUser.toModel(user);
        } else {
            System.out.println("ServiceUser: id=" + id + " not found");
            throw new UserNotFoundError("User not found =(");
        }
    }

    public ModelUser getByPhone(String phone) throws UserNotFoundError {
        EntityUser user = userRepo.findByPhoneNumber(phone);
        if (user != null) {
            System.out.println("ServiceUser: id=" + user.getId());
            return ModelUser.toModel(user);
        } else {
            System.out.println("ServiceUser: phone=" + phone + " not found");
            throw new UserNotFoundError("User not found =(");
        }
    }

    public ModelUser getByUserTAG(String userTAG) throws UserNotFoundError {
        EntityUser user = userRepo.findByUserTAG(userTAG);
        if (user != null) {
            System.out.println("ServiceUser: id=" + user.getUserTAG());
            return ModelUser.toModel(user);
        } else {
            System.out.println("ServiceUser: userTAG=" + userTAG + " not found");
            throw new UserNotFoundError("User not found =(");
        }
    }

    public ModelUser getByEmail(String email) throws UserNotFoundError {
        EntityUser user = userRepo.findByPhoneNumber(email);
        if (user != null) {
            System.out.println("ServiceUser: id=" + user.getId());
            return ModelUser.toModel(user);
        } else {
            System.out.println("ServiceUser: email=" + email + " not found");
            throw new UserNotFoundError("User not found =(");
        }
    }

    public String delete(Long id) throws UserNotFoundError {
        try {
            userRepo.deleteById(id);
            return "User with id " + id + " was deleted.";
        } catch (Exception e) {
            throw new UserNotFoundError("User with id " + id + " was not found, unable to delete!" +
                    "\n\t\t" + e.getMessage());
        }

    }
}
