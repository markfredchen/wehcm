package com.mcworkshop.wehcm.core.domain;

import com.mcworkshop.wehcm.core.domain.enumeration.IntegrationType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

/**
 * Created by markfredchen on 6/15/15.
 */
@Entity
@Table(name = "T_ACCOUNT")
public class Account extends DomainObject{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountID;

    @NotNull
    private UUID accountOID;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(unique = true)
    private String name;
    /**
     * This fields is the domain name for the account.
     */
    @NotNull
    @Size(min = 1, max = 255)
    @Column(unique = true)
    private String domain;
    /**
     * This field determines whether client is using EMAIL mechanism or WEB SERVICE mechanism to communicate.
     */
    @NotNull
    private IntegrationType integrationType;
    /**
     * If integrationType is Email, the value means that all message will be sent to this emailAddress.
     * If integrationType is WEB_SERVICE, the value is the baseURL for all the flow.
     */
    private String target;

    private String wxToken;

    private String wxAESKey;

    private String corpID;

    private String corpSecret;

    private String agentID;

    public Long getAccountID() {
        return accountID;
    }

    public void setAccountID(Long accountID) {
        this.accountID = accountID;
    }

    public UUID getAccountOID() {
        return accountOID;
    }

    public void setAccountOID(UUID accountOID) {
        this.accountOID = accountOID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public IntegrationType getIntegrationType() {
        return integrationType;
    }

    public void setIntegrationType(IntegrationType integrationType) {
        this.integrationType = integrationType;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getWxToken() {
        return wxToken;
    }

    public void setWxToken(String wxToken) {
        this.wxToken = wxToken;
    }

    public String getWxAESKey() {
        return wxAESKey;
    }

    public void setWxAESKey(String wxAESKey) {
        this.wxAESKey = wxAESKey;
    }

    public String getCorpID() {
        return corpID;
    }

    public void setCorpID(String corpID) {
        this.corpID = corpID;
    }

    public String getCorpSecret() {
        return corpSecret;
    }

    public void setCorpSecret(String corpSecret) {
        this.corpSecret = corpSecret;
    }

    public String getAgentID() {
        return agentID;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }
}
