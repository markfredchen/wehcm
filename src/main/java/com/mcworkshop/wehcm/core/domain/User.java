package com.mcworkshop.wehcm.core.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by markfredchen on 6/15/15.
 */
@Entity
@Table(name = "T_USER")
public class User extends DomainObject{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userID;

    @NotNull
    private UUID userOID;

    @NotNull
    private String wxUserID;

    private String wxUsername;

    @NotNull
    private String fullName;

    private String mobile;

    private String emailAddress;

    private String avator;

    @ManyToOne(optional = false, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "accountID", nullable = false, updatable = false)
    private Account account;

    private boolean isDeleted = false;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public UUID getUserOID() {
        return userOID;
    }

    public void setUserOID(UUID userOID) {
        this.userOID = userOID;
    }

    public String getWxUserID() {
        return wxUserID;
    }

    public void setWxUserID(String wxUserID) {
        this.wxUserID = wxUserID;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getWxUsername() {
        return wxUsername;
    }

    public void setWxUsername(String wxUsername) {
        this.wxUsername = wxUsername;
    }
}
