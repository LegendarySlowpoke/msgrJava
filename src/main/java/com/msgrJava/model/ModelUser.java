package com.msgrJava.model;

import com.msgrJava.entities.EntityUser;

public class ModelUser {

    private String userTag;
    private String userName;
    private String userSurname;
    private int userPhone;

    public static ModelUser toModel(EntityUser userEntity) {
        ModelUser userModel = new ModelUser();
        userModel.setUserTag(userEntity.getUserTAG());
        userModel.setUserName(userEntity.getName());
        userModel.setUserSurname(userEntity.getSurname());
        userModel.setUserPhone(userEntity.getPhoneNumber());

        return userModel;
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

    public int getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(int userPhone) {
        this.userPhone = userPhone;
    }
}
