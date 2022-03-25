package com.msgrJava.model;

import com.msgrJava.entities.EntityUser;

public class ModelUser {

    private Long id;
    private String userTag;
    private String userName;
    private String userSurname;
    private String userPhone;

    public static ModelUser toModel(EntityUser userEntity) {
        System.out.println("    ====ModelUser class: userEntity " + userEntity.toString() + " with userEntity id " +
                userEntity.getId());
        ModelUser userModel = new ModelUser();
        userModel.setId(userEntity.getId());
        userModel.setUserTag(userEntity.getUserTAG());
        userModel.setUserName(userEntity.getName());
        userModel.setUserSurname(userEntity.getSurname());
        userModel.setUserPhone(userEntity.getPhoneNumber());

        return userModel;
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
