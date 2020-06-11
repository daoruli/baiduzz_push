package com.edison.baidupush;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * @author: 陈俊锋
 * @Date: 2020年4月24日17:22:16
 */
public class BaiDuPush {
    /**
     * 站长推送链接
     * @param site 站点全地址
     * @param token 百度推送token
     */
    public BaiDuPush(String site, String token) {
        StringBuilder sb = new StringBuilder("http://data.zz.baidu.com/urls?site=");
        sb.append(site);
        sb.append("&token=");
        sb.append(token);
        this.zzApiUrl =  sb.toString();
    }

    /**
     * 从百度站长平台获取
     */
    private String zzApiUrl;
    /**
     * 返回状态码
     */
    private final static int SUCCESS_200 = 200;
    private final static int ERROR_400 = 400;
    private final static int ERROR_401 = 401;
    private final static int ERROR_404 = 404;
    private final static int ERROR_500 = 500;


    public String postUrl(List<String> urls) throws IOException {
        URLConnection urlConnection;
        StringBuilder result = new StringBuilder();
        PrintWriter postPrintWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        int statusCode;
        try {
            urlConnection = new URL(zzApiUrl).openConnection();
            urlConnection.setRequestProperty("Content-Type", "text/plain");
            urlConnection.setRequestProperty("User-Agent", "curl/7.12.1");
            urlConnection.setRequestProperty("Host", "data.zz.baidu.com");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            HttpURLConnection httpUrlConnection = (HttpURLConnection) urlConnection;
            postPrintWriter = new PrintWriter(httpUrlConnection.getOutputStream());
            //构造请求参数
            StringBuffer param = createParam(urls);
            //发送参数
            postPrintWriter.print(param);
            //刷新输出流缓冲
            postPrintWriter.flush();
            statusCode = httpUrlConnection.getResponseCode();
            inputStream = httpUrlConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder resultT = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                resultT.append(line);
            }
            System.out.println("发送请求结果=>" + resultT);
            //构造判断结果
            parseResult(result, statusCode);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭流
            if (postPrintWriter != null) {
                postPrintWriter.close();
                postPrintWriter = null;
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
                inputStreamReader = null;
            }
            if (inputStream != null) {
                inputStream.close();
                inputStream = null;
            }
        }
        return result.toString();
    }

    private StringBuffer createParam(List<String> urls) {
        StringBuffer param = new StringBuffer();
        for (String s : urls) {
            if (s != null) {
                //去除两头空格
                String tmpUrl = s.trim();
                if (tmpUrl.contains("http://") || tmpUrl.contains("https://")) {
                    param.append(tmpUrl);
                    param.append("\n");
                }
            }
        }
        System.out.println("请求参数=>" + param);
        return param;
    }

    private void parseResult(StringBuilder result, int statusCode) {
        switch (statusCode) {
            case SUCCESS_200:
                result.append("提交成功");
                break;
            case ERROR_400:
                result.append("站点未在站长平台验证");
                break;
            case ERROR_401:
                result.append("接口调用地址 错误");
                break;
            case ERROR_404:
                result.append("接口地址填写错误");
                break;
            case ERROR_500:
                result.append("服务器偶然异常，通常重试就会成功");
                break;
            default:
                result.append("未知错误");
                break;
        }
    }
}
