package io.trollingninjas.status200_android_client.Model;


import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import io.trollingninjas.status200_android_client.AppController;

public class ApiEndpoints {

    private void chatMessage(String query){

        String url = "";

        try {
            url = "http://139.59.78.99:8000/chat?" + URLEncoder.encode(query, "UTF-8") ;
            Log.i("TAG", "chatMessage: " + url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(stringRequest);

    }
}
