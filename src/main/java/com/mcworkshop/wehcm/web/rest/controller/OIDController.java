package com.mcworkshop.wehcm.web.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * Created by markfredchen on 8/19/15.
 */
@Controller
public class OIDController {
    @RequestMapping(value = "/api/oids/{baseName}", method = RequestMethod.GET)
    @ResponseBody
    public String generateOID(@PathVariable("baseName") String baseName) {
        return UUID.nameUUIDFromBytes(baseName.getBytes()).toString();
    }

    @RequestMapping(value = "/api/oids", method = RequestMethod.GET)
    @ResponseBody
    public String generateOID() {
        return UUID.randomUUID().toString();
    }
}
