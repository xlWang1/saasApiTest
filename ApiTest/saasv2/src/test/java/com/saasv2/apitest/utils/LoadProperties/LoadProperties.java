package com.saasv2.apitest.utils.LoadProperties;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class LoadProperties {
    public static String Host;
    public static String codeUrl;
    public static String loginUrl;
    public static String captcha;
    public static String account;
    public static String password;
    public static String orgIDurl;
    public static String getPhtIdUrl;
    public static String getPhtStudentListUrl;
    public static String getPhtPlanInfoUrl;
    public static String pythonPath;
    public static String pythonFilePath;
    public static String getPlanListUrl;
    public static String phtName;
    public static String getScoreSearchListUrl;
    public static String urlDb;
    public static String testLoginSuccess;
    public static String testLoginFailure;
    public static String testLoginErrorCode;
    public static String loginDataOnlyOnce;

    public static void loadProperties(){
        try(InputStream resourceAsStream = LoadProperties.class.getClassLoader().getResourceAsStream("uri.properties")) {
            Properties properties = new Properties();
            if (resourceAsStream != null) {
                properties.load(new InputStreamReader(resourceAsStream));
                Host = properties.getProperty("HOST");
                codeUrl =Host + properties.getProperty("codeUrl");
                loginUrl =Host + properties.getProperty("loginUrl");
                captcha = properties.getProperty("captcha");
                account = properties.getProperty("account");
                password = properties.getProperty("password");
                pythonPath = properties.getProperty("pythonPath");
                phtName = properties.getProperty("phtName");
                urlDb = properties.getProperty("urlDb");
                testLoginSuccess = properties.getProperty("testLoginSuccess");
                testLoginFailure = properties.getProperty("testLoginFailure");
                testLoginErrorCode = properties.getProperty("testLoginErrorCode");
                loginDataOnlyOnce = properties.getProperty("loginDataOnlyOnce");
                pythonFilePath = properties.getProperty("pythonFilePath");
                orgIDurl = Host + properties.getProperty("getOrgIdUrl");
                getPhtIdUrl = Host + properties.getProperty("getPhtIdUrl");
                getPhtStudentListUrl = Host + properties.getProperty("getPhtStudentListUrl");
                getPhtPlanInfoUrl = Host + properties.getProperty("getPhtPlanInfoUrl");
                getPlanListUrl = Host + properties.getProperty("getPlanListUrl");
                getScoreSearchListUrl = Host + properties.getProperty("getScoreSearchListUrl");
            }else {
                System.out.println("加载配置文件失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
