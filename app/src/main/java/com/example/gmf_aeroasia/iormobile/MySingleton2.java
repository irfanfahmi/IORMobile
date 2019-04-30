package com.example.gmf_aeroasia.iormobile;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class MySingleton2 {
    private static MySingleton2 mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private MySingleton2(Context context){
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue()
    {
        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingleton2 getmInstance(Context context){
        if(mInstance==null){
            mInstance = new MySingleton2(context);
        }
        return mInstance;
    }
    public<T> void addToRequestque(Request<T> request){
        requestQueue.add(request);
    }
}
