package com.udinic.accounts_auth_example.authentication;

import android.util.Log;
import com.udinic.accounts_auth_example.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Udini
 * Date: 21/03/13
 * Time: 15:10
 */
public class ServerUtils {

    public static String connect(final String user, final String pass, String authType, String accountType) {

        Log.d("udini", "ServerUtils > connect");

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String authtoken = null;

        BasicCookieStore cm = new BasicCookieStore();
        httpClient.setCookieStore(cm);

        String url = null;

        if (Consts.ACCOUNT_TYPE1.equals(accountType))
            url = "http://sm-dev.any.do/j_spring_security_check";
        else if (Consts.ACCOUNT_TYPE2.equals(accountType))
            url = "http://sm-dev.any.do/j_spring_security_check";


        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("j_username", user));
        nameValuePairs.add(new BasicNameValuePair("j_password", pass));
        nameValuePairs.add(new BasicNameValuePair("_spring_security_remember_me", "on"));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            HttpResponse response = httpClient.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());

            List<Cookie> cookies = cm.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("SPRING_SECURITY_REMEMBER_ME_COOKIE")) {
                    authtoken = cookie.getValue();
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return authtoken;
    }

}
