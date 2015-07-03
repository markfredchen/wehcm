package com.mcworkshop.wehcm.core.domain.flow;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by markfredchen on 6/25/15.
 */


public class FieldOption {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
