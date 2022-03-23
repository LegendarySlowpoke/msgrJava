package com.msgrJava.entities;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user") // for SQL table
public class EntityUser {
    //Fields---------------------------
    //Contacts
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "tag", nullable = false)
    private String userTAG;
    @Column(name="phoneNumber", nullable = true)
    private int phoneNumber;
    //Info
    private String name;
    private String surname;
    private String email;
    //SecuredInfo
    private String passHash;
    @ElementCollection
    private List<Long> userContacts;
    //----------------------------------

    public EntityUser() {}



}
