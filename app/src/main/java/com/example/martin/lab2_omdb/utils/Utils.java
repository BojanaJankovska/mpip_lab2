package com.example.martin.lab2_omdb.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.gson.Gson;


/**
 * Created by Tomislav on 3/14/2016.
 */
public class Utils {

    public static Gson gson = new Gson();

    /**
     * Log an object casted into String. If not possible, the reference of the
     * instance will be logged.
     *
     * @param o text to be loged
     */
    public static void doLog(Object o) {
        try {
            Log.d(C.TAG, o.toString());
        } catch (Exception e) {
            doLogException(e);
        }
    }

    /**
     * Log an exception stack.
     *
     * @param e the Exception object to be loged
     */
    public static void doLogException(Exception e) {
        Log.e(C.TAG, "Exception", e);
    }

    /**
     * Show long Toast.
     *
     * @param context the context object
     * @param text    the text to be shown in the Toast
     */
    public static void doToast(Context context, String text) {
        if (context != null && !TextUtils.isEmpty(text)) {
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Show long Toast.
     *
     * @param context     the context object
     * @param stringResId the string resource ID to be shown in the Toast
     */
    public static void doToast(Context context, int stringResId) {
        if (context != null) {
            Toast.makeText(context, stringResId, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Check if it has an active connection.
     *
     * @param context some Context.
     * @return does it have an active Network connection.
     */
    public static boolean hasActiveNetworkConnection(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        boolean activeNetworkConnection = ((networkInfo != null) && networkInfo.isConnected());
        if (!activeNetworkConnection) {
            Utils.doToast(context, "No network connection");
        }
        return activeNetworkConnection;
    }

    /**
     * Returns network coonnection type
     */
    public static String getNetworkStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return activeNetwork.getTypeName();
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return activeNetwork.getTypeName();
            }
        } else {
            return "No connection";
        }
        return "Unidentified";
    }

    /**
     * Hiding/closing the keyboard
     */

    public static void hideKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager inputManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            if (inputManager != null) {
                View v = activity.getCurrentFocus();
                if (v != null) {
                    inputManager.hideSoftInputFromWindow(v.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
    }

    /**
     * Requests focus to the view and shows keyboard
     */
    public static void showKeyboard(Context context, View toFocus) {
        toFocus.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.showSoftInput(toFocus, InputMethodManager.SHOW_IMPLICIT);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * Returns the device ID
     */
    public static String getDeviceId(Context context) {
        return Secure.getString(context.getContentResolver(),
                Secure.ANDROID_ID);
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }


    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;

        try {
            locationMode = Secure.getInt(context.getContentResolver(), Secure.LOCATION_MODE);

        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return locationMode != Secure.LOCATION_MODE_OFF;
    }

}