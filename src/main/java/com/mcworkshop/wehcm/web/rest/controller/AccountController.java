package com.mcworkshop.wehcm.web.rest.controller;

import com.mcworkshop.wehcm.core.exception.ValidationException;
import com.mcworkshop.wehcm.core.persistence.AccountRepository;
import com.mcworkshop.wehcm.web.rest.resource.MapResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by markfredchen on 6/23/15.
 */
@Controller
@RequestMapping(value = "/api/accounts")
@PropertySource("classpath:application.properties")
public class AccountController extends BaseController{

    @Autowired
    private AccountRepository accountRepository;

    @Value("${wehcm.account.path}")
    private String accountPath;


    @RequestMapping(value = "/{accountOID}", method = RequestMethod.GET)
    @ResponseBody
    public String getAccount(@PathVariable("accountOID") String accountOID) {
        File accountFolder = new File(accountPath + accountOID.toString());
        if(!accountFolder.exists()) {
            accountFolder.mkdir();
            return "Folder Created";
        } else {
            return accountFolder.getAbsoluteFile().toString();
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/upload/css/{accountOID}", method = RequestMethod.POST)
    public void uploadCSS(@RequestParam("file") MultipartFile file, @PathVariable("accountOID") String accountOID) throws IOException {
        System.out.println(accountOID);
        String accountFilePath = accountPath + accountOID.toString();
        File accountFolder = new File(accountFilePath);
        if(!accountFolder.exists()) {
            accountFolder.mkdir();
        }
        System.out.println(file.getOriginalFilename());
        if(file.getOriginalFilename().endsWith(".css") && !file.isEmpty()) {
            System.out.println(file.getName());
            file.transferTo(new File(accountFilePath + "/defaule.css"));
        } else {
            throw new ValidationException();
        }
    }

    @RequestMapping(value = "/accountOID", method = RequestMethod.GET)
    @ResponseBody
    public MapResource getAccountOIDByDomainName(HttpServletRequest request){
        String serverName = request.getServerName();
        System.out.println(serverName);
        MapResource result = new MapResource("accountOID", accountRepository.findOneByDomain(serverName).getAccountOID());
        return result;
    }

}
