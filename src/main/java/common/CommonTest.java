package common;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class CommonTest {
    public static WebDriver driver;
    public static void setDriver(WebDriver dr){
        driver=dr;
    }
    public static Logger logger = Logger.getLogger(CommonTest.class);

    public void takescreen(String filename){
        SaveScreenShot.saveScreenShot(driver,filename);

    }
}
