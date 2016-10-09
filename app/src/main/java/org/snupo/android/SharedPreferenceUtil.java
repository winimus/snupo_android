package org.snupo.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by minim on 2016-09-13.
 */
public class SharedPreferenceUtil
{
    public static void putSharedBoolean(Context context, String key, boolean value)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        //Log.i("Boolean Saved", String.valueOf(value));
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static Boolean getSharedBoolean(Context context, String key)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(key, false);
    }


    public static void putSharedString(Context context, String key, String value)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
       //Log.i("String Saved", value);
        editor.putString(key, value);
        editor.commit();
    }

    public static String getSharedString(Context context, String key)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(key, "");
    }

}