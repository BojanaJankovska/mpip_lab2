package com.example.martin.lab2_omdb.volley;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;


public class APIJSONRequest<T> extends APIRequest<T> {

    private String mJSONBody;

    public APIJSONRequest(Context context, String jsonString, int method, String url, Type c, Listener<T> listener, ErrorListener errorListener) {
        super(context, method, url, c, listener, errorListener, null, null);
        mJSONBody = jsonString;
    }



    @Override
    public String getBodyContentType() {
        String paramsEncoding = getParamsEncoding();

        return "application/json;" + paramsEncoding;
        //return "text/json;" + paramsEncoding;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            return mJSONBody.getBytes(getParamsEncoding());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return super.getHeaders();
    }
}
