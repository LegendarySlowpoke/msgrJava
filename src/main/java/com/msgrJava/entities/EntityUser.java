package com.msgrJava.entities;

import javax.persistence.*;
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
    @Column(name = "tag", nullable = false)
    private String userTAG;
    private String phoneNumber;
    //Info
    @Column(name = "name", nullable = false)
    private String name;
    private String surname;
    private String email;
    //SecuredInfo
    private String passHash;
    //todo this part should be implemented with ManyToMany annotation
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "usersList")
    @Column(name="userChats", nullable = true)
    private List<EntityChat> userChats;

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

    public List<EntityChat> getUserChats() {
        return userChats;
    }

    public void setUserChats(List<EntityChat> userChats) {
        this.userChats = userChats;
    }
}
