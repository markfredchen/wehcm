package com.mcworkshop.wehcm.web.rest.resource;

import com.mcworkshop.wehcm.core.domain.DomainObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by markfredchen on 7/5/15.
 */
public class PassiveMessageResource extends BaseResource{
    private UUID messageOID;
    private UUID accountOID;
    private String flowName;
    private String fromUser;
    private String toUser;
    private List<DataFieldResource> data = new ArrayList<>();
    private List<String> actions = new ArrayList<>();

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

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public List<DataFieldResource> getData() {
        return data;
    }

    public void setData(List<DataFieldResource> data) {
        this.data = data;
    }

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    @Override
    public <T extends DomainObject> T toEntity() {
        return null;
    }
}
