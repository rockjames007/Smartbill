package com.example.dipuj.smartbill.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class Pref
{

    public Pref()
    {
        //empty constructor.
    }

    /**
     * Open preference.
     * @param context context
     * @param mPrefName preference name
     * @return SharedPreferences
     */
    public static SharedPreferences openPref_(Context context, String mPrefName)
    {

        return  context.getSharedPreferences(mPrefName,
                Context.MODE_PRIVATE);

    }

    /**
     * This method get value from preference for String type.
     * @param context context
     * @param key key
     * @param defaultValue defaultValue
     * @param mFileName mFileName
     * @return string value
     */
    public static String getValue(Context context, String key,
                                  String defaultValue,String mFileName)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mFileName,
                Context.MODE_PRIVATE);

        return sharedPreferences.getString(key, defaultValue);
    }

    /**
     * This method set value in preference for String type.
     * @param context context
     * @param key key
     * @param value value
     * @param mFileName mFileName
     */
    public static void setValue(Context context, String key, String value,String mFileName)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mFileName,
                context.MODE_PRIVATE);
        SharedPreferences.Editor prefsPrivateEditor = sharedPreferences.edit();
        prefsPrivateEditor.putString(key, value);
        prefsPrivateEditor.apply();

    }

    /**
     * This method get value from preference for boolean type.
     * @param context context
     * @param key key
     * @param defaultValue defaultValue
     * @param mFileName mFileName
     * @return boolean
     */
    public static boolean getValue(Context context, String key,
                                   boolean defaultValue,String mFileName)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mFileName,
                Context.MODE_PRIVATE);

        return sharedPreferences.getBoolean(key, defaultValue);
    }

    /**
     * This method set value in preference for boolean type.
     * @param context context
     * @param key key
     * @param value value
     * @param mFileName mFileName
     */
    public static void setValue(Context context, String key, boolean value,String mFileName)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mFileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsPrivateEditor = sharedPreferences.edit();
        prefsPrivateEditor.putBoolean(key, value);
        prefsPrivateEditor.apply();

    }


    /**
     * This method check provided key available or not in preference.
     * @param _Context _Context
     * @param mKeyName mKeyName
     * @param mFileName mFileName
     * @return boolean value
     */
    static boolean hasKey(Context _Context,String mKeyName,String mFileName)
    {
        SharedPreferences sharedPreferences = _Context.getSharedPreferences(mFileName,
                Context.MODE_PRIVATE);

        //sharedPreferences=null;

        return sharedPreferences.contains(mKeyName);
    }


    /**
     * This method set value in preference for int type.
     * @param context context
     * @param key key
     * @param value value
     * @param mFileName mFileName
     */
    public static void setValue(Context context, String key, int value,String mFileName)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mFileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsPrivateEditor = sharedPreferences.edit();
        prefsPrivateEditor.putInt(key,value);
        prefsPrivateEditor.apply();

    }

    /**
     * This method set value in preference for long type.
     * @param context context
     * @param key key
     * @param value value
     * @param mFileName mFileName
     */
    public static void setValueLong(Context context, String key, long value,String mFileName)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mFileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsPrivateEditor = sharedPreferences.edit();
        prefsPrivateEditor.putLong(key, value);
        prefsPrivateEditor.apply();

    }

    /**
     * This method get value from preference for long type.
     * @param context context
     * @param key key
     * @param mFileName mFileName
     * @return long value
     */
    public static long getValueLong(Context context, String key,String mFileName)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mFileName,
                Context.MODE_PRIVATE);

        return sharedPreferences.getLong(key,1L);
    }


    /**
     * This method get value from preference for int type.
     * @param context context
     * @param key key
     * @param defaultValue defaultValue
     * @param mFileName mFileName
     * @return int value
     */
    public static int getValue(Context context, String key,
                               int defaultValue,String mFileName)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mFileName,
                Context.MODE_PRIVATE);

        return sharedPreferences.getInt(key,defaultValue);
    }

}
