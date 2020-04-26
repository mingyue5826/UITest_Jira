package test;
import common.CommonDriver;
import object.ApplicationObject;
import org.testng.annotations.Test;

public class ApplicationTest {

    public ApplicationObject applicationObject;

 //  @BeforeClass

    @Test
    public void applicationTest(){
        applicationObject=new ApplicationObject(CommonDriver.getDriver());
        System.out.println(applicationObject.application.getText());
        applicationObject.applicationClick();
    }

   // @AfterClass

}
