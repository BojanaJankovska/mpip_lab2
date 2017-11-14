package com.example.martin.lab2_omdb.volley;

/**
 * Created by drnch on 22.6.2017.
 */

public class APIResponse {

    public static final String OK = "0";
    public static final String ERROR = "1";
    public static final String UNAUTHENTICATED = "2";

    String status;

    String message;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
