package io.trollingninjas.status200_android_client.Model;


import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ApiEndpoints {

    private Context context ;
    private RequestQueue rq ;

    public ApiEndpoints(Context context) {
        this.context = context;
        rq = Volley.newRequestQueue(context) ;

    }

    public void chatMessage(String query){

        String url = "";

        try {
            url = "http://139.59.21.68:8000/chat?message=" + URLEncoder.encode(query, "UTF-8") ;
            Log.i("TAG", "chatMessage: " + url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Log.i("TAG", "onResponse: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response) ;

                            String responseMessage = jsonObject.getString("message") ;
                            EventBus.getDefault().postSticky(new ChatsPOJO(responseMessage, Constants.LIST_TYPE_RESPONSE));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            EventBus.getDefault().postSticky(new VolleyErrorEvent(e.getMessage()));
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                EventBus.getDefault().postSticky(new VolleyErrorEvent(error.getMessage()));
            }
        });


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)) ;
        rq.add(stringRequest) ;

    }
}
