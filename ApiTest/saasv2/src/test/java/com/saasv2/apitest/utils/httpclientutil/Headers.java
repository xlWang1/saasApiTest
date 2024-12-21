package com.saasv2.apitest.utils.httpclientutil;

import com.saasv2.apitest.utils.LoadProperties.LoadProperties;

import java.util.HashMap;
import java.util.Map;

public class Headers {
    public static String Athorization;
    public static String RequestId;
    public static String TimeStamp;
    public static HashMap<String, String> headers = new HashMap<>();
    public static Map<String, String> getHeaders(){
        headers.put("Accept", "application/json, text/plain, */*");
        headers.put("Accept-Encoding", "gzip, deflate");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
        headers.put("Authorization", Athorization);
        headers.put("Connection", "keep-alive");
        headers.put("Content-Type", "application/json");
        headers.put("HOST", LoadProperties.Host.split("://")[1]);
        headers.put("Origin", LoadProperties.Host);
        headers.put("Platform", "MISSION");
        headers.put("Referer", LoadProperties.Host + "/busi/");
        headers.put("Requestid", RequestId);
        headers.put("Timestamp", TimeStamp);
        headers.put("User-Agent",  "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");
        return headers;
    }


    public static void setAthorization(String athorization) {
        Athorization = athorization;
    }

    public static void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public static void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }
}
