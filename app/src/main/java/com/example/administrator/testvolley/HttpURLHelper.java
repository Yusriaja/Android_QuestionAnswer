package com.example.administrator.testvolley;

import android.accounts.NetworkErrorException;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/4.
 */
public class HttpURLHelper {

    private static String cookieToken;

    public static boolean Loginin(String UserName,String Password)
    {
        try {
            String LoginUrl = "http://10.2.21.239/User/UserLogin";
            // post请求的参数
            String data = "LoginName="+UserName+"&LoginPwd=test&MD5LoginPwd="+getMD5(Password).toUpperCase();
            String postResult = "";
            HttpURLConnection HttpUrlConn = null;
            try {
                // 创建一个URL对象
                URL mURL = new URL(LoginUrl);
                // 调用URL的openConnection()方法,获取HttpURLConnection对象
                HttpUrlConn = (HttpURLConnection) mURL.openConnection();

                HttpUrlConn.setRequestMethod("POST");// 设置请求方法为post
                HttpUrlConn.setReadTimeout(50000);// 设置读取超时为50秒
                HttpUrlConn.setConnectTimeout(100000);// 设置连接网络超时为100秒
                HttpUrlConn.setDoOutput(true);// 设置此方法,允许向服务器输出内容

                HttpUrlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                // 获得一个输出流,向服务器写数据,默认情况下,系统不允许向服务器输出内容
                OutputStream out = HttpUrlConn.getOutputStream();// 获得一个输出流,向服务器写数据
                out.write(data.getBytes());
                out.flush();
                out.close();

                int responseCode = HttpUrlConn.getResponseCode();// 调用此方法就不必再使用conn.connect()方法
                if (responseCode == 200) {
                    InputStream is = HttpUrlConn.getInputStream();
                    String response = getStringFromInputStream(is);
                    Log.i("postResult:", response);

                    String cookieskey = "Set-Cookie";
                    Map<String, List<String>> maps = HttpUrlConn.getHeaderFields();
                    List<String> coolist = maps.get(cookieskey);

                    String strcookie= coolist.get(0);
                    String spStr[] = strcookie.split(";");
                    String StrCookie=spStr[0];
                    String CookieExpired=spStr[1];

                    cookieToken = StrCookie;
                    Log.i("cookie",StrCookie);
                    Log.i("CookieExpired",CookieExpired);
                    return  true;
                } else {
                    Log.i("Post response：", "response status is " + responseCode);
                    throw new NetworkErrorException("response status is " + responseCode);
                }

            } catch (Exception e) {
                throw e;
            } finally {
                if (HttpUrlConn != null) {
                    HttpUrlConn.disconnect();// 关闭连接
                }
            }
        }catch (Exception ex)
        {
            Log.i("异常：",ex.toString());
            return false;
        }
    }

    public static String post(String url, String content) {
        HttpURLConnection HttpUrlConn = null;
        try {
            // 创建一个URL对象
            URL mURL = new URL(url);
            // 调用URL的openConnection()方法,获取HttpURLConnection对象
            HttpUrlConn = (HttpURLConnection) mURL.openConnection();

            HttpUrlConn.setRequestMethod("POST");// 设置请求方法为post
            HttpUrlConn.setReadTimeout(5000);// 设置读取超时为5秒
            HttpUrlConn.setConnectTimeout(10000);// 设置连接网络超时为10秒
            HttpUrlConn.setDoOutput(true);// 设置此方法,允许向服务器输出内容

            // post请求的参数
            String data = content;
            // 获得一个输出流,向服务器写数据,默认情况下,系统不允许向服务器输出内容
            OutputStream out = HttpUrlConn.getOutputStream();// 获得一个输出流,向服务器写数据
            out.write(data.getBytes());
            out.flush();
            out.close();

            int responseCode = HttpUrlConn.getResponseCode();// 调用此方法就不必再使用conn.connect()方法
            if (responseCode == 200) {
                InputStream is = HttpUrlConn.getInputStream();
                String response = getStringFromInputStream(is);
                return response;
            } else {
                Log.i("Post response：", "response status is " + responseCode);
                throw new NetworkErrorException("response status is " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (HttpUrlConn != null) {
                HttpUrlConn.disconnect();// 关闭连接
            }
        }

        return null;
    }

    public static String get(String url) {
        HttpURLConnection conn = null;
        try {
            // 利用string url构建URL对象
            URL mURL = new URL(url);
            conn = (HttpURLConnection) mURL.openConnection();

            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(10000);

            conn.addRequestProperty("Cookie", cookieToken);

            int responseCode = conn.getResponseCode();
            Log.i("服务器返回代码", " ：" + responseCode);
            if (responseCode == 200) {

                InputStream is = conn.getInputStream();
                String response = getStringFromInputStream(is);
                Log.i("返回结果", response.toString());
                return response;
            } else {
                throw new NetworkErrorException("response status is " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (conn != null) {
                conn.disconnect();
            }
        }

        return null;
    }

    private static String getStringFromInputStream(InputStream is)
            throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // 模板代码 必须熟练
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        is.close();
        String state = os.toString();// 把流中的数据转换成字符串,采用的编码是utf-8(模拟器默认编码)
        os.close();
        return state;
    }

    public static String getMD5(String info)
    {
        try
        {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++)
            {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1)
                {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                }
                else
                {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }

            return strBuf.toString();
        }
        catch (Exception e)
        {
            return "";
        }
    }

}
