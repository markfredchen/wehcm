package com.mcworkshop.wehcm.web.rest.wechat;

import com.mcworkshop.wehcm.core.domain.Account;
import com.mcworkshop.wehcm.core.domain.User;
import com.mcworkshop.wehcm.core.persistence.AccountRepository;
import com.mcworkshop.wehcm.core.persistence.UserRepository;
import com.mcworkshop.wehcm.integration.wechat.config.WeChatConfiguration;
import com.mcworkshop.wehcm.integration.wechat.config.wrapper.WeChatServiceWrapper;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.bean.WxCpMessage;
import me.chanjar.weixin.cp.bean.WxCpUser;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.messagebuilder.NewsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * EntryPoint for WeChat integration.
 *
 * Created by markfredchen on 6/23/15.
 */
@Controller
@RequestMapping(value = "/weChat")
public class WeChatEntryPoint {

    @Autowired
    private WeChatConfiguration weChatConfiguration;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String validate(HttpServletRequest request,
                           @RequestParam("msg_signature") String msg_signature,
                           @RequestParam("nonce") String nonce,
                           @RequestParam("timestamp") String timestamp,
                           @RequestParam("echostr") String echostr) {
        System.out.println("Entering validate method");
        WeChatServiceWrapper wrapper = weChatConfiguration.getWeChatServicer(request.getServerName());
        if (wrapper.getService().checkSignature(msg_signature, timestamp, nonce, echostr)) {
            System.out.println("Validate Successfully!!!");
            return wrapper.getCryptUtil().decrypt(echostr);
        }

        return "ERROR";
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String handleMessage(HttpServletRequest request,
                                @RequestParam("msg_signature") String signature,
                                @RequestParam("timestamp") String timestamp,
                                @RequestParam("nonce") String nonce) {
        System.out.println("Entering handleMessage method");
        WeChatServiceWrapper wrapper = weChatConfiguration.getWeChatServicer(request.getServerName());
        try {
            System.out.println("Handling WeChat Message");
            WxCpXmlMessage inMessage = WxCpXmlMessage.fromEncryptedXml(request.getInputStream(), wrapper.getConfig(), timestamp, nonce, signature);
            System.out.println("Incoming Message:\n" + inMessage.toString());
            return wrapper.getRouter().route(inMessage).toEncryptedXml(wrapper.getConfig());
        } catch (Throwable e) {
            throw new RuntimeException("Message Handler Failed");
        }
    }

    @RequestMapping(value = "/oauth2", method = RequestMethod.GET)
    public ModelAndView oauth2(HttpServletRequest request,
                                         @RequestParam("code") String code,
                                         @RequestParam("state") String state) {
        System.out.println("code: " + code);
        System.out.println("state: " + state);
        WeChatServiceWrapper wrapper = weChatConfiguration.getWeChatServicer(request.getServerName());
        if (!"authdeny".equals(code)) {
            try {
                String[] res = wrapper.getService().oauth2getUserInfo(code);
                if (res != null && res.length == 2) {
                    StringBuilder url = new StringBuilder(request.getScheme());
                    url.append("://");
                    url.append(request.getServerName());
                    url.append(":");
                    url.append(request.getServerPort());
                    url.append("/#/oauth2/authorize?");
                    url.append("userID=");
                    url.append(res[0]);
                    url.append("&");
                    WxCpUser wxUser = wrapper.getService().userGet(res[0]);
                    url.append(buildUserParameters(wxUser));
                    System.out.println(url.toString());
                    return new ModelAndView("redirect:" + url.toString());
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException();
    }

    private String buildUserParameters(WxCpUser user) {
        StringBuilder s = new StringBuilder();
        s.append("fullName=");
        s.append(encodeParam(user.getName()));
        s.append("&WeiXinId=");
        s.append(user.getWeiXinId());
        if (user.getMobile() != null) {
            s.append("&mobile=");
            s.append(user.getMobile());
        }
        if (user.getAvatar() != null) {
            s.append("&avatar=");
            s.append(encodeParam(user.getAvatar()));
        }
        return s.toString();
    }

    private String encodeParam(String param) {
        try {
            return URLEncoder.encode(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Transactional
    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public ResponseEntity<String> authorize(HttpServletRequest request, @RequestParam("userID") String userID) {
        WeChatServiceWrapper wrapper = weChatConfiguration.getWeChatServicer(request.getServerName());
        try {
            WxCpUser wxUser = wrapper.getService().userGet(userID);
            System.out.println(wxUser.toJson());

            User user = new User();
            user.setUserOID(UUID.nameUUIDFromBytes(wxUser.getWeiXinId().getBytes()));
            user.setWxUserID(userID);
            user.setWxUsername(wxUser.getWeiXinId());
            user.setFullName(wxUser.getName());
            user.setAccount(accountRepository.findOneByDomain(request.getServerName()));
            userRepository.save(user);
            wrapper.getService().userAuthenticated(userID);
            return new ResponseEntity<String>("OK", HttpStatus.OK);
        } catch (WxErrorException e) {
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/send/message", method = RequestMethod.GET)
    @ResponseBody
    public String sendMessage(HttpServletRequest request) {
        WeChatServiceWrapper wrapper = weChatConfiguration.getWeChatServicer(request.getServerName());
        NewsBuilder newsBuilder = new NewsBuilder();
        newsBuilder.toUser("markfred.chen");
        newsBuilder.agentId("1");
        WxCpMessage.WxArticle article = new WxCpMessage.WxArticle();
        article.setTitle("HELLO");
        article.setDescription("TEST SENDING NEWS");


        article.setUrl("http://mengniu.wehcm.com:8081/#/flow/success");
        newsBuilder.addArticle(article);
//        TextBuilder builder = new TextBuilder();
//        builder.agentId("1");
//        builder.toUser("markfred.chen");
//        builder.content("TEST SENDING MESSAGES");
        try {
            wrapper.getService().messageSend(newsBuilder.build());
            return "OK";
        } catch (WxErrorException e) {
            e.printStackTrace();
            return "error";
        }

    }
}
