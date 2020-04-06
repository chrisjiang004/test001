package selenium.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

//所有测试用例的入口，提供了登录功能，提供了去其他菜单的入口方法
public class App extends BasePage {
    public App loginWithCookie() throws MalformedURLException {
        String url="https://work.weixin.qq.com/";

        ChromeOptions chromeOptions=new ChromeOptions();
        chromeOptions.setCapability("pageLoadStrategy","none");
        driver = new ChromeDriver(chromeOptions);
        //使用seleniumserver来启动driver，可以查看日志
        //1.下载安装sleniumserver 2.启动seleniumsever -g 日志方式启动 3.修改driver 为RemoteWebDriver
        //driver=new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"), chromeOptions);

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(url);//打开浏览器并访问url
        driver.manage().window().maximize();//需要最大化窗口才能定位到元素
        driver.findElement(By.linkText("企业登录")).click();

        System.out.println(driver.manage().getCookies());

        driver.manage().addCookie(new Cookie("wwrtx.refid", "9156994571588966"));
//        driver.manage().addCookie(new Cookie("wwrtx.sid", "PvmFAAW3_ZQOnOfp5SzMi6G2ksLVrBXqM1nz3SnTzUm58TfxXhPA3kekfJjTJMK1"));
//        driver.manage().addCookie(new Cookie("wwrtx.sid", "PvmFAAW3_ZQOnOfp5SzMi5tkp2rMpNclSpq9ybt_pT1A-Y6z1C1Zx7f06qrRVCcR"));
        driver.navigate().refresh();
        return this;
    }
    public ContactPage toContact(){
        findElement(By.linkText("通讯录")).click();
        return new ContactPage();

    }

    public ContactPage toMemberAdd(){
        //find click
        findElement(By.linkText("添加成员")).click();
        return new ContactPage();
    }

    public BroadcastPage toGroupMessage(){
        findElement(By.linkText("管理工具")).click();
        findElement(By.cssSelector(".ww_icon_AppGroupMessageBig")).click();
        return new BroadcastPage();
    }
}
