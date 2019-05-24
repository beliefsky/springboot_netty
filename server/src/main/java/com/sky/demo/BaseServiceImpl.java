package com.sky.demo;

import org.springframework.stereotype.Service;

@Service
public class BaseServiceImpl implements IBaseService {
    @Override
    public void test() {
        System.out.println("调用 service服务");
    }
}
