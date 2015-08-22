package com.mcworkshop.wehcm.web.rest.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by markfredchen on 6/24/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldResource {
    private String name;
    private String labelKey;
    private String valueType; // Sting, Integer, etc
    private String inputType; // text, select, radio, checkbox
    private List<String> options;
    private String defaultValue;
    private boolean isReadonly;
    private int sequence;
    private ConstraintsResource constraints;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabelKey() {
        return labelKey;
    }

    public void setLabelKey(String labelKey) {
        this.labelKey = labelKey;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isReadonly() {
        return isReadonly;
    }

    public void setIsReadonly(boolean isReadonly) {
        this.isReadonly = isReadonly;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public ConstraintsResource getConstraints() {
        return constraints;
    }

    public void setConstraints(ConstraintsResource constraints) {
        this.constraints = constraints;
    }
}
