package com.ehking.wallet.api.v1_0.controllers;

import java.util.HashMap;
import java.util.Map;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ehking.wallet.Versions;

@RestController
@RequestMapping(value = "/api/wallet", produces = Versions.V1_0, consumes = Versions.V1_0)
public class WalletController extends BaseController{

    private static final Logger logger = LoggerFactory
            .getLogger(WalletController.class);

    @Autowired
    private Mapper mapper;

    /**
     * 首页信息
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> index() {
        long s = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<String, Object>();
        return map;
    }


}
