package app.page;

import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class StockPage extends BasePage {

    //取消已关注的自选股
    public StockPage deleteAll(){
        click(By.id("com.xueqiu.android:id/edit_group"));//进入管理自选股票页面
        click(By.id("com.xueqiu.android:id/check_all"));//点击全选
        click(By.id("com.xueqiu.android:id/cancel_follow"));//点击删除自选按钮
        click(By.id("com.xueqiu.android:id/tv_right"));//点击确定取消关注
        click(By.id("com.xueqiu.android:id/action_close"));//点击完成按钮回到行情页

        //parseSteps();//使用步骤参数方法
        return this;
    }

    //获取所有自选股的list
    public List<String> getAllStocks(){

        handleAlert();

        List<String> stocks=new ArrayList<>();
        findElements(By.id("com.xueqiu.android:id/portfolio_stockName")).forEach(element -> {
            stocks.add(element.getText());
        });
        System.out.println(stocks);
        return stocks;
    }

    public StockPage addDefaultSelectedStocks(){
        click(By.id("com.xueqiu.android:id/subscribe"));//点击一键添加自选 元素会变
        return this;
    }

    public SearchPage toSearch(){
        click(By.id("com.xueqiu.android:id/action_search"));
        return new SearchPage();
    }
}
