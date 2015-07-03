package com.mcworkshop.wehcm.core.domain.message;

import org.json.JSONObject;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by markfredchen on 6/15/15.
 */
@MappedSuperclass
abstract class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long messageID;
    @NotNull
    private UUID messageOID;

    @NotNull
    private UUID accountOID;

    private String flowName;

    private String fromUser;

    @NotNull
    private JSONObject data;

    private MessageStatus status;

    public Long getMessageID() {
        return messageID;
    }

    public void setMessageID(Long messageID) {
        this.messageID = messageID;
    }

    public UUID getMessageOID() {
        return messageOID;
    }

    public void setMessageOID(UUID messageOID) {
        this.messageOID = messageOID;
    }

    public UUID getAccountOID() {
        return accountOID;
    }

    public void setAccountOID(UUID accountOID) {
        this.accountOID = accountOID;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(String data) {
        this.data = new JSONObject(data);
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }
}
