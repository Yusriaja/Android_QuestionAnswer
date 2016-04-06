//package com.example.administrator.testvolley;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.View;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by Administrator on 2016/2/4.
// */
//public class VolleyHelper {
//
//    public static  void getUrl(Context v)
//    {
//        RequestQueue requestQueue = Volley.newRequestQueue(v);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://10.2.21.239/api/usergen",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("response", response);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("error", error.getMessage(), error);
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> Params = new HashMap<String, String>();
//                Params.put("Keyword", "d155f081-2772-4218-b9a8-857630e2ee95");
//                Log.i("i", "Keyword=d155f081-2772-4218-b9a8-857630e2ee95");
//                return Params;
//            }
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<String, String>();
//                headers.put("Cookie", "Token=0B18208D-D207-4703-8FF2-5093D1C479B5");
//                Log.i("i", "Token=88E8D3D8-21C5-49DD-AFA3-8B1AE8040CC4");
//                return headers;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }
//}
