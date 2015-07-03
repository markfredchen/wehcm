package com.mcworkshop.wehcm.core.domain.flow;

import com.mcworkshop.wehcm.core.domain.Account;

import javax.persistence.*;
import java.util.List;

/**
 * Created by markfredchen on 6/15/15.
 */
@Entity
@Table(name = "T_FORM_FLOW")
public class FormFlow extends Flow {

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "accountID", updatable = false)
    private Account account;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="flowID")
    private List<FormField> fields;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<FormField> getFields() {
        return fields;
    }

    public void setFields(List<FormField> fields) {
        this.fields = fields;
    }
}
