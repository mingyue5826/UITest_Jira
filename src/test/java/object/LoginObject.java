package object;

import common.CommonObject;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginObject extends CommonObject {

    public LoginObject(WebDriver driver) {
        super(driver);
    }
    private static Logger logger = Logger.getLogger(LoginObject.class);

    //定位元素
    @FindBy(id="username1")
    public  WebElement username;

    @FindBy(id="password1")
    public WebElement password;

    @FindBy(xpath="//*[@id='kc-login1']")
    public WebElement loginButton;

    @FindBy(xpath="//*[@id=\"page-main\"]/div/div/app-email-binding/div/div[2]/div[2]/button[2]")
    public WebElement emailbind;

    public void setLogin(String user,String pwd)
    {

        logger.info("进入登录页面开始登陆");
        username.clear();
        username.sendKeys(user);
        password.clear();
        password.sendKeys(pwd);
        loginButton.click();


//        emailbind.click();

    }



}