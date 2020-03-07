package app.page;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import framework.PageObjectMethod;
import framework.PageObjectModel;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BasePage {
    //使用泛型规定好AndroidDriver为WebElement类型
    //所有page都要用driver所有提取成静态类直接继承并使用
    public static AndroidDriver<WebElement> driver;
    private PageObjectModel model=new PageObjectModel();

    //测试步骤参数化
    public void setParams(HashMap<String, Object> params) {
        this.params = params;
    }

    public static HashMap<String, Object> getParams() {
        return params;
    }

    private static HashMap<String, Object> params=new HashMap<>();

    //测试步骤结果读取
    public static HashMap<String, Object> getResults() {
        return results;
    }

    private static HashMap<String, Object> results=new HashMap<>();

    public BasePage(){
        model.engine="appium";
    }


    //通用元素定位与异常处理机制
//    public static WebElement findElement(By by) {
//        //todo: 递归是更好的
//        //todo: 如果定位的元素是动态变化位置
//
//        System.out.println(by);
//        try {
//            return driver.findElement(by);//这里的抛错时间是隐式等待时间
//        } catch (Exception e) {
//            handleAlert();
//
//            return driver.findElement(by);
//        }
//    }
    //递归方法处理元素定位与异常机制
    static int i = 1;
    public static WebElement findElement(By by) {
        //定位失败异常后才去处理黑名单
        try {
            System.out.println(by);
            return driver.findElement(by);//这里的抛错时间是隐式等待时间
        } catch (Exception e) {
            if (i > 3){   //设置最多递归三次
                i = 1;
                return driver.findElement(by);
            }
            else {
                System.out.println("进入弹框处理第" + i + "次");
                handleAlertByPageSource();
                i++;
                return findElement(by); //最后调用自身完成递归，防止多弹框同时出现造成定位失败
            }
        }
    }

    //递归最多3次处理点击异常
    static int j = 1;
    public static void click(By by) {
        System.out.println(by);
        try {
            driver.findElement(by).click();
        } catch (Exception e) {
            if (j>3){
                j=1;
                driver.findElement(by).click();
            }
            else {
                System.out.println("进入弹框处理第"+j+"次");
                handleAlertByPageSource();
                j++;
                click(by);//最后调用自身完成递归，防止多弹框同时出现造成定位失败
            }
        }
    }

    //封装findElenments
    public static List<WebElement> findElements(By by) {
        System.out.println(by);
        return driver.findElements(by);
    }

    static void handleAlert() {
        By tips = By.id("com.xueqiu.android:id/snb_tip_text");
        List<By> alertBoxs = new ArrayList<>();
        //todo: 不需要所有的都判断是否存在
        alertBoxs.add(By.id("com.xueqiu.android:id/image_cancel"));//升级弹框
        alertBoxs.add(tips);
        alertBoxs.add(By.id("com.xueqiu.android:id/md_buttonDefaultNegative"));//下次再说弹框
//        alertBoxs.add(By.xpath("dddd"));

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        alertBoxs.forEach(alert -> {
            List<WebElement> ads = driver.findElements(alert);
            //特殊处理tips 因为tips需要点击非tips区域才能关掉tips 增加判断分支
            if (alert.equals(tips)) {
                System.out.println("snb_tip found");
                Dimension size = driver.manage().window().getSize();//获取屏幕高宽
                try {
                    //这里还要findElenments一下确定tips还是存在的，再去点击。防止有的页面出现了该tips但一下就消失了导致点击报错
                    if (driver.findElements(tips).size() >= 1) {
                        new TouchAction(driver).tap(PointOption.point(size.width / 2, size.height / 2)).perform();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("snb_tip clicked");
                }
            } else if (ads.size() >= 1) {
                ads.get(0).click();//else 处理alert弹框
            }
        });
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    //很多弹框的话，最好的是直接定位到到底哪个弹框在界面上，元素的判断使用xpath
    public static void handleAlertByPageSource(){
        String pageSource = driver.getPageSource();//可以得到一个文本字符串，也可以理解为当前页面的xml
        //黑名单
        String adBox = "com.xueqiu.android:id/ib_close";
        String gesturePromptBox = "com.xueqiu.android:id/snb_tip_text";
        String evaluateBox = "com.xueqiu.android:id/md_buttonDefaultNegative";

        //将标记和定位符存入map
        HashMap<String,By> map = new HashMap<>();
        map.put(adBox,By.id("ib_close"));
        map.put(gesturePromptBox,By.id("snb_tip_text"));
        map.put(evaluateBox,By.id("md_buttonDefaultNegative"));

        //临时修改隐式等待时间，防止查找黑名单中元素过久
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        //遍历map，判断黑名单弹框元素是否存在于当前pageSource，存在即点击处理
        map.entrySet().forEach(entry ->{
            if (pageSource.contains(entry.getKey())){
                if (entry.getKey().equals("com.xueqiu.android:id/snb_tip_text")){
                    System.out.println("gesturePromptBox found");
                    Dimension size = driver.manage().window().getSize();
                    //防止其他页面有该tips但一下就消失了导致点击报错
                    try {
                        if (driver.findElements(By.id("com.xueqiu.android:id/snb_tip_text")).size()>=1);{
                            new TouchAction(driver).tap(PointOption.point(size.width / 2, size.height / 2)).perform();
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println("snb_tip clicked");
                    }
                }else {
                    driver.findElement(entry.getValue()).click();
                }
            }
        });
        //判断完成后将隐式等待时间恢复
        driver.manage().timeouts().implicitlyWait(8,TimeUnit.SECONDS);
    }

//    private static void handleAlertByPageSource() {
//        //todo: xpath匹配， 标记 定位
//        String xml = driver.getPageSource();
//        List<String> alertBoxs = new ArrayList<>();
//        alertBoxs.add("xxx");
//        alertBoxs.add("yyy");
//
//        alertBoxs.forEach(alert -> {
//            if (xml.contains(alert)) {
//                driver.findElement(By.id(alert)).click();
//            }
//        });
//
//    }

    public void parseSteps(){
        String method=Thread.currentThread().getStackTrace()[2].getMethodName();
        System.out.println(method);
        parseSteps(method);
    }
    public void parseSteps(String method) {
//        HashMap<String, List<HashMap<String, String>>> 可以取消steps的多余关键字
        //TODO: 参数化，把关键数据参数化到你的yaml中

        String path = "/" + this.getClass().getCanonicalName().replace('.', '/') + ".yaml";
        parseSteps(path, method);
    }
    public void parseSteps(String path, String method){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
//        TypeReference<HashMap<String, PageObjectMethod>> typeRef = new TypeReference<HashMap<String, PageObjectMethod>>() {
//        };
        try {
            model = mapper.readValue(
                    BasePage.class.getResourceAsStream(path), PageObjectModel.class
            );
            parseSteps(model.methods.get(method));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void parseSteps(PageObjectMethod steps){
        steps.getSteps().forEach(step->{
            WebElement element = null;

            //todo: 多个可能定位，多平台支持，多版本的支持
            String id=step.get("id").toString();
            if(id!=null){
                element=findElement(By.id(id));
            }else if(step.get("xpath")!=null){
                element=findElement(By.xpath(step.get("xpath").toString()));
            }else if(step.get("aid")!=null){
                element=findElement(MobileBy.AccessibilityId(step.get("aid").toString()));
            }else if(step.get("element")!=null){
                element=findElement(model.elements.get(step.get("element")).getLocator());
            }

            String send=step.get("send").toString();
//            params.entrySet().forEach(kv->{
//                send=send.replace("{"+ kv.getKey() +"}", kv.getValue().toString());
//            });



            if(send!=null){
                //配置文件中的参数替换
                for(Map.Entry<String, Object> kv: params.entrySet()){
                    String matcher="${"+kv.getKey()+"}";
                    if(send.contains(matcher)) {
                        System.out.println(kv);
                        send = send.replace(matcher, kv.getValue().toString());
                    }
                }
                element.sendKeys(send);

            }else if(step.get("get")!=null){
                String attribute=element.getAttribute(step.get("get").toString());
                getResults().put(step.get("dump").toString(), attribute);

            }else{
                element.click();
            }

        });
    }
}
