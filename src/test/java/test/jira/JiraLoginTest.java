package test.jira;

import common.*;
import object.InitialObject;
import object.jira.JiraLoginObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JiraLoginTest extends CommonTest{
    public CommonDriver commondriver;//初始化driver
    public InitialObject initial;
    public CommonObject commonObject;

    public JiraLoginObject jiraLoginObject;
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
        jiraLoginObject=new JiraLoginObject(new CommonDriver().start(url));

        jiraLoginObject.setLogin(username, password);
        CommonTest.setDriver(jiraLoginObject.getDriver());
        intelligentWait.intelligentWait(jiraLoginObject.driver,20,toassert);
        result=getTestData.GetResult(jiraLoginObject,toassert);
        resultList.add(result);
        Sleep.sleep(1000);
        Assert.assertTrue(jiraLoginObject.getDriver().getPageSource().contains(toassert));
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
