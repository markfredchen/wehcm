package com.mcworkshop.wehcm.web.rest.resource;

import java.util.List;

/**
 * Created by markfredchen on 6/24/15.
 */
public class FormFlowResource {
    private String messageOID;
    private String accountOID;
    private String userOID;
    private String username;
    private String flowOID;
    private String flowName;
    private List<FieldResource> fields;
    private boolean hasCSS;


    public String getMessageOID() {
        return messageOID;
    }

    public void setMessageOID(String messageOID) {
        this.messageOID = messageOID;
    }

    public String getAccountOID() {
        return accountOID;
    }

    public void setAccountOID(String accountOID) {
        this.accountOID = accountOID;
    }

    public String getUserOID() {
        return userOID;
    }

    public void setUserOID(String userOID) {
        this.userOID = userOID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFlowOID() {
        return flowOID;
    }

    public void setFlowOID(String flowOID) {
        this.flowOID = flowOID;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public List<FieldResource> getFields() {
        return fields;
    }

    public void setFields(List<FieldResource> fields) {
        this.fields = fields;
    }

    public boolean isHasCSS() {
        return hasCSS;
    }

    public void setHasCSS(boolean hasCSS) {
        this.hasCSS = hasCSS;
    }
}
