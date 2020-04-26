package common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ElementExist extends CommonObject{
    public ElementExist(WebDriver driver) {
        super(driver);
    }
    //判断元素是否存在
    public boolean isElementExist(WebDriver driver, By selector){
        try{
            driver.findElement(selector);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}


//调用示例：
/**元素是否存在测试类
 * public class ElementExitTest extends CommonObject {
 *     public ElementExitTest(WebDriver driver){
 *         super(driver);
 *     }
 *
 *     public void ExistTest() {
 *
 *         ElementExist elementExist=new ElementExist();
 *         By selector = new By.ByXPath("//tr/td[1]");
 *         if (elementExist.isElementExist(driver,selector)){
 *             System.out.println("元素存在");
 *         }else {
 *             System.err.println("元素不存在");
 *         }
 *     }
 * }
 */
