package service.dubbo.consumer;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import service.dubbo.inter.GreetingsService;

//消费端建立通讯
public class Application {
    //zookper服务的地址
    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");

    public static void main(String[] args) {
        ReferenceConfig<GreetingsService> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("first-dubbo-consumer"));//消费者名称
        reference.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));//访问注册中心
        reference.setInterface(GreetingsService.class);//引用契约
        GreetingsService service = reference.get();//获得接口服务 建立通讯完成得到 server服务实例
        String message = service.sayHi("dubbo");//调用接口进行测试
        System.out.println(message);
    }
}