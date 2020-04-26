package object.jira;

import common.CommonObject;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class JiraLoginObject extends CommonObject {

    public JiraLoginObject(WebDriver driver) {
        super(driver);
    }
    private static Logger logger = Logger.getLogger(JiraLoginObject.class);


    //定位元素
    @FindBy(id="login-form-username")
    public  WebElement username;

    @FindBy(id="login-form-password")
    public WebElement password;

    @FindBy(id="login")
    public WebElement loginButton;



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