package com.mcworkshop.wehcm.web.rest.controller;

import com.mcworkshop.wehcm.core.domain.Account;
import com.mcworkshop.wehcm.core.persistence.AccountRepository;
import com.mcworkshop.wehcm.integration.wechat.config.WeChatConfiguration;
import com.mcworkshop.wehcm.integration.wechat.config.wrapper.WeChatServiceWrapper;
import me.chanjar.weixin.common.bean.WxMenu;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.Arrays;

/**
 * Created by markfredchen on 8/22/15.
 */
@Controller
public class MenuController {

    @Autowired
    private WeChatConfiguration weChatConfiguration;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = "/menu/create", method = RequestMethod.GET)
    @ResponseBody
    public String createMenu(HttpServletRequest request) {
        try {
            WeChatServiceWrapper wrapper = weChatConfiguration.getWeChatServicer(request.getServerName());
            Account account = accountRepository.findOneByDomain(request.getServerName());
            WxMenu menu = new WxMenu();
            WxMenu.WxMenuButton baseButton = new WxMenu.WxMenuButton();
            baseButton.setName("提交申请");
            WxMenu.WxMenuButton viewButton = new WxMenu.WxMenuButton();
            StringBuilder url = new StringBuilder();
            url.append("http://");
            url.append(account.getDomain());
            url.append(":8081/#/flow?flowName=PTO");
            viewButton.setUrl(wrapper.getService().oauth2buildAuthorizationUrl(URLEncoder.encode(url.toString(), "UTF-8"), null));
            viewButton.setType("view");
            viewButton.setName("休假申请");
            baseButton.setSubButtons(Arrays.asList(viewButton));
            WxMenu.WxMenuButton reportButton = new WxMenu.WxMenuButton();
            reportButton.setName("汇总报表");
            reportButton.setType("view");
            reportButton.setUrl("http://mengniu.wehcm.com:8081/#/");
            menu.setButtons(Arrays.asList(baseButton, reportButton));


            wrapper.getService().menuCreate("1", menu);
            return "OK";
        } catch (Throwable e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
}
