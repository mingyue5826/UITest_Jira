package common;

import org.openqa.selenium.WebDriver;

import java.util.Iterator;
import java.util.Set;

public class WindowChange extends CommonObject {
    public WindowChange(WebDriver driver) {
        super(driver);
    }

    public void changeWindow() {
        String currentHandle = driver.getWindowHandle();  	//获取当前窗口句柄
        Set<String> allHandle = driver.getWindowHandles();  	//获取所有窗口句柄
        Iterator<String> it = allHandle.iterator();  	//迭代allhandle里面的句柄
        while(it.hasNext()) {                        	//用it.hasNext()判断时候有下一个窗口,如果有就切换到下一个窗口
            driver.switchTo().window(it.next());     	//切换到新窗口
        }
    }

    public void closeWindow() {

        try {
            String winHandleBefore = driver.getWindowHandle();//关闭当前窗口前，获取当前窗口句柄
            Set<String> winHandles = driver.getWindowHandles();//使用set集合获取所有窗口句柄

            driver.close();//关闭窗口

            Iterator<String> it = winHandles.iterator();//创建迭代器，迭代winHandles里的句柄
            while (it.hasNext()) {//用it.hasNext()判断时候有下一个窗口,如果有就切换到下一个窗口
                String win = it.next();//获取集合中的元素
                if (!win.equals(winHandleBefore)) { //如果此窗口不是关闭前的窗口
                    driver.switchTo().window(win);//切换到新窗口
                    logger.info("Switch Window From " + winHandleBefore + " to " + win);
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
