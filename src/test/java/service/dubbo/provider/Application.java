package service.dubbo.provider;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import service.dubbo.impl.GreetingsServiceImpl;
import service.dubbo.inter.GreetingsService;
import java.util.concurrent.CountDownLatch;

//要先安装zookeep 再ide直接运行或者使用mvn启动生产者服务
//向注册中心注册服务
public class Application {
    //使用redis也可以 zookeep也相当于是个数据库。允许存数据
    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");

    public static void main(String[] args) throws Exception {
        ServiceConfig<GreetingsService> service = new ServiceConfig<>();//服务配置实例
        service.setApplication(new ApplicationConfig("first-dubbo-provider"));//服务应用的名字
        service.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));//使用zk来注册
        service.setInterface(GreetingsService.class);//服务声明使用的契约
        service.setRef(new GreetingsServiceImpl());//最终实现的类
        service.export();

        System.out.println("dubbo service started");
        new CountDownLatch(1).await();//不停止服务
    }
}