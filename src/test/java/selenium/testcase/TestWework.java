package selenium.testcase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import selenium.pages.APP;
import selenium.pages.BasePage;

public class TestWework {
    public static APP app;//app设置成静态类 方便其他testcase使用
    @BeforeClass
    public static void beforeall(){
        app = new APP();
        app.loginWithCookie();
    }

    @Test
    public void testAdd(){
        String phone = "13482280002";
        app.toMemberAdd().addMember(phone);

    }

    @AfterClass
    public static void afterAll() throws InterruptedException {
        Thread.sleep(3000);
        BasePage.driver.quit();
    }
}
