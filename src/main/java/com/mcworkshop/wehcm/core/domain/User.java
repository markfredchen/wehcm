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
    private String username;

    @NotNull
    private String wxUserID;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
