package common;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClearText extends CommonObject {
    public ClearText(WebDriver driver) {
        super(driver);
    }

    public static void clearText(WebElement element){

        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.DELETE);

    }
    public void clear(String xPath){
        WebElement element=driver.findElement(By.xpath(xPath));
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.DELETE);

    }
}
