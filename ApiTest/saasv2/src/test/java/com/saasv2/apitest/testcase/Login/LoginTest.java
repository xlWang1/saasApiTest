package com.saasv2.apitest.testcase.Login;
import com.alibaba.fastjson.JSONObject;
import com.saasv2.apitest.testcase.MyBaseTestCase;
import com.saasv2.apitest.utils.LoadProperties.LoadProperties;
import com.saasv2.apitest.utils.httpclientutil.Headers;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Map;
import java.util.Objects;
public class LoginTest extends MyBaseTestCase {

    @Test(dataProvider = "loginInfo", dataProviderClass = LoginDataProvider.class, description = "成功登录",groups = {"success","multiple"})
    public void testLoginSuccess(JSONObject jsonObject) {
        executeLoginTest(jsonObject, "操作成功");
    }
    @Test(dataProvider = "loginInfo", dataProviderClass = LoginDataProvider.class, description = "成功登录一次",groups = {"success","once"})
    public void testLoginSuccessOnce(JSONObject jsonObject) {
        executeLoginTest(jsonObject, "操作成功");
    }

    @Test(dataProvider = "loginInfo", dataProviderClass = LoginDataProvider.class, description = "登录失败",groups = {"failure","multiple"})
    public void testLoginFailure(JSONObject jsonObject) {
        executeLoginTest(jsonObject, "用户名或密码不正确");
    }

    @Test(dataProvider = "loginInfo", dataProviderClass = LoginDataProvider.class, description = "验证码错误",groups = {"failure","multiple"})
    public void testLoginErrorCode(JSONObject jsonObject) {
        executeLoginTest(jsonObject,"验证码校验错误",1);
    }
    private void executeLoginTest(JSONObject jsonObject, String expectedMessage) {
        executeLoginTest(jsonObject,expectedMessage,0);
    }
    private void executeLoginTest(JSONObject jsonObject, String expectedMessage,int offsetCode) {
        Map<String, Object> stringObjectMap = LoginDataProvider.testGetCaptchaSuccess();
        int code;
        code = (int) stringObjectMap.get("code");
        jsonObject.put("code", code + offsetCode + "");
        jsonObject.put("randomStr", stringObjectMap.get("randomStr"));
        try (CloseableHttpResponse response = httpMethods.DoPostReturnResponse(LoadProperties.loginUrl, jsonObject)) {
            JSONObject object = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
            JSONObject data = object.getJSONObject("data");
            if (!Objects.isNull(data)) {
                Headers.setAthorization(data.getString("token_type") + " " + data.getString("access_token"));
            }
            Assert.assertEquals(object.getString("msg"), expectedMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
