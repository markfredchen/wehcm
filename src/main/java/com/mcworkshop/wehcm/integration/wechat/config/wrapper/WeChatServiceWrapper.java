package com.mcworkshop.wehcm.integration.wechat.config.wrapper;

import me.chanjar.weixin.cp.api.WxCpConfigStorage;
import me.chanjar.weixin.cp.api.WxCpMessageRouter;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;

/**
 * Created by markfredchen on 6/16/15.
 */
public class WeChatServiceWrapper {
    private WxCpConfigStorage config;
    private WxCpService service;
    private WxCpCryptUtil cryptUtil;
    private WxCpMessageRouter router;

    public WxCpConfigStorage getConfig() {
        return config;
    }

    public void setConfig(WxCpConfigStorage config) {
        this.config = config;
    }

    public WxCpService getService() {
        return service;
    }

    public void setService(WxCpService service) {
        this.service = service;
    }

    public WxCpCryptUtil getCryptUtil() {
        return cryptUtil;
    }

    public void setCryptUtil(WxCpCryptUtil cryptUtil) {
        this.cryptUtil = cryptUtil;
    }

    public WxCpMessageRouter getRouter() {
        return router;
    }

    public void setRouter(WxCpMessageRouter router) {
        this.router = router;
    }
}
