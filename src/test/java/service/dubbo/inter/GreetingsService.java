package service.dubbo.inter;

//接口文件 契约。消费端调用时知道类别
public interface GreetingsService {
    String sayHi(String name);
}
