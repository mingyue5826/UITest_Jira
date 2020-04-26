package object.jira;

import common.CommonObject;
import common.SaveScreenShot;
import common.Sleep;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class TodoScreenShootObject extends CommonObject {
    public TodoScreenShootObject(WebDriver driver){
        super(driver);
    }

    @FindBy(xpath = "//header//li/a[@href='/issues/']/../a[contains(text(),'问题')]")
    public WebElement filterSelect;//导航栏问题下拉框

    @FindBy(xpath = "//li/a[contains(text(),'搜索问题')]") public WebElement selectIssue;

    @FindAll(@FindBy(xpath = "//h4[contains(text(),'收藏的过滤器')]/..//ul/li/a[@class='filter-link']"))
    public List<WebElement> filterList;//收藏的过滤器

    @FindBy(xpath = "//h3[contains(text(),'没有查询到问题')]") public WebElement noIssues;

    @FindBy(xpath = "//div[@class='aui-item']/span/span[3]")
    public WebElement countPlace;//数量位置，一共两个，上下各一个

    /**
     * 截图经办人为测试人的代办任务
     * @param filter
     */
    public void todoScreenShoot(String filter){


        filterSelect.click();

        selectIssue.click();

        intelligentWait.intelligentWait(driver,10,"收藏的过滤器");
        Sleep.sleep(2000);


        for (WebElement we:filterList){
            if (we.getAttribute("innerText").contains(filter)){

                logger.debug("success find filter:"+filter);
                we.click();
                Sleep.sleep(3000);



                if (driver.getPageSource().contains("没有查询到问题")){
                    logger.info(filter+"无代办任务");
                }
                else {
                    String count = countPlace.getAttribute("innerText");

                    logger.info(filter+"的任务数量为："+count);

                    String fileName = filter + "-" + count;

                    new SaveScreenShot().saveScreenShot(driver,fileName);
                }


                break;

            }
        }


    }




}
