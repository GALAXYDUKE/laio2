package com.bofsoft.sdk.network;

import android.util.Log;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpTool {


    public static String doPost(String url, Map<String, String> params) {
        return doPost(url, params, HTTP.UTF_8, false);
    }

    /**
     * 通过POST发送请求
     *
     * @param url     请求的URL地址
     * @param params  请求的查询参数,可以为null
     * @param charset 字符集
     * @param pretty  是否美化
     * @return 返回请求响应的HTML
     */
    public static String doPost(String url, Map<String, String> paramMap, String charset,
                                boolean setProxy) {
        try {
            if (!url.startsWith("http"))
                url = "http://" + url;

            // 提交HTTP POST请求
            HttpPost httpPost = new HttpPost(url);
            if (paramMap != null) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();

                // 建立一个NameValuePair数组，用于存储欲传送的参数
                // 添加参数
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                // 设置HTTP POST请求参数
                httpPost.setEntity(new UrlEncodedFormEntity(params, charset));
            }
            HttpClient httpClient = new DefaultHttpClient();

            // 设置代理，可抓包测试，务必打开fiddler
            if (setProxy) {
                HttpHost proxy = new HttpHost("127.0.0.1", 8888);

                httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
            }

            HttpResponse httpResponse = httpClient.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 200 || statusCode == 201) {

                String result = EntityUtils.toString(httpResponse.getEntity(), charset);

                return result;
            } else {
                Log.e("", "请求返回异常！代码：" + httpResponse.getStatusLine().getStatusCode() + " url:"
                        + url);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("", "发送请求出现异常" + e.getMessage() + " url:" + url);
        }
        return null;
    }

    public static String doGet(String url, Map<String, String> params) {
        return doGet(url, params, HTTP.UTF_8, false);
    }

    public static String doGet(String url, Map<String, String> params, boolean setProxy) {
        return doGet(url, params, HTTP.UTF_8, setProxy);
    }

    public static String doGet(String url, Map<String, String> paramMap, String charset,
                               boolean setProxy) {
        try {

            if (!url.contains("?"))
                url += "?";
            if (!url.startsWith("http"))
                url = "http://" + url;
            if (paramMap != null) {
                // 添加参数

                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    url += entry.getKey() + "=" + entry.getValue() + "&";
                }
                url = url.substring(0, url.length() - 1);
            }
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            // 设置代理，可抓包测试，务必打开fiddler
            if (setProxy) {
                HttpHost proxy = new HttpHost("127.0.0.1", 8888);

                httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
            }

            HttpResponse httpResponse = httpClient.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 200 || statusCode == 201) {

                String result = EntityUtils.toString(httpResponse.getEntity(), charset);
                return result;
            } else {
                Log.e("", "请求返回异常！代码：" + httpResponse.getStatusLine().getStatusCode() + " url:"
                        + url);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("", "发送请求出现异常" + e.getMessage() + " url:" + url);
        }
        return null;
    }
}
