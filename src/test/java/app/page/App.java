package app.page;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

//app启动和首页po放在了一起，所有testcase的beforall都要调用 总入口
public class App extends BasePage{
    private static App app;//把app设置成静态类，testcase需要直接调用app
    //这里统一实例化app出来 静态的类级别，case中就不用再去new了。单例模式
    public static App getInstance(){
        if(app==null){
            app=new App();
        }
        return app;
    }

    //启动app 配置好capability
    public void start() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "android");
        desiredCapabilities.setCapability("appPackage", "com.xueqiu.android");
        desiredCapabilities.setCapability("appActivity", ".view.WelcomeActivityAlias");
        //noreset 是否保留缓存
        desiredCapabilities.setCapability("noReset", false);
        //自动给所有权限赋值
        desiredCapabilities.setCapability("autoGrantPermissions", true);
        desiredCapabilities.setCapability("udid", System.getenv("UDID"));

        //使用本地的appiumserver
        URL remoteUrl = new URL("http://127.0.0.1:4723/wd/hub");
        //URL remoteUrl = new URL("http://192.168.31.199:4444/wd/hub");//使用远程或者内网ip的appiumserver

        //启动app 带上capability参数配置
        driver = new AndroidDriver(remoteUrl, desiredCapabilities);
        //隐式等待5秒
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //加速启动app 因为如果隐式等待设置的太大会显著减慢后面findElenments的时间 而只有在app启动时页面加载才会很慢
        //在app启动后再增加一个显式等待来特殊判断app启动成功
        long start=System.currentTimeMillis();  //加载开始时间毫秒
        new WebDriverWait(driver, 40)
                //自定义unitl方法去判断app是否启动成功：有搜索框或者升级框元素
                //lambda语法
                .until(x -> {
                    String xml=driver.getPageSource();
                    Boolean exist=xml.contains("home_search") || xml.contains("image_cancel") ;//
                    System.out.println((System.currentTimeMillis() - start)/1000);
                    System.out.println(exist);
                    return exist;
                });
    }

    public SearchPage toSearch() {
//        click(By.id("com.xueqiu.android:id/home_search"));
        parseSteps("/app/page/app.yaml", "toSearch");
        return new SearchPage();
    }

    public StockPage toStocks(){
//        click(By.xpath("//*[contains(@resource-id, 'tab_name') and @text='自选']"));
        parseSteps("/app/page/app.yaml", "toStocks");
        return new StockPage();

    }
}
