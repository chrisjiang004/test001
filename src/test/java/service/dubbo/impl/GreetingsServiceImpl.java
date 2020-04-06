package service.dubbo.impl;

import service.dubbo.inter.GreetingsService;

//开发写的具体接口代码。通过jia包导入
public class GreetingsServiceImpl implements GreetingsService {
    @Override
    public String sayHi(String name) {
        return "hi, " + name;
    }
}
