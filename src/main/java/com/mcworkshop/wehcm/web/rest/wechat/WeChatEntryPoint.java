package com.mcworkshop.wehcm.web.rest.wechat;

import com.mcworkshop.wehcm.core.domain.Account;
import com.mcworkshop.wehcm.core.persistence.AccountRepository;
import com.mcworkshop.wehcm.web.rest.wechat.wrapper.WeChatServiceWrapper;
import me.chanjar.weixin.cp.api.WxCpInMemoryConfigStorage;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpServiceImpl;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by markfredchen on 6/23/15.
 */
@Controller
@RequestMapping(value = "/weChat")
public class WeChatEntryPoint {

    private static Map<String, WeChatServiceWrapper> accountWeChatServiceWrapperMap = new HashMap<>();

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String validate(HttpServletRequest request,
                           @RequestParam("msg_signature") String msg_signature,
                           @RequestParam("nonce") String nonce,
                           @RequestParam("timestamp") String timestamp,
                           @RequestParam("echostr") String echostr) {

        Account account = accountRepository.findOneByDomain(request.getServerName());
        if (account != null) {
            WeChatServiceWrapper wrapper = accountWeChatServiceWrapperMap.get(account.getDomain());
            if (wrapper == null) {
                wrapper = new WeChatServiceWrapper();
                WxCpInMemoryConfigStorage config = new WxCpInMemoryConfigStorage();
                config.setCorpId(account.getCorpID());
                config.setCorpSecret(account.getCorpSecret());
                config.setAgentId(account.getAgentID());
                config.setToken(account.getWxToken());
                config.setAesKey(account.getWxAESKey());
                WxCpService service = new WxCpServiceImpl();
                service.setWxCpConfigStorage(config);
                WxCpCryptUtil wxCpCryptUtil = new WxCpCryptUtil(config);
                wrapper.setService(service);
                wrapper.setConfig(config);
                wrapper.setCryptUtil(wxCpCryptUtil);
                accountWeChatServiceWrapperMap.put(account.getDomain(), wrapper);
            }
            if (wrapper.getService().checkSignature(msg_signature, timestamp, nonce, echostr)) {
                return wrapper.getCryptUtil().decrypt(echostr);
            }

        }
        return "ERROR";
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String handleMessage(@RequestBody String requestBody,
                                @RequestParam("msg_signature") String signature,
                                @RequestParam("timestamp") String timestamp,
                                @RequestParam("nonce") String nonce) {

        return "";
    }
}
