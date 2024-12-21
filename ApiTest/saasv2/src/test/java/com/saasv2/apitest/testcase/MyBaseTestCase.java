package com.saasv2.apitest.testcase;
import com.saasv2.apitest.utils.LoadProperties.LoadProperties;
import com.saasv2.apitest.utils.httpclientutil.HttpMethods;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
public class MyBaseTestCase{
    public static HttpMethods httpMethods;
    @BeforeSuite
    public static void LoadResources(){
        LoadProperties.loadProperties();
        System.out.println("加载配置完成");
    }
    @AfterSuite
    public void closeClient(){
        httpMethods.closeClient();
        System.out.println("释放连接客户端");
    }

    public MyBaseTestCase() {
        httpMethods = new HttpMethods();
    }
}
