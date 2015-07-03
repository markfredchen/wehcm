package com.mcworkshop.wehcm.core.persistence.converter;

import org.json.JSONObject;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by markfredchen on 6/27/15.
 */
@Converter(autoApply = true)
public class JSONObjectConverter implements AttributeConverter<JSONObject, String> {
    @Override
    public String convertToDatabaseColumn(JSONObject attribute) {
        if (attribute != null) {
            return attribute.toString();
        } else {
            return null;
        }
    }

    @Override
    public JSONObject convertToEntityAttribute(String dbData) {
        if (!StringUtils.isEmpty(dbData)) {
            return new JSONObject(dbData);
        } else {
            return null;
        }
    }
}
