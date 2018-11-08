package com.kaye.feign.impl;

import com.kaye.feign.ConsumerFeign;
import org.springframework.stereotype.Component;


/**
 * @program: firstcloudproject
 * @description:
 * @author: yk
 * @create: 2018-11-06 10:14
 * @version: 0.0.1
 */
@Component
public class ConsumerFallBack implements ConsumerFeign {
    //    @Override
    public String add(Integer a, Integer b) {
        return "Feign Error";
    }
}
