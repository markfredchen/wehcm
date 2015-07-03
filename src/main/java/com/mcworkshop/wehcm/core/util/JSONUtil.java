package com.mcworkshop.wehcm.core.util;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by markfredchen on 7/1/15.
 */
public class JSONUtil {

    public static List<String> convertJSONArray(JSONArray array) {
        if (array == null) {
            return null;
        }
        List<String> result = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            result.add(array.getString(i));
        }

        return result;
    }
}
