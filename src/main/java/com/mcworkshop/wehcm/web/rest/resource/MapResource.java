package com.mcworkshop.wehcm.web.rest.resource;

import java.util.HashMap;

/**
 * Created by markfredchen on 4/11/15.
 */
public class MapResource extends HashMap<String, Object>{
    public MapResource(){
        super();
    }

    public MapResource(String key, Object value) {
        super();
        super.put(key, value);
    }
}
