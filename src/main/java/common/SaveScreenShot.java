package common;

import arrow.TestResultListener;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.log4testng.Logger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveScreenShot {

    private static Logger logger = Logger.getLogger(TestResultListener.class);

    public static void saveScreenShot(WebDriver driver,String classname) {
        System.out.println("截图测试12：:0");
        System.out.println(driver);

        // 获取截图file
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            // 将图片移动到指定位置
            FileUtils.moveFile(file, new File(getFilePath(classname)));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String getFilePath(String classname) {
        // 创建储存图片的路径，不存在则创建
        File dir = new File("test-output/screenshot");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        String dateStr = dateFormat.format(new Date());
        // 获取新的文件名，包含时间，类名
        String fileName = dateStr + "_" + classname + "_" + ".jpg";
        // 获取文件路径
        String filePath = dir.getAbsolutePath() + "/" + fileName;
        return filePath;

    }

}
