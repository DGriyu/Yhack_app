package com.example.riyu.yhackproject;

import android.content.Context;

import com.loopj.android.http.*;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

/**
 * Created by Sasawat on 11/02/2014.
 */
public class WebClient {
    private static AsyncHttpClient client = new AsyncHttpClient(8080);

    static AsyncHttpResponseHandler rhnull = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            //fuck it ship it
            return;
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            //fuck it ship it
            return;
        }
    };

    public static void transmitUserMACPair(Context context, String user, long MAC)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user", user);
            jsonObject.put("MAC", MAC);
        }catch (Exception ex)
        {
            //yololololol
        }

        StringEntity entity;
        try {
            entity = new StringEntity(jsonObject.toString());
        }catch (Exception ex)
        {
            //yolo
            return;
        }

        client.post(context, "http://104.236.63.179:8080/api/galileos", entity, "application/json", rhnull);

    }

}
