package selenium.testcase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import selenium.page.App;

import java.net.MalformedURLException;

public class TestWeWork {
    public static App app;
    @BeforeClass
    public static void beforeAll() throws MalformedURLException {
        //启动driver并登录成功
        app=new App();
        app.loginWithCookie();
        //数据清洗
        String phone="15600534762";
        app.toContact().delete(phone);
    }
    @Test
    public void add(){
        String phone="15600534762";
        app.toMemberAdd().add(phone, phone, phone);
//        assertThat();
    }

    @Test
    public void delete(){
        String phone="15600534763";
        app.toMemberAdd().add(phone, phone, phone).delete(phone);
    }

    @Test
    public void deleteCurrentPage(){
        app.toContact().deleteCurrentPage();
    }

    @Test
    public void importFromFile(){
        app.toContact().importFromFile("/Users/seveniruby/Downloads/通讯录批量导入模板.xlsx");
    }

    @AfterClass
    public static void afterAll() throws InterruptedException {
        app.quit();
    }


}
