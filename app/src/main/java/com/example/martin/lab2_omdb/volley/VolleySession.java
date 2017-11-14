package com.example.martin.lab2_omdb.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Tomislav on 12/21/2015.
 */
public class VolleySession {
    private static VolleySession mIstance;
    private RequestQueue mQueue;

    protected VolleySession() {

    }

    public static synchronized VolleySession instance() {
        if(mIstance == null) {
            mIstance = new VolleySession();
        }
        return mIstance;
    }

    public void initialize(Context context) {
        mQueue = Volley.newRequestQueue(context);
    }

    /**
     * Add Request to the request queue.
     * @param request the request to be added.
     */
    public void addRequest(Request<?> request) {
        if(mQueue != null) {
            mQueue.add(request);
        }
    }

    /**
     * Get the active Volley request queue.
     * @return the Volley request queue.
     */
    public RequestQueue getQueue() {
        return mQueue;
    }
}
