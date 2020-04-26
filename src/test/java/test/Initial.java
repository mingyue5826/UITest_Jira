package test;

import arrow.TestResultListener;
import common.CommonDriver;
import common.ConfigReader;
import common.UrlAddress;
import object.InitialObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class Initial {
    public CommonDriver commondriver;//初始化driver
    public InitialObject initial;

    @BeforeClass
    public void BeforeClass(){

        ConfigReader config = ConfigReader.getInstance();
		System.out.println("获取登录的url网址信息是："+ UrlAddress.login);
        commondriver=new CommonDriver();
        try {
            initial=new InitialObject(commondriver.start(UrlAddress.login));
        } catch (IOException e) {
            e.printStackTrace();
        }
        TestResultListener.driver=initial.getDriver();//监听器传参(截图需要)

    }
    @Test(description="初始化浏览器驱动监听器")
    public void InitialToTest()
    {
        System.out.println("开始运行测试");

    }
    @AfterClass
    public void AfterClass(){
        CommonDriver.setDriver(initial.getDriver());
        //initial.getDriver().quit();

    }
}
