package common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class RenameObject extends CommonObject {
    public RenameObject(WebDriver driver){
        super(driver);
    }

    //详情页的改名按钮
    @FindBy(xpath = "//label[contains(text(),'名称：')]/..//i") public WebElement detailPage_RenameButton;
    //改名弹框的输入框
    @FindBy(xpath = "//div[@class='ant-popover-content']//input") public WebElement renameInputBox;
    @FindBy(xpath = "//div[@class='ant-popover-content']//button[1]") public WebElement updateOkButon;

    //详情页名称位置
    @FindBy(xpath = "//label[contains(text(),'名称：')]/..//p") public WebElement detailPageNameLabel;



    /**
     * @param num 重命名第几个
     * @param renamePlace   重命名位置：列表/详情
     * @param updateName 修改后的名称
     */
    public void renameObject(int num,String renamePlace,String updateName){

        int thead= columnNo("ID/名称");

        if (thead > -1){ //如果表头存在[ID/名称]
            if (renamePlace.contains("列表")){ //列表页对第一个实例进行改名
                driver.findElement(By.xpath("//tr["+num+"]/td["+thead+"]//span")).click();//点击名称位置，激活改名按钮
                Sleep.sleep(500);
                driver.findElement(By.xpath("//tr["+num+"]/td["+thead+"]//i")).click();//点击列表改名按钮
            }

            else if (renamePlace.contains("详情")||renamePlace.contains("概览")){//第一个实例详情页修改名称
                driver.findElement(By.xpath("//tr["+num+"]/td["+thead+"]//a")).click();//点击id进入详情
                Sleep.sleep(5000);

                detailPage_RenameButton.click();//点击详情页改名按钮
            }

            inputAndRename(updateName);
        }

        else if (thead == -1){
            logger.error("当前列表无此列：[ID/名称]，或者列表未加载完成");
        }

        //无法点击名称位置时可以使用这个方式
//        WebElement namePlace = driver.findElement(By.xpath("//tr["+num+"]/td["+thead+"]//span"));
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("arguments[0].click()", namePlace);//点击名称位置激活改名按钮
    }


    /**
     * 根据传参num，选择改名第几个，并判断是否改名成功
     * @param num
     * @param renamePlace
     * @param updateName
     * @return
     * 前提条件：名称在[ID/名称]列，测试用例里的'renamePlace'必须包含[列表/详情/概览]
     */
    public boolean isRename(int num,String renamePlace,String updateName){
        boolean flag=false;
        int thead= columnNo("ID/名称");

        if (thead > -1){ //如果表头存在[ID/名称]
            if (renamePlace.contains("列表")){ //列表页对第一个实例进行改名

                driver.findElement(By.xpath("//tr["+num+"]/td["+thead+"]//span")).click();//点击名称位置，激活改名按钮
                Sleep.sleep(500);
                driver.findElement(By.xpath("//tr["+num+"]/td["+thead+"]//i")).click();//点击列表改名按钮
            }
            else if (renamePlace.contains("详情")||renamePlace.contains("概览")){//第一个实例详情页修改名称
                driver.findElement(By.xpath("//tr["+num+"]/td["+thead+"]//a")).click();//点击id进入详情
                Sleep.sleep(5000);

                detailPage_RenameButton.click();//点击详情页改名按钮

            }

            inputAndRename(updateName);


            //断言部分
            if (renamePlace.contains("列表")){
                Sleep.sleep(2000);
                String newName = driver.findElement(By.xpath("//tr["+num+"]/td["+thead+"]//span")).getAttribute("innerText");
                System.out.println(newName);

                if (newName.contains(updateName))
                    flag = true;
                else
                    flag = false;
            }

            else if (renamePlace.contains("详情")||renamePlace.contains("概览")){//第一个实例详情页修改名称
                Sleep.sleep(2000);
                String newName = driver.findElement(By.xpath("//label[contains(text(),'名称：')]/..//span")).getAttribute("innerText");

                if (newName.equals(updateName))
                    flag = true;
                else
                    flag = false;
            }
        }

        else if (thead == -1){
            logger.error("当前列表无此列：[ID/名称]，或者列表未加载完成");
            flag = false;
        }

        return flag;
    }



    /**
     * 根据传参实例名称选择性改名
     * @param oldName
     * @param renamePlace
     * @param updateName
     */
    public void renameByName(String renamePlace,String oldName,String updateName){
        if (renamePlace.contains("列表")){ //列表页对第一个实例进行改名
            listRename(oldName,updateName);
        }
        else if (renamePlace.contains("详情")||renamePlace.contains("概览")){//第一个实例详情页修改名称
            detailPageRename(oldName,updateName);
        }
    }



    /**
     * 根据传参实例名称选择性改名并断言
     */
    public boolean renameByNameAndAssert(String renamePlace,String oldName,String updateName,String toassert){
        boolean flag = false;

        if (renamePlace.contains("列表")){ //列表页对第一个实例进行改名
            flag = isListRenameSuccess(oldName,updateName,toassert);
        }

        else if (renamePlace.contains("详情")||renamePlace.contains("概览")){//第一个实例详情页修改名称
            flag = isDetailPageRenameSuccess(oldName,updateName,toassert);
        }

        return flag;
    }



    /**
     * 在实例列表页改名
     * @param oldName
     * @param updateName
     */
    public void listRename(String oldName,String updateName){

        int thead= columnNo("ID/名称");
        List<WebElement> nameAndIds = driver.findElements(By.xpath("//tr/td[" + thead + "]"));

        if (thead > -1){
            for (WebElement we:nameAndIds){
                if (we.getAttribute("innerText").contains(oldName)){
                    we.findElement(By.xpath("..//td["+thead+"]//span")).click();//点击名称位置，激活改名按钮
                    Sleep.sleep(500);
                    we.findElement(By.xpath("..//td["+thead+"]//i")).click();//点击列表改名按钮

                    inputAndRename(updateName);
                }
                else {
                    logger.error("couldn't find this instance:"+oldName);
                }
            }
        }
        else if (thead == -1){
            logger.error("当前列表无此列：[ID/名称]，或者列表未加载完成");
        }

    }


    /**
     * 详情页改名
     */
    public void detailPageRename(String oldName,String updateName){

        int thead= columnNo("ID/名称");
        List<WebElement> nameAndIds = driver.findElements(By.xpath("//tr/td[" + thead + "]"));

        if (thead > -1){
            for (WebElement we : nameAndIds) {
                if (we.getAttribute("innerText").contains(oldName)){
                    we.findElement(By.xpath("..//a[contains(@href,'#/')]")).click();//点击id进入详情
                    Sleep.sleep(5000);

                    detailPage_RenameButton.click();//点击详情页改名按钮
                    inputAndRename(updateName);
                    break;
                }
                else {
                    logger.error("couldn't find this instance:"+oldName);
                }
            }
        }
        else if (thead == -1){
            logger.error("当前列表无此列：[ID/名称]，或者列表未加载完成");
        }
    }






    /**
     * 实例列表页改名，并断言
     */
    public boolean isListRenameSuccess(String oldName,String updateName,String toassert){
        boolean flag = false;

        int thead= columnNo("ID/名称");
        List<WebElement> nameAndIds = driver.findElements(By.xpath("//tr/td[" + thead + "]"));

        if (thead > -1){
            for (WebElement we:nameAndIds){

                if (we.getAttribute("innerText").contains(oldName)){
                    we.findElement(By.xpath("..//td["+thead+"]//span")).click();//点击名称位置，激活改名按钮
                    Sleep.sleep(500);
                    we.findElement(By.xpath("..//td["+thead+"]//i")).click();//点击列表改名按钮

                    inputAndRename(updateName);
                    IntelligentWait intelligentWait=new IntelligentWait();
                    intelligentWait.intelligentWait(driver,10,toassert);
//                    Sleep.sleep(3000);

                    //断言是否改名成功
                    if (we.getAttribute("innerText").contains(updateName))
                        flag = true;
                    else
                        flag = false;
                }
                else {
                    logger.error("couldn't find this instance:"+oldName);
                }
            }
        }
        else if (thead == -1){
            logger.error("当前列表无此列：[ID/名称]，或者列表未加载完成");
        }



        return flag;
    }




    /**
     * 详情页改名并断言
     */
    public boolean isDetailPageRenameSuccess (String oldName,String updateName,String toassert){
        boolean flag = false;

        int thead= columnNo("ID/名称");
        List<WebElement> nameAndIds = driver.findElements(By.xpath("//tr/td[" + thead + "]"));

        if (thead > -1){
            for (WebElement we : nameAndIds) {
                if (we.getAttribute("innerText").contains(oldName)){
                    we.findElement(By.xpath("..//a[contains(@href,'#/')]")).click();//点击id进入详情
                    Sleep.sleep(5000);

                    detailPage_RenameButton.click();//点击详情页改名按钮
                    inputAndRename(updateName);

                    IntelligentWait intelligentWait=new IntelligentWait();
                    intelligentWait.intelligentWait(driver,10,toassert);

                    if (detailPageNameLabel.getAttribute("innerText").contains(updateName))
                        flag = true;
                    else
                        flag = false;
                }
                else {
                    logger.error("couldn't find this instance:"+oldName);
                }
            }
        }
        else if (thead == -1){
            logger.error("当前列表无此列：[ID/名称]，或者列表未加载完成");
        }


        return flag;

    }





    /**
     * 打开改名输入框后，直接输入并点击确定，前面的方法调用
     * @param updateName
     */
    private void inputAndRename(String updateName){

        Sleep.sleep(500);
        renameInputBox.clear();
        renameInputBox.sendKeys(updateName);

        updateOkButon.click();//点击确定
        logger.info("Rename Over");
    }




}
