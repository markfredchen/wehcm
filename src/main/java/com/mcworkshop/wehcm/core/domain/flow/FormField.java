package com.mcworkshop.wehcm.core.domain.flow;

import com.mcworkshop.wehcm.core.domain.DomainObject;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by markfredchen on 6/15/15.
 */
@Entity
@Table(name = "T_FORM_FIELD")
public class FormField extends DomainObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fieldID;
    @NotNull
    private String name;
    @NotNull
    private String labelKey;
    @NotNull
    private String valueType; // Sting, Integer, etc
    @NotNull
    private String inputType; // text, select, radio, checkbox

    private String options;

    private String defaultValue;

    private boolean isReadonly;

    @NotNull
    @Min(value = 0)
    private int sequence;

    public Long getFieldID() {
        return fieldID;
    }

    public void setFieldID(Long fieldID) {
        this.fieldID = fieldID;
    }

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

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public boolean isReadonly() {
        return isReadonly;
    }

    public void setIsReadonly(boolean isReadonly) {
        this.isReadonly = isReadonly;
    }
}
