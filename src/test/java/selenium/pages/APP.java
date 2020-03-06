package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

// 启动 登录 后的index页面po
public class APP extends BasePage {
    //把登录提取到这
    public APP loginWithCookie(){
        String URL = "https://work.weixin.qq.com/";
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
        driver.get(URL);
        driver.manage().window().maximize();
        driver.findElement(By.linkText("企业登录")).click();

        //使用已登录的cookie来跳过扫码登录
        //driver.manage().addCookie(new Cookie("wwrtx.refid","35083257501778528"));
        String sid = "c5pb39jzyAtw8O62DKUlyA1wmEvfu6JTMQej6M0qgAxNCJqT_ViuIU3nnBlXpS9n";
        driver.manage().addCookie(new Cookie("wwrtx.sid",sid));
        //需要刷新页面才能登录成功
        driver.navigate().refresh();
        return this;
    }
    public ContactPage toContact(){
        return new ContactPage();

    }

    public ContactPage toMemberAdd(){
        driver.findElement(By.linkText("添加成员")).click();
        return new ContactPage();

    }
}
