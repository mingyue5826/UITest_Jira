package common;

import org.testng.log4testng.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class ConfigReader {
    private static Logger logger = Logger.getLogger(ConfigReader.class);
    public GetTestData getTestData;
    private static ConfigReader cr;
    private int retryCount = 0;
    private String sourceCodeDir = "src";
    private String sourceCodeEncoding = "UTF-8";
    private String environmentName = "pro";
    private static final String RETRYCOUNT = "retrycount";
    private static final String SOURCEDIR = "sourcecodedir";
    private static final String SOURCEENCODING = "sourcecodeencoding";
    private static final String HOMEURL="home";
    private static final String INDEXURL="index";
    private static final String LOGINURL="login";
    private static final String ENVIRONMENT="environment";
    private static final String CONFIGFILE ="config.properties";

    private ConfigReader() {
        readConfig(System.getProperty("user.dir")+"\\src\\test\\java\\data\\"+CONFIGFILE);
    }

    public static ConfigReader getInstance() {
        if (cr == null) {
            cr = new ConfigReader();
        }
        return cr;
    }

//    @DataProvider(name="login")
//    public  Object[][] words(String sheetName) throws IOException {
//        return getTestData.getData("src\\test\\java\\data","login.xlsx",sheetName);
//    }

//    @Test(dataProvider = "login")
    private void readConfig(String fileName){
        Properties properties = getConfig(fileName);
        if (properties != null) {
            String sRetryCount = null;
            Enumeration<?> en = properties.propertyNames();
            while (en.hasMoreElements()) {
                String key = (String) en.nextElement();
                if (key.toLowerCase().equals(RETRYCOUNT)) {
                    sRetryCount = properties.getProperty(key);
                }
                if (key.toLowerCase().equals(SOURCEDIR)) {
                    sourceCodeDir = properties.getProperty(key);
                }
                if (key.toLowerCase().equals(SOURCEENCODING)) {
                    sourceCodeEncoding = properties.getProperty(key);
                }
                if (key.toLowerCase().equals(HOMEURL)) {
                    UrlAddress.home = properties.getProperty(key);
                }
                if (key.toLowerCase().equals(INDEXURL)) {
                    UrlAddress.index= properties.getProperty(key);
                }
                if (key.toLowerCase().equals(LOGINURL)) {
                    UrlAddress.login= properties.getProperty(key);
                }

                if (key.toLowerCase().equals(ENVIRONMENT)) {
                    UrlAddress.environment= properties.getProperty(key);
                }


            }
            if (sRetryCount != null) {
                sRetryCount = sRetryCount.trim();
                try {
                    retryCount = Integer.parseInt(sRetryCount);
                } catch (final NumberFormatException e) {
                    throw new NumberFormatException("Parse " + RETRYCOUNT + " [" + sRetryCount + "] from String to Int Exception");
                }
            }
        }
    }

    public int getRetryCount() {
        return this.retryCount;
    }

    public String getSourceCodeDir() {
        return this.sourceCodeDir;
    }

    public String getSrouceCodeEncoding() {
        return this.sourceCodeEncoding;
    }

    public String getEnvironmentName(){
        return this.environmentName;
    }

    /**
     *
     * @param propertyFileName
     *
     * @return
     */
    private Properties getConfig(String propertyFileName) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(propertyFileName));
        } catch (FileNotFoundException e) {
            properties = null;
            logger.warn(System.getProperty("user.dir"));
            logger.warn("FileNotFoundException:" + propertyFileName);
        } catch (IOException e) {
            properties = null;
            logger.warn("IOException:" + propertyFileName);
        }
        return properties;
    }
}
