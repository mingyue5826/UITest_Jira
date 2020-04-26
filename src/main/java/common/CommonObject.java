package common;

//import com.sun.istack.internal.logging.Logger;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.NoSuchElementException;

public class CommonObject {

    public WebDriver driver;
    public static Logger logger = Logger.getLogger(CommonObject.class);
    public IntelligentWait intelligentWait = new IntelligentWait();
    public CommonObject(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }
    public WebDriver getDriver() {
        return driver;
    }
    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }


    //左侧导航栏菜单
    public WebElement menuProduct(String productName){
        return driver.findElement(By.xpath("//aside//a[contains(text(),'"+productName+"')]"));
    }

    //button标签根据内容text定位元素
    public WebElement tagElement_button(String text_button){ return driver.findElement(By.xpath("//button/span[contains(text(), '"+text_button+"')]/..")); }

    //label标签根据内容text定位元素
    public WebElement tagElement_label(String text_label){ return driver.findElement(By.xpath("//label/span[contains(text(), '"+text_label+"')]/..")); }

    //li标签根据内容text定位元素
    public WebElement tagElement_li(String text_li){ return driver.findElement(By.xpath("//li[contains(text(), '"+text_li+"')]")); }

    //a标签根据内容test定位元素
    public WebElement tagElement_a(String text_a){ return driver.findElement(By.xpath("//a[contains(text(), '"+text_a+"')]")); }



    @FindBy(xpath = "//button//span[contains(text(),'确定')]/..") public WebElement Enter;

    @FindBy(xpath = "//div[@id='header-nav']//nz-dropdown[1]//a") public WebElement xialaButton;

    @FindAll(@FindBy(xpath = "//*[@id='dropdown-aside']/div[2]/div/div/div/a")) public List<WebElement> allProductList;


    /**
     * 控制台产品与服务中索引各产品
     */
    public WebElement search(String productName){

        logger.info("start click menu---");
        try{
            intelligentWait.intelligentWait(driver,10,"产品与服务");
            Sleep.sleep(2000);
            xialaButton.click();

        }catch (NoSuchElementException e){
            logger.warn("xialaButton load failed, waiting...");
            driver.navigate().refresh();//刷新

//            intelligentWait.waitElement(driver,xialaButton,10).click();
            Sleep.sleep(6000);

            xialaButton.click();
        }
        Sleep.sleep(2000);

        WebElement product=null;

        for(WebElement we:allProductList){
            if(we.getAttribute("innerText").contains(productName)) {
                logger.debug("[" + productName + "] match success");
                product = we;
                break;
            }
            else {
                logger.debug("this product couldn't match-"+we.getAttribute("innerText"));
            }

        }

        logger.debug("start click "+product.getText());
        return product;

    }



    @FindAll(@FindBy(xpath = "//aside//a")) public List<WebElement> menuProductsList;//左侧导航栏菜单

    /**遍历点击到需要的目录，从产品与服务到导航栏菜单
     * @param products
     */
    public void productsGuide(String... products){

        int length = products.length;

        driver.navigate().refresh();//刷新
//        Sleep.sleep(5000);

        search(products[0]).click();//产品与服务->产品
        Sleep.sleep(1500);

        if (length > 1){
            for (int i=1;i<length;i++){
                String pro = products[i];

                for (WebElement we:menuProductsList){
                    if (we.getAttribute("innerText").contains(pro)){
                        we.click();
                        logger.debug("click ["+pro+"] success");
                        Sleep.sleep(300);
                    }
                }
            }
        }

    }


    @FindBy(xpath = "//nz-input-group//input") public WebElement inputBox;

    @FindBy(xpath = "//nz-input-group//button") public WebElement searchButton;

    @FindBy(xpath = "//nz-input-group/../button[1]")public WebElement refreshButton;

    //名称和id 的Element,只适用于名称在第二列的情况
    @FindAll(@FindBy(xpath = "//tbody[@class='ant-table-tbody']/tr/td[2]")) public List<WebElement> productNameAndID;


    /**
     * 名称和id 的Element
     * 适用于名称在任何列的情况
     * @return
     */
    public List<WebElement> productNameAndID(){
        int td=columnNo("ID/名称");//获取[ID/名称]是第几列
        List<WebElement> elementsList = driver.findElements(By.xpath("//tbody[@class='ant-table-tbody']/tr/td["+td+"]"));
        return elementsList;
    }




    /**
     * 订单支付
     * @param orderType
     * @param chargeMode
     * @param buyNum
     * @param buyTime
     * @return
     */
    public boolean payOrder(String orderType,String chargeMode,String buyNum,String buyTime){
        boolean flag=false;
        String actualOrderType=driver.findElement(By.xpath("//*[@class='ant-card-body']/div[1]/p/span")).getAttribute("innerText");
        if(chargeMode.contains("按需")) {
            String actualChargeMode = driver.findElement(By.xpath("//*[@class='ant-card-body']/div[2]/div/h4[1]")).getAttribute("innerText");
            String actualBuyNum = driver.findElement(By.xpath("//*[@class='ant-card-body']/div[2]/div/h4[2]")).getAttribute("innerText");


            if(actualOrderType.contains(orderType)&&actualChargeMode.contains(chargeMode)
                    &&actualBuyNum.contains(buyNum))
                flag=true;

            Assert.assertTrue(flag);


        }
        if(chargeMode.contains("包年包月")){
            String actualChargeMode = driver.findElement(By.xpath("//*[@class='ant-card-body']/div[2]/div/h4[1]")).getAttribute("innerText");
            String actualBuyTime=driver.findElement(By.xpath("//*[@class='ant-card-body']/div[2]/div/h4[2]")).getAttribute("innerText");
            String actualBuyNum = driver.findElement(By.xpath("//*[@class='ant-card-body']/div[2]/div/h4[3]")).getAttribute("innerText");

            if(actualOrderType.contains(orderType)&&actualChargeMode.contains(chargeMode)
                    &&actualBuyNum.contains(buyNum)&&actualBuyTime.contains(buyTime))
                flag=true;
        }
        return flag;
    }


    /**
     * 订单结束，返回实例列表
     */
    @FindBy(xpath = "//span[contains(text(),'返回实例列表')]/..") public WebElement backList;



    /**
     * 回到控制台主页面
     */
    public void toConsole(){
        driver.findElement(By.xpath("//*[@id=\"header-nav\"]/nav/div[2]/span/a")).click();

    }



    @FindBy(xpath = "//div[@class='pull-right']//i[contains(@class,'anticon-setting')]/..") public WebElement userDefinedListBtn;
    /**
     * 用户自定义列表,button按钮仅限网络模块使用，其他产品未验证
     * @param thead 不限制个数的表头，调用时传参要操作的列表名
     */
    public void userDefinedList(String... thead) {
        try {
            intelligentWait.waitElement(driver,userDefinedListBtn,10).click();
//            userDefinedListBtn.click();//点击自定义列表按钮
        }catch (NoSuchElementException e){
            logger.warn("e.getMessage()："+e.getMessage());
            logger.warn("列表加载失败，开始第二次加载");
            Sleep.sleep(5000);
            userDefinedListBtn.click();
        }

        int length = thead.length;
        logger.info("本次设置的列表数为："+length);

        for (int i=0;i<length;i++){
            String list=thead[i];
            logger.info("本次设置："+list);
//            Sleep.sleep(1000);

            WebElement th = driver.findElement(By.xpath("//nz-checkbox-group//span[contains(text(),'"+list+"')]/.."));
            intelligentWait.waitElement(driver,th,5).click();
        }
        Enter.click();
    }



    /**
     * 确认列表中某表头是否显示 显示为true，隐藏为false
     * @param thead
     * @return
     */
    public boolean isTheadShow(String thead){
        WebElement we = driver.findElement(By.tagName("thead"));

        if (we.getAttribute("innerText").contains(thead)){
            logger.info("["+thead+"] is shown.");
            return true;
        } else {
            logger.info("["+thead+"] is hidden.");
            return false;
        }
    }




    @FindBy(xpath = "//nz-select[@nzplaceholder='选择区域']//div[2]")
    public WebElement regionNow;//当前的region

    /**选择区域
     * @param region
     */
    public void regionSelector(String region){
        if (regionNow.getAttribute("innerText").contains(region)){
            logger.info("No need to switch regions.");
        }
        else {
            regionNow.click();
            driver.findElement(By.xpath("//li[contains(text(),'"+region+"')]")).click();
            logger.info("switch region to ["+region+"] success");
        }
    }



    /**
     * 获取某个表头是第几列
     * @param theadName
     * @return 返回列的序号(从1开始)
     */
    public int columnNo(String theadName){
        int column = -1;
        List<WebElement> elements = driver.findElements(By.xpath("//thead//th"));
        logger.debug("td number is :"+elements.size());

        for (int i=0;i<elements.size();i++){    //例如：获取【ID/名称】是第几列
            if (elements.get(i).getAttribute("innerText").contains(theadName)) {
                column = i+1;
                logger.info("[" + theadName + "] is the " + column + " column.");
                break;
            }
        }

        return column;
    }




    @FindAll(@FindBy(xpath = "//nz-card")) public List<WebElement> regions;
    @FindBy(xpath = "//aside//a[contains(text(),'概览')]") public WebElement dashboard;

    /**
     * 获取概览页数量
     * @param productName 产品线名称
     * @param region 区域，概览页的显示名称
     * @param instanceName 产品的名，概览页显示的名称
     * @return
     */
    public Integer getInstanceNum(String productName,String region,String instanceName){
        Integer num = 0;

        productsGuide(productName);
        dashboard.click();
//        Sleep.sleep(5000);
        intelligentWait.intelligentWait(driver,10,region);//等待页面加载完成

        for (WebElement we:regions){
            if (we.getAttribute("innerText").contains(region)){
                String str = we.findElement(By.xpath("..//div[contains(text(),'"+instanceName+"')]/../div[1]")).getText();
                num = Integer.parseInt(str);
                logger.info("the number of "+instanceName+"is:"+num);
                break;
            }
            else {
                logger.error("this region dose't match ["+region+"]");
            }
        }

        return num;
    }




    /**
     * 判断元素是否存在
     * @param webElement
     *
     * eg:
     * WebElement we = driver.findElement(By.tagName("input"));
     * boolen flag = new CommonObject().isElementVisible(we);
     */
    public boolean isElementVisible(WebElement webElement){

        try {
            webElement.getSize();
            return true;
        }catch (Exception e){
            return false;
        }

    }




}
