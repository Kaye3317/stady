package com.kaye.nettyserver.run;

import com.kaye.nettyserver.server.NettyServer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * spring boot 启动的时候执行一些东西
 *
 * @author yk
 * @since 2019/2/20$ 11:27$
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Resource
    private NettyServer nettyServer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        nettyServer.run();
    }
}
