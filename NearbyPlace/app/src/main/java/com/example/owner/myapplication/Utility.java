package com.example.owner.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.Toast;


public class Utility {

    public static final String BASE_URL = "https://maps.googleapis.com";
    private static final String PLACE_PHOTO_BASE_URL = "https://maps.googleapis.com/maps/api/place/photo";
    public final static String SUCCESS = "SUCCESS";
    public final static String LOADING = "loading";
    public final static String ERROR = "error";
    public final static String OK = "OK";

    public static ProgressDialog getProgressDialog(Context context, String msg) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    public static void makeToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null){
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    private static final String PHOTO_REFERENCES = "photoreference";
    private static final String SENSOR = "sensor";
    private static final String MAX_HEIGHT = "maxheight";
    private static final String MAX_WIDTH = "maxwidth";
    private static final String KEY = "key";

    public static String buildPlacePhotoUrl(String imagePath) {
        Uri builtUri = Uri.parse(PLACE_PHOTO_BASE_URL).buildUpon()
                .appendQueryParameter(PHOTO_REFERENCES, imagePath)
                .appendQueryParameter(SENSOR, "false")
                .appendQueryParameter(MAX_HEIGHT, "1200")
                .appendQueryParameter(MAX_WIDTH, "1200")
                .appendQueryParameter(KEY, BuildConfig.PLACE_API_KEY)
                .build();
        return builtUri.toString();
    }

    public static final String RADIUS = "1500";
}
