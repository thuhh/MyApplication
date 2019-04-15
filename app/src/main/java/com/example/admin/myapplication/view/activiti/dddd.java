package com.example.admin.myapplication.view.activiti;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.controller.util.MySingleton;

import java.util.HashMap;
import java.util.Map;

public class dddd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dddd);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,  "http://192.168.1.171/sphone/postdata.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", "bemboyvanhao@gmail.com");
                params.put("name", "bemboyvanhao@gmail.com");
                params.put("password", "bemboyvanhao@gmail.com");

                return params;
            }

        };

        MySingleton.getInstance(this).addTorequestque(stringRequest);
    }
}
