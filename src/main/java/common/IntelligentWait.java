package common;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class IntelligentWait {

    public void intelligentWait(WebDriver driver, int timeOut, final String toassert) {
        try {
            (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {

                public Boolean apply(WebDriver driver) {
                    return driver.getPageSource().contains(toassert);
                }
            });
        } catch (TimeoutException e) {
            Assert.fail("超时!! " + timeOut + " 秒之后还没找到元素:"+toassert,e);
        }
    }

    /**
     * 元素智能等待，timeout单位为秒
     * @param driver
     * @param we
     * @param timeout
     * @return
     */
    public static WebElement waitElement(WebDriver driver, WebElement we, int timeout)
    {
        new WebDriverWait(driver,timeout).until(ExpectedConditions.visibilityOf(we));
        return we;
    }



}
