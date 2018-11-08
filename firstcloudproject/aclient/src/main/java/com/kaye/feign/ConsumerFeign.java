package com.kaye.feign;

import com.kaye.feign.impl.ConsumerFallBack;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: firstcloudproject
 * @description:
 * @author: yk
 * @create: 2018-11-06 10:15
 * @version: 0.0.1
 */
@FeignClient(value = "SERVICE-PROVISION", fallback = ConsumerFallBack.class)
public interface ConsumerFeign {

    @RequestMapping(value = "/server/add", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    String add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b);

}
