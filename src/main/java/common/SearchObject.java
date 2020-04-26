package common;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class SearchObject extends CommonObject{
    public SearchObject (WebDriver driver) {
        super(driver);
    }
    public CommonObject commonObject = new CommonObject(CommonDriver.getDriver());
    public IntelligentWait intelligentWait = new IntelligentWait();


    @FindBy(xpath = "//nz-input-group//input") public WebElement inputBox;
    @FindBy(xpath = "//nz-input-group//button") public WebElement searchButton;

    /**
     * 简单搜索：输入搜索内容，点击搜索按钮，并且添加搜索框为空的场景
     * @param searchValue
     */
    public void inputAndSearch(String searchValue){

        Sleep.sleep(1000);
        ClearText.clearText(intelligentWait.waitElement(driver,inputBox,5));
        Sleep.sleep(500);

        if (searchValue.contains("null")||searchValue.contains("NULL")||searchValue.contains("空")){
            Sleep.sleep(500);
            logger.info("SearchValue is null");
        }
        else {
            inputBox.sendKeys(searchValue);//输入搜索内容
            logger.info("SearchValue is \""+searchValue+"\"");
        }

        intelligentWait.waitElement(driver,searchButton,3).click();
    }


    /**
     * 获取列表第一行的名称和ID
     * @return
     */
    public List<String> getNameAndId(){
        commonObject = new CommonObject(CommonDriver.getDriver());

        boolean flag = true;
        int td = commonObject.columnNo("ID/名称");

//        inputBox.clear();
//        searchButton.click();
        commonObject.refreshButton.click();//刷新恢复页面
        Sleep.sleep(3000);

        WebElement element1 = driver.findElement(By.xpath("//tbody/tr[1]/td["+td+"]//span"));
        WebElement element2 = driver.findElement(By.xpath("//tbody/tr[1]/td["+td+"]//a[contains(@href,'/')]"));

        String name = element1.getText();
        String id = element2.getText();

        List<String> list = new ArrayList<String>();
        list.add(id);
        list.add(name);


        return list;
    }


    /**
     * 搜索后的断言,搜索内容为空校验为true
     * @param value
     * @param toassert
     * @return
     */
    public boolean toassertSearch(String value,String toassert){
        commonObject = new CommonObject(CommonDriver.getDriver());
        boolean flag = true;

        if (toassert.contains("成功")){

            List<WebElement> productList = driver.findElements(By.xpath("//tbody/tr"));//行

            if (value.contains("null")||value.contains("NULL")||value.contains("空")){
                if (productList.size() > 0)
                    flag = true;
                else
                    flag = false;
            }
            else {
                for(WebElement we:productList){
                    logger.debug("打印搜索结果："+we.getAttribute("innerText"));
//                    System.out.println("xpath:"+we.toString());
                    if(we.getAttribute("innerText").contains(value)){
                        flag=true;
                        break;
                    }
                    else {
                        flag = false;
                    }
                }
            }
        }

        else if (toassert.contains("失败")){

            String tbody = driver.findElement(By.xpath("//tbody")).getText();
            System.out.println("if失败toString："+tbody);

            System.out.println("is tbody null? >"+StringUtils.isBlank(tbody));

            if (StringUtils.isBlank(tbody))
                flag = true;
            else
                flag = false;

        }
        else {
            logger.info("toassert is " + toassert + ",don't contains [成功] or [失败]");
            flag = false;//无法确定的场景，暂时默认为false
        }

        logger.info("flag:"+flag);
        return flag;

    }


    /**
     * 获取列表第一个实例的id并且进行搜索
     * @return
     */
    public boolean getFirstIdAndSearch(){
        commonObject = new CommonObject(CommonDriver.getDriver());

        boolean flag = true;
        int td = commonObject.columnNo("ID/名称");

//        inputBox.clear();
//        searchButton.click();
        refreshButton.click();//刷新恢复页面
        Sleep.sleep(3000);

//        WebElement element = driver.findElement(By.xpath("//tbody/tr[1]/td["+td+"]//a[contains(@href,'#/')]"));
        WebElement element = driver.findElement(By.xpath("//tbody/tr[1]/td["+td+"]//a"));

        String id0 = element.getAttribute("innerText");

        inputAndSearch(id0);//输入并点击搜索
        Sleep.sleep(3000);

        if(productNameAndID().size()==1&&productNameAndID().get(0).getAttribute("innerText").contains(id0))
            flag=true;
        else
            flag=false;

        return flag;
    }

    /**
     * 获取列表第一个名称并搜索
     * @return
     */

    public boolean getFirstNameAndSearch(){
        commonObject = new CommonObject(CommonDriver.getDriver());

        boolean flag = true;
        int td = commonObject.columnNo("ID/名称");

//        inputBox.clear();
//        searchButton.click();
        refreshButton.click();//刷新恢复页面
        Sleep.sleep(3000);


        WebElement element = driver.findElement(By.xpath("//tbody/tr[1]/td["+td+"]/p/span"));

        String name0 = element.getAttribute("innerText");

        inputAndSearch(name0);//输入并点击搜索
        Sleep.sleep(1500);

        if(productNameAndID().get(0).getAttribute("innerText").contains(name0))
            flag=true;
        else
            flag=false;

        return flag;
    }



    /**
     * 搜索框，searchvalue为搜索框输入值，Type为输入类型（为ID,name两种)；ID为精确搜索，name为模糊搜索
     * @param searchValue
     * @param Type
     * @return
     */
    public boolean InputAndSearch(String searchValue,String Type){

        boolean flag=true;
        inputBox.clear();
        Sleep.sleep(5000);

        if(Type.equals("name")||Type.equals("名称")){
            inputBox.sendKeys(searchValue);
            Sleep.sleep(1000);
            searchButton.click();

            for(WebElement we:productNameAndID()){
                if(we.getAttribute("innerText").contains(searchValue))
                    flag=true;
                else
                    flag=false;
            }
        }

        if(Type.equals("ID")||Type.equals("id")){
            inputBox.sendKeys(searchValue);
            Sleep.sleep(1000);
            searchButton.click();
            Sleep.sleep(3000);

            if(productNameAndID().size()==1&&productNameAndID().get(0).getAttribute("innerText").contains(searchValue))
                flag=true;
            else
                flag=false;
        }

        return flag;

    }


}
