package com.msgrJava.model;

import com.msgrJava.entities.EntityUser;

public class ModelUserOwner {

    private Long id;
    private String userTag;
    private String userName;
    private String userSurname;
    private String userPhone;
    private String email;



    public static ModelUserOwner toModelOwner(EntityUser userEntity) {
        System.out.println("    ====ModelUserOwner class: userEntityOwner " + userEntity.toString() +
                " with userEntity id " + userEntity.getId());
        ModelUserOwner userModelOwner = new ModelUserOwner();
        userModelOwner.setId(userEntity.getId());
        userModelOwner.setUserTag(userEntity.getUserTAG());
        userModelOwner.setUserName(userEntity.getName());
        userModelOwner.setUserSurname(userEntity.getSurname());
        userModelOwner.setUserPhone(userEntity.getPhoneNumber());
        userModelOwner.setEmail(userEntity.getEmail());
        return userModelOwner;
    }

    public void printInfo() {
        System.out.println("ModelUserOwner{" +
                "id=" + id +
                ", userTag='" + userTag + '\'' +
                ", userName='" + userName + '\'' +
                ", userSurname='" + userSurname + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", email='" + email + '\'' +
                '}'
        );
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserTag() {
        return userTag;
    }

    public void setUserTag(String userTag) {
        this.userTag = userTag;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}

