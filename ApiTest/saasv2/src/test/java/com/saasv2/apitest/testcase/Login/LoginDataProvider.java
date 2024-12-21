package com.saasv2.apitest.testcase.Login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.saasv2.apitest.testcase.MyBaseTestCase;
import com.saasv2.apitest.utils.LoadProperties.LoadProperties;
import org.apache.http.client.methods.CloseableHttpResponse;

import org.testng.annotations.DataProvider;

import top.gcszhn.d4ocr.OCREngine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginDataProvider extends MyBaseTestCase {
    static{
        LoadProperties.loadProperties();
    }
    public static Map<String,Object> testGetCaptchaSuccess(){
        long currentTimeMillis = System.currentTimeMillis();
        Map<String,Object> randomStr = new HashMap<>();
        randomStr.put("randomStr",currentTimeMillis + "test");
        try (CloseableHttpResponse response = httpMethods.DoGetReturnResponse(LoadProperties.codeUrl, randomStr)) {
            BufferedImage image = ImageIO.read(response.getEntity().getContent());
            OCREngine engine = OCREngine.instance();
            String predict = engine.recognize(image);
            char[] chars = predict.toCharArray();
            int num1 = Character.getNumericValue(chars[0]);
            int num2 = Character.getNumericValue(chars[2]);
            String fuHao = String.valueOf(chars[1]);
            int code;
            code = switch (fuHao) {
                case "+", "4" -> num1 + num2;
                case "-" -> num1 - num2;
                case "x", "X" -> num1 * num2;
                default -> 0;
            };
            randomStr.put("code",code);

        }catch (Exception e){
            e.printStackTrace();

        }
        return randomStr;
    }

    public static ArrayList<JSONObject> readFileGetInfo(String filePath){
        ArrayList<JSONObject> info = new ArrayList<>();
        File userInfoFile = new File(filePath);
        try (FileInputStream in = new FileInputStream(userInfoFile)) {
            byte[] bytes = in.readAllBytes();
            String jsonString = new String(bytes, StandardCharsets.UTF_8);
            // 检查 JSON 文件内容是数组还是对象
            if (jsonString.trim().startsWith("[")) {
                JSONArray jsonArray = JSON.parseArray(jsonString);
                for (int i = 0; i < jsonArray.size(); i++) {
                    info.add(jsonArray.getJSONObject(i));
                }
            } else {
                JSONObject jsonObject = JSON.parseObject(jsonString);
                info.add(jsonObject);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return info;
    }

    @DataProvider(name="loginInfo")
    public static Object[][] combinedProviders(Method methodName){
        String fileName = switch (methodName.getName()) {
            case "testLoginSuccess" ->
                    LoadProperties.testLoginSuccess;
            case "testLoginFailure" ->
                    LoadProperties.testLoginFailure;
            case "testLoginErrorCode" ->
                    LoadProperties.testLoginErrorCode;
            default ->
                    LoadProperties.loginDataOnlyOnce;
        };
        ArrayList<Object[]> combinData = new ArrayList<>();
        ArrayList<JSONObject> fileGetInfo = readFileGetInfo(fileName);
        for (JSONObject jsonObject : fileGetInfo){
            combinData.add(new Object[]{jsonObject});
        }
        return combinData.toArray(new Object[0][]);
    }

}
