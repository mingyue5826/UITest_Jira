package object.jira;

import common.CommonObject;
import common.Sleep;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class JiraDoneObject extends CommonObject {
    public JiraDoneObject(WebDriver driver){
        super(driver);
    }

    @FindAll(@FindBy(xpath = "//th[contains(text(),'问题类型')]/..//th"))
    public List<WebElement> theads;

    @FindAll(@FindBy(xpath = "//tr/td[2]//a")) public List<WebElement> projects;//问题链接


    public int getTdNum(){


        int num = 0;

        for (int i=0;i<theads.size();i++){
            if (theads.get(i).getAttribute("innerText").contains("待测试")){
                num = i+1;
                logger.info("[待测试]的表头序号为"+num);
                break;
            }
        }

        return num;
    }

    public Integer todoNum(){
        Integer num = 0;

        int td = getTdNum()-1;
        WebElement we = driver.findElement(By.xpath("//th[contains(text(),'总计')]/../td["+td+"]/a"));

        String str = we.getAttribute("innerText");
        num = Integer.parseInt(str);
        logger.info("待测试的数量为"+num);

        return num;
    }


    //点击待测试的总计链接
    public void clickTaskTest(){

        int td = getTdNum()-1;

        WebElement we = driver.findElement(By.xpath("//th[contains(text(),'总计')]/../td["+td+"]/a"));

        we.click();

        Sleep.sleep(5000);
    }

    @FindBy(xpath = "//div[@class='command-bar']//li//span[contains(text(),'转至测试中')]/..")
    public WebElement toTesting;

    @FindBy(xpath = "//div[@class='command-bar']//li//span[contains(text(),'完成')]/..")
    public WebElement done;

    @FindBy(xpath = "//select[@id='resolution']") public WebElement resolutionSelect;//解决结果下拉框
    @FindBy(xpath = "//option[@value='10202']") public WebElement solved;//已解决
    @FindBy(xpath = "//input[@id='log-work-time-logged']") public WebElement timeInput;
    @FindBy(xpath = "//input[@value='完成']") public WebElement Enter;


    //点击流程
    public void setClick(){

        projects.get(0).click();//点击第一个jira的链接
        intelligentWait.intelligentWait(driver,10,"待测试");
        Sleep.sleep(3000);

        toTesting.click();//点击待测试
        Sleep.sleep(2000);

        done.click();//点击完成
        Sleep.sleep(2000);

        resolutionSelect.click();//点击解决结果下拉框
        Sleep.sleep(1000);

        solved.click();//点击已解决
        Sleep.sleep(1500);

        timeInput.clear();//清空时间
        timeInput.sendKeys("1d");

        Enter.click();//点击完成
        Sleep.sleep(1000);


    }



}
