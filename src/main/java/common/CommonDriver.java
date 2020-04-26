package common;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class CommonDriver {

    public static Logger logger = Logger.getLogger(CommonObject.class);
    private static File file;
    private static WebDriver driver ;


    public WebDriver start(String url) throws IOException {
        //driver的位置
        file=new File("drivers\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        System.out.println(file.getAbsolutePath());

        File file = new File("drivers");

        String downloadpath = file.getAbsolutePath();
        System.out.println("浏览器新设置的下载绝对路径是："+downloadpath);
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadpath);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("window-size=1920,1080");
        driver=new ChromeDriver(options);//初始化driver-提git用

//        driver = new ChromeDriver();
//        driver.manage().window().maximize();//初始化driver-本地调试用

//        driver.get(UrlAddress.login);//url在配置文件写死
        System.out.println("CommonDriver.class, start get url:"+url);
        driver.get(url);//通过environment选择sheet页
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);//加载元素等待
        return driver;
    }

    public static WebDriver getDriver()
    {
        return driver;
    }
    public static void setDriver(WebDriver dr)
    {
        driver=dr;
    }

    public void refresh(){
        //刷新浏览器
        driver.navigate().refresh();
    }

    public void quit(){
        //退出浏览器
        driver.close();
        driver.quit();
    }

    public static void deleteDriver()
    {
        driver=null;

    }




}
