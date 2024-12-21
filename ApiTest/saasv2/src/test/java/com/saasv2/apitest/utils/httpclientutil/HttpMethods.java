package com.saasv2.apitest.utils.httpclientutil;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Objects;
public class HttpMethods {
    public static BasicCookieStore cookieStore = new BasicCookieStore();
    public static CloseableHttpClient client;
    public HttpMethods() {
        try {
            client = HttpClients
                    .custom()
                    .setDefaultCookieStore(cookieStore)
                    .setSSLSocketFactory(getSslConnectionSocketFactory())
                    .setProxy(new HttpHost("127.0.0.1", 8888))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void closeClient(){
        if (!Objects.isNull(client)){
            try {
                client.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public CloseableHttpResponse DoGetReturnResponse(String uri, Map<String,Object> params){
        CloseableHttpResponse response = null;
        try{
            URIBuilder builder = new URIBuilder(uri);
            for (Map.Entry<String,Object> para :params.entrySet()){
                builder.addParameter(para.getKey(), (String) para.getValue());
            }
            HttpGet doGet = new HttpGet(builder.build());
            Map<String, String> headers = Headers.getHeaders();
            for (Map.Entry<String,String> header: headers.entrySet()){
                doGet.setHeader(header.getKey(), header.getValue());
            }
            response = client.execute(doGet);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
    public CloseableHttpResponse DoPostReturnResponse(String uri,Map<String,Object> params){
        CloseableHttpResponse response;
        try {
            HttpPost doPost = new HttpPost(uri);
            for (Map.Entry<String,String> entry : Headers.getHeaders().entrySet()){
                doPost.setHeader(entry.getKey(),entry.getValue());
            }
            StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(params), "utf-8");
            doPost.setEntity(stringEntity);
            response = client.execute(doPost);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    private static SSLConnectionSocketFactory getSslConnectionSocketFactory() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (x509Certificates, s) -> true;
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        return new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
    }
}
