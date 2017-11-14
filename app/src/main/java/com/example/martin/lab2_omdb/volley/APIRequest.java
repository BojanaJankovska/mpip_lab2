package com.example.martin.lab2_omdb.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.martin.lab2_omdb.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;




class APIRequest<T> extends Request<T> {

    Context context;

    private Type mClass;//TODO change name because it is not a class anymore
    private Listener<T> mListener;
    private ErrorListener mErrorListener;

    private Map<String, String> mHeaders = new HashMap<String, String>();
    private Map<String, String> mParams = new HashMap<String, String>();

    String mBody;

    private APIRequest(Context context, int method, String url, Type c, Listener<T> listener,
                       ErrorListener errListener) {
        super(method, url, errListener);
        this.context = context;
        mClass = c;
        mListener = listener;
        mErrorListener = errListener;

        setRetryPolicy(new DefaultRetryPolicy(3000, 2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        setShouldCache(false);
    }

    APIRequest(Context context, int method, String url, Type c, Listener<T> listener,
               ErrorListener errorListener, Map<String, String> headers,
               Map<String, String> params) {
        this(context, method, url, c, listener, errorListener);

        if (headers != null)
            mHeaders = headers;
        if (params != null)
            mParams = params;
    }


    @Override
    protected void deliverResponse(T response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {

            String content = new String(response.data, "UTF-8");
            T result = null;

            if (content.trim().isEmpty()) {
                return Response.error(new VolleyError("Error: Server response empty"));
            }

            //content = "";
            Log.d("Content", content);

            try {
                result = deserialize(content, mClass);
               /*
                if (checkAuthorization(result)) {
                    return null;
                }
                */
                return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));

            } catch (Exception e) {//TODO should this be something more specific?
                e.printStackTrace();
                return Response.error(new VolleyError(e));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new VolleyError(e));
        } finally {

        }
    }

    private boolean checkAuthorization(T result) {
        APIResponse apiResponse = (APIResponse) result;
        if (apiResponse.getStatus().equals(APIResponse.UNAUTHENTICATED)) {
            //((BaseActivity)context).startActivityForResult();
        }
        return false;
    }

    /* @Override
     public Map<String, String> getHeaders() throws AuthFailureError {

         User user = PrefsManager.getUser(context);
         if(user != null){
             mHeaders.put("Accept", "application/json");
             mHeaders.put("Authorization", "Bearer "+ user.getToken());
             Utils.doLog("APIRequest with auth token: "+user.getToken());
         }

         return mHeaders;
     }
 */
    @Override
    protected Map<String, String> getParams() {
        return mParams;
    }

    public void setParameters(Map<String, String> params) {
        mParams = params;
    }

    private T deserialize(String content, Type c) {

        return Utils.gson.fromJson(content, c);
    }
}