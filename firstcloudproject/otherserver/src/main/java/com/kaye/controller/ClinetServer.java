package com.kaye.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @program: firstcloudproject
 * @description: 一个服务模块
 * @author: yk
 * @create: 2018-11-05 11:43
 * @version: 0.0.1
 */
@RestController
@RequestMapping("/server")
public class ClinetServer {

    private final Logger logger = LoggerFactory.getLogger(ClinetServer.class);

    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addadd(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b) {
        ServiceInstance instance = client.getLocalServiceInstance();
        Integer r = a + b;
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        logger.info("/add, host:" + instance.getHost() + ", service_id:" + instance.getServiceId() + ", result:" + r);
        return "" + r;
    }


}
