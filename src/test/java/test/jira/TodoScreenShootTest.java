package test.jira;

import common.*;
import object.jira.TodoScreenShootObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TodoScreenShootTest extends CommonTest {
    public TodoScreenShootObject todoScreenShootObject;
    public CommonObject commonObject;
    public GetTestData getTestData;
    public SetResultData setResultData;
    public IntelligentWait intelligentWait;
    public List<String> resultList = new ArrayList<String>();
    private String result;

    @DataProvider(name="todo")
    public  Object[][] words() throws IOException
    {
        return getTestData.getData("src\\test\\java\\data\\jira", "jira.xlsx", "todo");
    }


    @BeforeClass
    public void before() {
        getTestData = new GetTestData();
        intelligentWait = new IntelligentWait();
        commonObject = new CommonObject(CommonDriver.getDriver());
        todoScreenShootObject = new TodoScreenShootObject(CommonDriver.getDriver());

        CommonTest.setDriver( commonObject.getDriver());

    }

    @Test(dataProvider="todo")
    public void todoScreenShoot(String filter){

        todoScreenShootObject.todoScreenShoot(filter);

    }



}
