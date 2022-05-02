package com.msgrJava.entities;

import com.msgrJava.crypt.PassHasher;
import com.msgrJava.exceptions.chatException.ChatError;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "user") // for SQL table
public class EntityUser {
    //Fields---------------------------
    //ContactInfo
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tag", nullable = false, unique = true)
    private String userTAG;
    @Column(unique = true)
    private String phoneNumber;
    //Info
    @Column(name = "name", nullable = false)
    private String name;
    private String surname;
    @Column(unique = true)
    private String email;
    //SecuredInfo
    private String passHash;
    private String deviceIdHash;
    //todo this part should be implemented with ManyToMany annotation
    @OneToMany(mappedBy = "id")
    @Column(name="userChats", nullable = false)
    private static List<EntityChat> userChats;

    //----------------------------------

    //Constructors
    public EntityUser() {
        userChats = new ArrayList<>();
    }


    //Methods

    //GetInfoPreInit
    public String getInfoString() {


        return "id " + id + ", userTAG " + userTAG + ", phonenumber " + phoneNumber + ", name " + name + ", surname " +
                surname + ", email " + email + ", passHash " + passHash + ", deviceIdHash " + deviceIdHash +
                ", userChats " + userChats;
    }

    public boolean passIsOk(String pass) {
        if (passHash.equals(pass)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deviceIdIsOk(String receivedDeviceIdHash) {
        if (deviceIdHash.equals(receivedDeviceIdHash)) {
            return true;
        } else {
            return false;
        }
    }

    public void addChat(EntityChat newChat) throws ChatError {
        if (!userChats.contains(newChat)) {
            userChats.add(newChat);
        } else {
            throw new ChatError("You are already added to this chat");
        }
    }

    public void removeChat(EntityChat chatToRemove) throws ChatError {
        if (userChats.contains(chatToRemove)) {
            userChats.remove(chatToRemove);
        } else {
            throw new ChatError("You've exited this chat already");
        }
    }

    //Setters and getters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserTAG() {
        return userTAG;
    }

    public void setUserTAG(String userTAG) {
        this.userTAG = userTAG;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    public String getDeviceIdHash() {return deviceIdHash;}

    public void setDeviceIdHash(String deviceIdHash) {
        this.deviceIdHash = deviceIdHash;
    }

    public List<EntityChat> getUserChats() {
        return userChats;
    }

    public void setUserChats(List<EntityChat> userChats) {
        this.userChats = userChats;
    }

}
