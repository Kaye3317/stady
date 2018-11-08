package com.kaye.controller;

import com.kaye.feign.ConsumerFeign;
import com.kaye.service.ConsumerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @program: firstcloudproject
 * @description: 一个消费服务的客户类
 * @author: yk
 * @create: 2018-11-05 15:46
 * @version: 0.0.1
 */
@RestController
@RequestMapping("/client")
public class ConsumerController {


    @Resource
    private ConsumerService consumerService;
    @Resource
    private ConsumerFeign consumerFeign;
//    @Value("${number}")
//    private String number;

    @GetMapping(value = "/consumer")
    public String consumer() {
//        return consumerFeign.add(Integer.valueOf(number), Integer.valueOf(number));
        return consumerService.add();
    }
}
