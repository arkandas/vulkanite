package com.arkandas.vulkanite.data.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

public class UserPreferencesService {

    private static final String TAG = "UserPreferencesService";

    public static String GetToken(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
        Log.d(TAG, "Retrieving token");
        return sharedPreferences.getString("token", "error");
    }

    public static String GetWallet(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
        Log.d(TAG, "Retrieving user wallet");
        return sharedPreferences.getString("wallet", "error");
    }

    public static String GetBalance(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
        Log.d(TAG, "Retrieving user balance");
        return sharedPreferences.getString("balance", "error");
    }

    public static void hideKeyboard(@NonNull Activity activity) {
        // Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
