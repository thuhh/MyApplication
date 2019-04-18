package com.example.admin.myapplication.controller.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MySingleton {
    public static MySingleton mInstance;
    private RequestQueue requestQueue;
    private static Context context;

    public MySingleton(Context con) {
        this.context = con;
        requestQueue = getRequestQueue();
    }

    public static synchronized MySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleton(context);
        }

        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T>void addTorequestque(Request<T> request){
        requestQueue.add(request);
    }

//    StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.url + "postdata.php", new Response.Listener<String>() {
//        @Override
//        public void onResponse(String response) {
//
//        }
//    }, new Response.ErrorListener() {
//        @Override
//        public void onErrorResponse(VolleyError error) {
//            error.printStackTrace();
//        }
//    }) {
//        @Override
//        protected Map<String, String> getParams() throws AuthFailureError {
//            Map<String, String> params = new HashMap<String, String>();
//            params.put("email", "bemboyvanhao@gmail.com");
//            params.put("name", "bemboyvanhao@gmail.com");
//            params.put("password", "bemboyvanhao@gmail.com");
//
//            return params;
//        }
//
//    };
//
//  MySingleton.getInstance(this).addTorequestque(stringRequest);

}
