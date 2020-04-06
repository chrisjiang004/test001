package selenium.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    //定义静态driver类 方便方法直接使用
    //使用seleniumserver运行时需要修改成RemoteWebDriver类
    //public static RemoteWebDriver driver;
    public static WebDriver driver;

    //把显式等待封装禁元素定位方法。且默认等待5秒 因为java目前还没有默认参数的功能
    public WebElement findElement(By by){
        return findElement(by, 5);
    }
    //这个定位方法是用来调试用，或者增加等待时间。会打印查找的元素
    public WebElement findElement(By by, int timeout){
        System.out.println(by);
        if(timeout>0) {
            waitClickable(by, timeout);
            System.out.println("clickable");
        }
        return driver.findElement(by);
    }
    //封装带超时时间参数的显示等待的点击方法
    public void waitClickable(By by, int timeout){
        new WebDriverWait(driver, timeout).until(ExpectedConditions.elementToBeClickable(by));
    }
    //封装更严格更好用的显示等待的点击方法。默认等待时间3秒
    public void waitClickable(By by){
        //先判断是否可见
        new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(by));
        //再判断是否可以点击，有时候可以点击但是不可见，会导致点击失败
        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(by));

    }
    //封装关闭浏览器的方法。默认等待20秒方便查看结果
    public void quit() throws InterruptedException {
        Thread.sleep(20000);
        driver.quit();
    }
}
