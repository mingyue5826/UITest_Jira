package arrow;

import common.CommonTest;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.log4testng.Logger;

public class TestResultListener extends TestListenerAdapter {
    private static Logger logger = Logger.getLogger(TestResultListener.class);
    public static WebDriver driver;
//	@Override
//	public void onStart(ITestContext testContext) { // 这里也是新加的，用于对context进行统一
//		this.testContext = testContext;
//		System.out.println("当前context对象数据是："+testContext.toString());
//		browser = String.valueOf(testContext.getCurrentXmlTest().getParameter("browserName"));
//		System.out.println("当前browser对象数据是："+browser);
//		System.out.println("onstart时当前driver对象数据是："+testContext.getAttribute("SELENIUM_DRIVER"));
//		super.onStart(testContext);
//	}


    @Override
    public void onTestStart(ITestResult tr) {
        super.onTestStart(tr);
        logger.info(tr.getName() + " 测试用例开始执行！");
    }


    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        System.out.println("错误测试");
        takeScreenShot(tr);
        logger.info(tr.getName() + " 测试用例执行失败！");
        //    test.log(LogStatus.INFO,"TakesScreenshot ",test.addScreenCapture("../img/"+tr.getName()+".png"));
        //    test.log(LogStatus.FAIL, tr.getThrowable());
        //    extent.endTest(test);

    }

    public void takeScreenShot(ITestResult tr){
        CommonTest commonTest=(CommonTest) tr.getInstance();
        commonTest.takescreen(tr.getName());

    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        super.onTestSkipped(tr);
//		WebDriver webDriver = (WebDriver) testContext.getAttribute("SELENIUM_DRIVER");
        logger.warn(tr.getName() + " 测试用例由于某些原因被跳过！");
    }

	@Override
	public void onTestSuccess(ITestResult tr) {
		super.onTestSuccess(tr);
		logger.info(tr.getName() + " 测试用例执行成功");
	}
    @Override
    public void onFinish(ITestContext testContext) {
        super.onFinish(testContext);

    }



 /*   @Override
    public void onFinish(ITestContext testContext) {
        super.onFinish(testContext);
        // List of test results which we will delete later
        ArrayList<ITestResult> testsToBeRemoved = new ArrayList<ITestResult>();
        // collect all id's from passed test
        Set<Integer> passedTestIds = new HashSet<Integer>();
        for (ITestResult passedTest : testContext.getPassedTests().getAllResults()) {
            logger.info("执行成功的用例 = " + passedTest.getName());
            passedTestIds.add(getId(passedTest));
        }

        // Eliminate the repeat methods
        Set<Integer> skipTestIds = new HashSet<Integer>();
        for (ITestResult skipTest : testContext.getSkippedTests().getAllResults()) {
            logger.info("被跳过的用例 = " + skipTest.getName());
            // id = class + method + dataprovider
            int skipTestId = getId(skipTest);

            if (skipTestIds.contains(skipTestId) || passedTestIds.contains(skipTestId)) {
                testsToBeRemoved.add(skipTest);
            } else {
                skipTestIds.add(skipTestId);
            }
        }

        // Eliminate the repeat failed methods
        Set<Integer> failedTestIds = new HashSet<Integer>();
        for (ITestResult failedTest : testContext.getFailedTests().getAllResults()) {
            logger.info("执行失败的用例 = " + failedTest.getName());
            // id = class + method + dataprovider
            int failedTestId = getId(failedTest);

            // if we saw this test as a failed test before we mark as to be
            // deleted
            // or delete this failed test if there is at least one passed
            // version
            if (failedTestIds.contains(failedTestId) || passedTestIds.contains(failedTestId) || skipTestIds.contains(failedTestId)) {
                testsToBeRemoved.add(failedTest);
            } else {
                failedTestIds.add(failedTestId);
            }
        }

        // finally delete all tests that are marked
        for (Iterator<ITestResult> iterator = testContext.getFailedTests().getAllResults().iterator(); iterator.hasNext();) {
            ITestResult testResult = iterator.next();
            if (testsToBeRemoved.contains(testResult)) {
                logger.info("移除重复失败的用例 = " + testResult.getName());
                iterator.remove();
            }
        }

    }

    private int getId(ITestResult result) {
        int id = result.getTestClass().getName().hashCode();
        id = id + result.getMethod().getMethodName().hashCode();
        id = id + (result.getParameters() != null ? Arrays.hashCode(result.getParameters()) : 0);
        return id;
    }

    private File saveScreenShot(ITestResult tr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String mDateTime = formatter.format(new Date());
        String fileName = mDateTime + "_" + tr.getName();
        String filePath = "";
        File destFile =null;
        try {
            // 这里可以调用不同框架的截图功能
//			WebDriver augmentedDriver = new Augmenter().augment(driver);
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//		    File screenshot = ((TakesScreenshot)augmentedDriver).
//		                        getScreenshotAs(OutputType.FILE);
//			filePath = "\\result\\screenshot\\"+ fileName + ".jpg";
            filePath = "test-output/screenshot/"+ fileName + ".jpg";
            destFile=new File(filePath);

            FileUtils.copyFile(screenshot, destFile);
            logger.info("["+fileName + "] 截图成功，保存在：" + "[ " + filePath + " ]");

        } catch (Exception e) {
            filePath = "["+fileName+"]" + " ,截图失败，原因：" + e.getMessage();
            logger.error(filePath);
            e.printStackTrace();//后期省略
        }

        if (!"".equals(filePath)) {
            Reporter.setCurrentTestResult(tr);
            Reporter.log(filePath);
            // 把截图写入到Html报告中方便查看
            filePath = "screenshot/"+ fileName + ".jpg";
//			Reporter.log("<img src=\""+System.getProperty("user.dir")+"\\"+ filePath + "\" width='600px' height='320px' >");
            Reporter.log("<img src=\""+filePath + "\" width='600px' height='320px' >");
        }
        return destFile;
    }*/
}
