package com.k2infosoft.proprofile.managers;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class SharedPrefs {

    //SharedPreferences file name
    private static String SHARED_PREFS_FILE_NAME = "MyData";

    public static String SocialType = "socialtypeKey";
    public static String FacebookId = "facebookKey";
    public static String GooglePlusId = "googleplusKey";
    public static String LinkedinId = "linkedinKey";
    public static String TwitterId = "twitterKey";
    public static String InstagramId = "twitterKey";
    public static String PinterestId = "pinterestKey";

    public static String DeviceId = "deviceKey";
    //here you can centralize all your shared prefs keys
    public static String Userid = "idKey";
    public static String Username = "nameKey";
    public static final String mobile = "phoneKey";
    public static final String Email = "emailKey";
    public static final String Designation = "designKey";
    public static final String about = "aboutKey";
    public static final String Address = "addressKey";
    public static final String Website = "websiteKey";
    public static final String Profilepic = "profileKey";
    public static final String Dob = "dobKey";
    public static final String language = "profileKey";
    public static final String Gender = "genderKey";
    public static final String Loggedinuserid = "useraccountKey";

    public static final String Profileset = "profilesetKey";


    public static final String LoginUserProfileid = "profilekey";


    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE);
    }

    //Save Booleans
    public static void savePref(Context context, String key, boolean value) {
        getPrefs(context).edit().putBoolean(key, value).commit();
    }

    //Get Booleans
    public static boolean getBoolean(Context context, String key) {
        return getPrefs(context).getBoolean(key, false);
    }

    //Get Booleans if not found return a predefined default value
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return getPrefs(context).getBoolean(key, defaultValue);
    }

    //Strings
    public static void save(Context context, String key, String value) {
        getPrefs(context).edit().putString(key, value).commit();
    }


    public static String getString(Context context, String key) {
        return getPrefs(context).getString(key, "");
    }

    public static String getString(Context context, String key, String defaultValue) {
        return getPrefs(context).getString(key, defaultValue);
    }

    //Integers
    public static void save(Context context, String key, int value) {
        getPrefs(context).edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, String key) {
        return getPrefs(context).getInt(key, 0);
    }

    public static int getInt(Context context, String key, int defaultValue) {
        return getPrefs(context).getInt(key, defaultValue);
    }

    //Floats
    public static void save(Context context, String key, float value) {
        getPrefs(context).edit().putFloat(key, value).commit();
    }

    public static float getFloat(Context context, String key) {
        return getPrefs(context).getFloat(key, 0);
    }

    public static float getFloat(Context context, String key, float defaultValue) {
        return getPrefs(context).getFloat(key, defaultValue);
    }

    //Longs
    public static void save(Context context, String key, long value) {
        getPrefs(context).edit().putLong(key, value).commit();
    }

    public static long getLong(Context context, String key) {
        return getPrefs(context).getLong(key, 0);
    }

    public static long getLong(Context context, String key, long defaultValue) {
        return getPrefs(context).getLong(key, defaultValue);
    }

    //StringSets
    public static void save(Context context, String key, Set<String> value) {
        getPrefs(context).edit().putStringSet(key, value).commit();
    }

    public static Set<String> getStringSet(Context context, String key) {
        return getPrefs(context).getStringSet(key, null);
    }

    public static Set<String> getStringSet(Context context, String key, Set<String> defaultValue) {
        return getPrefs(context).getStringSet(key, defaultValue);
    }

    public static void remove(Context context, String key) {
        getPrefs(context).edit().remove(key).commit();
    }

}