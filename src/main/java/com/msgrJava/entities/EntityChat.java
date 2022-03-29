package com.msgrJava.entities;

import com.msgrJava.ENUMS.RequestMessage;
import com.msgrJava.ENUMS.ResponseMessage;
import com.msgrJava.exceptions.chatException.ChatError;
import com.msgrJava.exceptions.chatException.MessageError;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "chat")
public class EntityChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    @JoinColumn(name = "user_id")
    private List<EntityUser> user;
    @OneToMany
    @JoinColumn(name="messages", nullable = false)
    private List<EntityMessage> messages;

    @ManyToOne
    @JoinColumn(name = "entity_user_id")
    private EntityUser entityUser;

    public EntityUser getEntityUser() {
        return entityUser;
    }

    public void setEntityUser(EntityUser entityUser) {
        this.entityUser = entityUser;
    }

    //todo create a one WILD map where users who've read messages are noted
    //private Map<Long, List<EntityUser>> unreadMessages;

    //Constructor
    public EntityChat() {}

    //Methods
    public ResponseMessage createNewMessage(EntityUser user, String message) throws ChatError {
        try {
            new EntityMessage(user, message);
            return ResponseMessage.CREATED;
        } catch (MessageError e ){
            throw new ChatError("Failed to create new message(EntityChat class): MessageError " + e.getMessage());
        } catch (Exception e) {
            throw new ChatError("Failed to create new message(EntityChat class): Exception" + e.getMessage());
        }
    }

    //todo finish this
    public LinkedList<EntityMessage> getMessages(RequestMessage request) throws ChatError {
            switch (request) {
                case UNREADONLY:
                    return new LinkedList<EntityMessage>();
                case ALLMESSAGES:
                    return new LinkedList<EntityMessage>();
                case MODIFIEDMESSAGES:
                    return new LinkedList<EntityMessage>();
                default:
                    throw new ChatError("Unexpected request during getMessage in Entity chat");
            }
    }


    //todo finish this
    public ResponseMessage modifyMessage(EntityUser user, Long messageId , String corerectedMessage) throws ChatError {
        try {
            //messages.get(messageId);

            return ResponseMessage.MODIFIED;
      /*  } catch (MessageError e) {
            throw new ChatError("Failed to modify new message(EntityChat class): MessageError " + e.getMessage());

       */
        } catch (Exception e) {
            throw new ChatError("Failed to modify new message(EntityChat class): Exception" + e.getMessage());
        }
    }
}
