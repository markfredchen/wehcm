package com.mcworkshop.wehcm.core.domain.flow;

import com.mcworkshop.wehcm.core.domain.DomainObject;

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
abstract public class Flow extends DomainObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long flowID;

    @NotNull
    protected UUID flowOID;

    @NotNull
    protected String name;
    @NotNull
    protected String msgKey;

    public Long getFlowID() {
        return flowID;
    }

    public void setFlowID(Long flowID) {
        this.flowID = flowID;
    }

    public UUID getFlowOID() {
        return flowOID;
    }

    public void setFlowOID(UUID flowOID) {
        this.flowOID = flowOID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsgKey() {
        return msgKey;
    }

    public void setMsgKey(String msgKey) {
        this.msgKey = msgKey;
    }
}
