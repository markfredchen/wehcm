package com.mcworkshop.wehcm.web.rest.wechat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by markfredchen on 6/23/15.
 */
@Controller
@RequestMapping(value = "/weChat")
public class WeChatEntryPoint {


    @RequestMapping(method = RequestMethod.GET)
    public String validate() {

        return "";
    }


    @RequestMapping(method = RequestMethod.POST)
    public String handleMessage() {

        return "";
    }
}
