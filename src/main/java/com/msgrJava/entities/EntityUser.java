package com.msgrJava.entities;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user") // for SQL table
public class EntityUser {
    //blablablab
    //Fields
    //Contacts
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Id
    @Column(name = "tag", nullable = false)
    private String userTAG;
    @Id
    @Column(name="phoneNumber", nullable = true)
    private int phoneNumber;


    //Info
    private String name;
    private String surname;
    private String email;

    @ElementCollection
    private List<Long> userContacts;




}
