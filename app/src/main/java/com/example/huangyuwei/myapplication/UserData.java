package com.example.huangyuwei.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user-pc on 2017/10/28.
 */

public class UserData {
    private static final String data = "DATA";
    private static final String account_data = "ACCOUNT";
    private static final String password_data = "PASSWORD";

    public static SharedPreferences getSharedPreferences(Context context){
        SharedPreferences settings = context.getSharedPreferences(data, 0);
        return settings;
    }

    public static boolean readData(Context context){
        SharedPreferences settings = getSharedPreferences(context);
        if(!settings.getString(account_data, "").equals("")){
            return true;
        }
        return false;
    }

    public static boolean saveData(Context context, String email, String password){
        SharedPreferences settings = getSharedPreferences(context);
        settings.edit()
                .putString(account_data, email)
                .putString(password_data, password)
                .apply();
        return true;

    }
}
