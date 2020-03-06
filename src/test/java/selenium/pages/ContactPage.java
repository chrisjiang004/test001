package selenium.pages;

import org.openqa.selenium.By;

public class ContactPage extends BasePage {
    public void list(){

    }
    public ContactPage addMember(String phone){
        findElement(By.id("username")).sendKeys(phone);
        findElement(By.id("memberAdd_acctid")).sendKeys(phone);
        findElement(By.id("memberAdd_phone")).sendKeys(phone);
        findElement(By.linkText("保存")).click();
        return this;

    }
}
