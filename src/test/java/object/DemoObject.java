package object;

import common.CommonObject;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DemoObject extends CommonObject {

    public DemoObject(WebDriver driver) {
        super(driver);
    }
    private static Logger logger = Logger.getLogger(DemoObject.class);


    //查询元素的方法
    @FindBy() public WebElement object1; //定位单个元素

}
