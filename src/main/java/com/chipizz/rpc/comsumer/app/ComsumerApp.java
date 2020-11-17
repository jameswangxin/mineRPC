package com.chipizz.rpc.comsumer.app;

import com.chipizz.rpc.comsumer.service.CalculatorRemoteImpl;
import com.chipizz.rpc.provider.service.Calculator;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class  ComsumerApp {
    private static Logger logger = LoggerFactory.getLogger(ComsumerApp.class);

    public static void main(String[] args) {
        Calculator calculator = new CalculatorRemoteImpl();
        int res = calculator.add(6, 3);
        logger.info("result is {}", res);
    }
}
