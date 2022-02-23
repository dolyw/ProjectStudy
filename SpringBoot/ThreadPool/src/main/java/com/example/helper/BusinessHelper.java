package com.example.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * BusinessHelper
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/6 14:15
 */
@Component
public class BusinessHelper {

    private final static Logger logger = LoggerFactory.getLogger(BusinessHelper.class);

    public String handle1() throws Exception {
        Thread.sleep(5000L);
        logger.info("handle1");
        return "handle1";
    }

    public String handle2() throws Exception {
        Thread.sleep(3000L);
        logger.info("handle2");
        return "handle2";
    }

}
