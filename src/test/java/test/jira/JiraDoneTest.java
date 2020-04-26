package test.jira;

import common.*;
import object.jira.JiraDoneObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class JiraDoneTest extends CommonTest {
    public JiraDoneObject jiraDoneObject;
    public CommonObject commonObject;
    public GetTestData getTestData;
    public SetResultData setResultData;
    public IntelligentWait intelligentWait;
    public List<String> resultList = new ArrayList<String>();
    private String result;



    @BeforeClass
    public void before() {
        getTestData = new GetTestData();
        intelligentWait = new IntelligentWait();
        commonObject = new CommonObject(CommonDriver.getDriver());
        jiraDoneObject = new JiraDoneObject(CommonDriver.getDriver());

        CommonTest.setDriver( commonObject.getDriver());

    }

    @Test
    public void JiraDone(){


        int count = jiraDoneObject.todoNum();
        logger.info("总共需要点击的[待测试]任务数量有"+count);

        jiraDoneObject.clickTaskTest();

        int length = jiraDoneObject.projects.size();
        logger.info("本页需要点击的[待测试]任务数量有"+length);

        for (int i = 0;i<count;i++){

            int size = jiraDoneObject.projects.size();
            logger.info("本页需要点击的[待测试]任务数量有"+size);

            jiraDoneObject.setClick();

            intelligentWait.intelligentWait(commonObject.driver,5,"完成");
//            Assert.assertFalse(commonObject.getDriver().getPageSource().contains("待测试"));

            driver.navigate().back();
            Sleep.sleep(1000);

            driver.navigate().refresh();
            Sleep.sleep(3000);
        }


    }


    @AfterClass
    public void after() {

    }
}
