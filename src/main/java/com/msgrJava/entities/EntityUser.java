package com.msgrJava.entities;


import javax.persistence.*;
import java.util.List;

@Entity
//@Table(name = "user") // for SQL table
public class EntityUser {
    //Fields---------------------------
    //Contacts
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tag", nullable = false)
    private String userTAG;
    @Column(name="phoneNumber")
    private String phoneNumber;
    //Info
    @Column(name = "name", nullable = false)
    private String name;
    private String surname;
    private String email;
    //SecuredInfo
    private String passHash;
    @ElementCollection
    private List<Long> usersFriends;
    //----------------------------------

    //Constructor
    public EntityUser() {
    }

    //GetInfoPreInit
    public String getInfoPreInit() {
        return "id " + id + ", userTAG " + userTAG + ", phonenumber " + phoneNumber + ", name " + name + ", surname " +
                surname + ", email " + email + ", passHash " + passHash;
    }

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

    public List<Long> getUsersFriends() {
        return usersFriends;
    }

    public void setUsersFriends(List<Long> userContacts) {
        this.usersFriends = userContacts;
    }
}
