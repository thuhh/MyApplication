package com.example.admin.myapplication.controller.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.jetbrains.annotations.Nullable;

public class MyPreferenceHelper {
    private static final String NEWS_ADS_PREFERENCES = "eyeProtector";
    public static String remember = "remember";
    public static String userName = "userName";
    public static String password = "password";
    public static String keyNext = "keyNext";
    public static String SELECT_POSITION = "SELECT_POSITION";
    public static String SELECT_IMAGE = "SELECT_IMAGE";
    public static String DialogFood = "DialogFood";
    public static String idUser = "idUser";
    public static String firstData = "firstData";
    public static String firstMaterial = "firstMaterial";
    public static String firstTable = "firstTable";
    public static String checkEdit = "checkEdit";
    public static String clickItem = "clickItem";


    public static int getInt(String key, Context context) {
        return getIntValue(key, context);
    }

    public static void setInt(String key, int value, Context context) {
        putIntValue(key, value, context);
    }

    public static float getFloat(String key, Context context) {
        return getFloatValue(key, context);
    }

    public static void setFloat(String key, float value, Context context) {
        putFloatValue(key, value, context);
    }


    public static String getString(String key, Context context) {
        return getStringValue(key, context);
    }

    public static void setString(Context context, String key, String value) {
        putStringValue(key, value, context);
    }


    public static void putStringValue(String key, String s, Context context) {
        SharedPreferences pref = context.getSharedPreferences(
                NEWS_ADS_PREFERENCES, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, s);
        editor.apply();
    }

    public static void putBoolean(String key, boolean value, Context context) {
        SharedPreferences pref = context.getSharedPreferences(
                NEWS_ADS_PREFERENCES, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void putFloatValue(String key, Float s, Context context) {
        SharedPreferences pref = context.getSharedPreferences(
                NEWS_ADS_PREFERENCES, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(key, s);
        editor.apply();
    }

    public static void putLongValue(String key, long l, Context context) {
        SharedPreferences pref = context.getSharedPreferences(
                NEWS_ADS_PREFERENCES, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(key, l);
        editor.apply();
    }

    public static Long getLongValue(String key, Context context) {
        SharedPreferences pref = context.getSharedPreferences(
                NEWS_ADS_PREFERENCES, 0);
        return pref.getLong(key, 0);
    }

    public static void putIntValue(String key, int value, Context context) {
        SharedPreferences pref = context.getSharedPreferences(
                NEWS_ADS_PREFERENCES, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void putBooleanValue(String key, Boolean s, Context context) {
        SharedPreferences pref = context.getSharedPreferences(
                NEWS_ADS_PREFERENCES, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, s);
        editor.apply();
    }

    public static boolean getBooleanValue(String key, Context context) {
        SharedPreferences pref = context.getSharedPreferences(
                NEWS_ADS_PREFERENCES, 0);
        return pref.getBoolean(key, false);
    }


    public static String getStringValue(String key, Context context) {
        SharedPreferences pref = context.getSharedPreferences(
                NEWS_ADS_PREFERENCES, 0);
        return pref.getString(key, null);
    }

    public static Float getFloatValue(String key, Context context) {
        SharedPreferences pref = context.getSharedPreferences(
                NEWS_ADS_PREFERENCES, 0);
        return pref.getFloat(key, (float) 0);
    }

    public static int getIntValue(String key, Context context) {
        SharedPreferences pref = context.getSharedPreferences(
                NEWS_ADS_PREFERENCES, 0);
        return pref.getInt(key, -1);
    }
}
