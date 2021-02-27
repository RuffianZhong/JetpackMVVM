package com.ruffian.android.library.common.utils;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SpManager
 *
 * @author ZhongDaFeng
 */
public class SpManager {

    /*默认*/
    public static final String sp_key_default = "sp_key_default";
    /*设置类*/
    public static final String sp_key_setting = "sp_key_setting";
    /*账户*/
    public static final String sp_key_account = "sp_key_account";
    /*...*/

    private static volatile SpUtils mSpUtils;
    private static volatile SpManager instance;

    public static SpManager get(Context context) {
        mSpUtils = SpUtils.getInstance(context, sp_key_default);
        return getInstance();
    }

    public static SpManager getAccount(Context context) {
        mSpUtils = SpUtils.getInstance(context, sp_key_account);
        return getInstance();
    }

    public static SpManager getSetting(Context context) {
        mSpUtils = SpUtils.getInstance(context, sp_key_setting);
        return getInstance();
    }

    private SpManager() {
    }

    private static SpManager getInstance() {
        if (instance == null) {
            synchronized (SpManager.class) {
                if (instance == null) {
                    instance = new SpManager();
                }
            }
        }
        return instance;
    }

    /*string*/
    public void put(@NonNull final String key, final String value) {
        mSpUtils.put(key, value);
    }

    public String getString(@NonNull final String key) {
        return mSpUtils.getString(key);
    }

    public String getString(@NonNull final String key, final String defaultValue) {
        return mSpUtils.getString(key, defaultValue);
    }

    /*int*/
    public void put(@NonNull final String key, final int value) {
        mSpUtils.put(key, value);
    }

    public int getInt(@NonNull final String key) {
        return mSpUtils.getInt(key);
    }

    public int getInt(@NonNull final String key, final int defaultValue) {
        return mSpUtils.getInt(key, defaultValue);
    }

    /*long*/
    public void put(@NonNull final String key, final long value) {
        mSpUtils.put(key, value);
    }

    public long getLong(@NonNull final String key) {
        return mSpUtils.getLong(key);
    }

    public long getLong(@NonNull final String key, final long defaultValue) {
        return mSpUtils.getLong(key, defaultValue);
    }

    /*float*/
    public void put(@NonNull final String key, final float value) {
        mSpUtils.put(key, value);
    }

    public float getFloat(@NonNull final String key) {
        return mSpUtils.getFloat(key);
    }

    public float getFloat(@NonNull final String key, final float defaultValue) {
        return mSpUtils.getFloat(key, defaultValue);
    }

    /*boolean*/
    public void put(@NonNull final String key, final boolean value) {
        mSpUtils.put(key, value);
    }

    public boolean getBoolean(@NonNull final String key) {
        return mSpUtils.getBoolean(key);
    }

    public boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
        return mSpUtils.getBoolean(key, defaultValue);
    }

    /*map*/
    public void put(@NonNull final Map<String, Object> map) {
        mSpUtils.put(map);
    }

    public Map<String, ?> getAll() {
        return mSpUtils.getAll();
    }

    /*Set<String>*/
    public void put(@NonNull final String key, final Set<String> value) {
        mSpUtils.put(key, value);
    }

    public Set<String> getStringSet(@NonNull final String key) {
        return mSpUtils.getStringSet(key);
    }

    public Set<String> getStringSet(@NonNull final String key, final Set<String> defaultValue) {
        return mSpUtils.getStringSet(key, defaultValue);
    }

    /*list*/
    public <T> void put(String key, List<T> list) {
        mSpUtils.put(key, list);
    }

    public <T> ArrayList<T> getList(String key, Class<T> cls) {
        return mSpUtils.getList(key, cls);
    }

    /*Object*/
    public <T> void put(String key, Object object) {
        mSpUtils.put(key, object);
    }

    public <T> T getObject(String key, Class<T> cls) {
        return mSpUtils.getObject(key, cls);
    }

    public boolean contains(@NonNull final String key) {
        return mSpUtils.contains(key);
    }

    public void remove(@NonNull final String key) {
        mSpUtils.remove(key);
    }

    public void clear() {
        mSpUtils.clear();
    }

}