package object;

import common.CommonObject;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ApplicationObject extends CommonObject {

    public ApplicationObject(WebDriver driver) {
        super(driver);
    }
    private static Logger logger = Logger.getLogger(ApplicationObject.class);

    @FindBy(xpath="//*[@id=\"header-nav\"]/nav/div[3]/ul/li[1]")
    public WebElement application;

    public void applicationClick(){
        application.click();
    }





}