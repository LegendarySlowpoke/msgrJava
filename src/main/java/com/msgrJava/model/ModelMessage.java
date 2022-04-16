package com.msgrJava.model;

import com.msgrJava.entities.EntityMessage;
import com.msgrJava.entities.EntityUser;
import org.springframework.boot.Banner;

import javax.persistence.*;
import java.sql.Time;

public class ModelMessage {

    private Long sender;
    private String message;
    private Time created;
    private Time timeModified;
    private Time timeDeleted;

    public static ModelMessage toModelMessage(EntityMessage entityMessage) {
        ModelMessage model = new ModelMessage();
        model.sender = entityMessage.getSender().getId();
        model.message = entityMessage.getMessage();
        model.created = entityMessage.getCreated();
        if (entityMessage.isModified()) {
            model.timeModified = entityMessage.getTimeModified();
        }
        if (entityMessage.isDeleted()) {
            model.timeDeleted = entityMessage.getTimeDeleted();
        }
        return model;
    }
}
