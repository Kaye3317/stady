package com.kaye.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @program: firstcloudproject
 * @description: 消费服务逻辑
 * @author: yk
 * @create: 2018-11-05 16:26
 * @version: 0.0.1
 */
@Service
public class ConsumerService {


    @Resource
    private RestTemplate restTemplate;

    @Value("${number}")
    private String number;

    @HystrixCommand(fallbackMethod = "fallbackMsg")
    public String add() {
        String j = restTemplate.getForObject("Http://service-provision/server/add?a=" + number + "&b=" + number, String.class);
        return j;
    }

    public String fallbackMsg() {
        return "error";
    }
}
