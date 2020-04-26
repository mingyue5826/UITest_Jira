package common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;



/**
 * 删除功能
 * deButton:删除按钮的xpath
 * return: boolean
 */

public class DeleteObject extends CommonObject {
    public DeleteObject(WebDriver driver) {
        super(driver);
    }
    public IntelligentWait intelligentWait = new IntelligentWait();

    @FindBy(xpath = "//button/span[contains(text(),'确定')]/..") public WebElement Enter;//确定按钮

    public boolean delete(String deButton){
        boolean flag=false;
        Sleep.sleep(5000);
        List<WebElement> list=driver.findElements(By.xpath(deButton));
        if(list!=null){
            list.get(0).click();//设置删除第一个
//            Actions action = new Actions(driver);
//            action.sendKeys(Keys.ENTER).perform();//此处确定按钮不知如何定位，因焦点在确定按钮，设置enter键来确定
            Enter.click();
            flag=true;
        }

        return flag;
    }
    public boolean deleteChoice(int delete,String deButton){
        boolean flag=false;
        Sleep.sleep(5000);
        List<WebElement> list=driver.findElements(By.xpath(deButton));
        if(list!=null){
            list.get(delete-1).click();//设置删除第几个
//            Actions action = new Actions(driver);
//            action.sendKeys(Keys.ENTER).perform();//此处确定按钮不知如何定位，因焦点在确定按钮，设置enter键来确定
            Enter.click();
            flag=true;
        }

        return flag;
    }



    @FindBy(xpath = "//tr[1]//a[contains(text(),'更多')]") public WebElement moreAction;
    @FindBy(xpath = "//li[contains(text(),'删除')]") public WebElement delete_li;
    @FindBy(xpath = "//a[contains(text(),'删除')]") public WebElement delete_a;
    /**
     * 删除
     * @param isInMore [删除]是否存在于[更多]-[删除]
     */
    public void delete( boolean isInMore){

        CommonObject commonObject = new CommonObject(CommonDriver.getDriver());
//        IntelligentWait intelligentWait = new IntelligentWait();

        if (isInMore){
            intelligentWait.waitElement(CommonDriver.getDriver(),moreAction,10).click();
//            moreAction.click();
            delete_li.click();
        }else {
//            delete_a.click();
            intelligentWait.waitElement(CommonDriver.getDriver(),delete_a,10).click();
        }

        Sleep.sleep(1500);

        commonObject.Enter.click();

    }

    /**
     * 根据名称来删除
     * @param name  实例名称
     * @param isInMore [删除]是否存在于[更多]-[删除]
     */
    public void deleteByname(String name, boolean isInMore){

        CommonObject commonObject = new CommonObject(CommonDriver.getDriver());
        List<String> list = new ArrayList<String>();



        for (WebElement we:commonObject.productNameAndID()){
            list.add(we.getAttribute("innerText"));

            logger.info("wait the instance "+name+" ...");
            if (we.getAttribute("innerText").contains(name)){
                logger.info("find instance success, at "+list.size()+"");
                if (isInMore){
                    logger.info("start click [更多].");
                    we.findElement(By.xpath("..//a[contains(text(),'更多')]")).click();
                    driver.findElement(By.xpath("//li[contains(text(),'删除')]")).click();
                }
                else {
                    we.findElement(By.xpath("..//a[contains(text(),'删除')]")).click();
                }

                break;
            }
        }

        System.out.print("find this instances[");
        for (int i=0;i<list.size();i++){
            System.out.print(list.get(i));
            if (i<list.size()-2){
                System.out.print(",");
            }
        }
        System.out.print("]");



        intelligentWait.waitElement(driver,commonObject.Enter,5).click();
//        Sleep.sleep(1500);
//        commonObject.Enter.click();

    }

}
