package app.page;

import org.openqa.selenium.By;

import java.util.HashMap;

public class SearchPage extends BasePage{
    private By inputBox=By.id("com.xueqiu.android:id/search_input_text");
    private By name=By.xpath("(//*[@resource-id='com.xueqiu.android:id/name'])[1]");


    //search方法改成 参数化步骤方式
    public SearchPage search(String keyword) {
//        HashMap<String, Object> data=new HashMap<>();
//        data.put("keyword", keyword);
//        setParams(data);
//        parseSteps();
        App.driver.findElement(inputBox).sendKeys(keyword);
        click(name);
        return this;
    }

    public Float getCurrentPrice() {
        parseSteps();
//        MobileElement el4 = (MobileElement) findElement(By.id("com.xueqiu.android:id/current_price"));
        return Float.valueOf(getResults().get("price").toString());

    }

    //使用步骤参数化方式
    public App cancel(){
        click(By.id("com.xueqiu.android:id/action_close"));
        //parseSteps();
        return new App();

    }

    public SearchPage select(){
       click(By.id("com.xueqiu.android:id/follow_btn"));
//        parseSteps();
        return this;
    }
}
