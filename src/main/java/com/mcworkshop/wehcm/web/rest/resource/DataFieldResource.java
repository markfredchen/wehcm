package com.mcworkshop.wehcm.web.rest.resource;

/**
 * Created by markfredchen on 7/5/15.
 */
public class DataFieldResource {
    private String name;
    private String value;
    private int sequence;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
