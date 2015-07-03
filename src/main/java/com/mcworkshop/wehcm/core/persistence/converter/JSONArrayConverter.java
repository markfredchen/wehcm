package com.mcworkshop.wehcm.core.persistence.converter;

import org.json.JSONArray;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by markfredchen on 6/27/15.
 */
@Converter(autoApply = true)
public class JSONArrayConverter implements AttributeConverter<JSONArray, String>{
    @Override
    public String convertToDatabaseColumn(JSONArray attribute) {
        if (attribute != null) {
            return attribute.toString();
        } else {
            return null;
        }
    }

    @Override
    public JSONArray convertToEntityAttribute(String dbData) {
        if (!StringUtils.isEmpty(dbData)) {
            return new JSONArray(dbData);
        } else {
            return null;
        }
    }
}
