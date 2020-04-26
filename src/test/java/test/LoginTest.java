package test;

import common.*;
import object.InitialObject;
import object.LoginObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginTest extends CommonTest{
    public CommonDriver commondriver;//初始化driver
    public InitialObject initial;
    public CommonObject commonObject;
    public LoginObject loginobject;
    public GetTestData getTestData;
    public SetResultData setResultData;
    public IntelligentWait intelligentWait;
    public List<String> resultList =new ArrayList<String>();
    private String result;
    public String environment = UrlAddress.environment;


    @DataProvider(name="login")
    public  Object[][] words() throws IOException {

        logger.info("start read environment: "+environment);
        return getTestData.getData("src\\test\\java\\data","login.xlsx",environment);
    }

    @BeforeClass
    public void before(){
        getTestData=new GetTestData();
        intelligentWait=new IntelligentWait();
    }


    @Test(dataProvider="login")
    public void loginTest(String url,String username,String password,String toassert) throws IOException {

        logger.info("start login");
        loginobject=new LoginObject(new CommonDriver().start(url));

        loginobject.setLogin(username, password);
        CommonTest.setDriver(loginobject.getDriver());
        intelligentWait.intelligentWait(loginobject.driver,20,toassert);
        result=getTestData.GetResult(loginobject,toassert);
        resultList.add(result);
        Sleep.sleep(1000);
        Assert.assertTrue(loginobject.getDriver().getPageSource().contains(toassert));
        //loginobject.getDriver().quit();
    }



    @AfterClass
    public void after()
    {
//        loginobject.getDriver().quit();
//        System.out.println(resultList);
//        setResultData=new SetResultData();
//        try {
//            setResultData.SetResult("src\\test\\java\\data","login.xlsx","sheet1",resultList,"result");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }


}
