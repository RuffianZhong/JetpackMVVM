package com.ruffian.android.mvvm.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SpUtils
 *
 * @author ZhongDaFeng
 */
public class SpUtils {

    private static final Map<String, SpUtils> SP_UTILS_MAP = new HashMap<>();
    private SharedPreferences sp;

    public static SpUtils getInstance(Context context, String spName) {
        return getInstance(context, spName, Context.MODE_PRIVATE);
    }

    public static SpUtils getInstance(Context context, String spName, final int mode) {
        if (context == null) {
            throw new UnsupportedOperationException("context can't empty, please init me in SpHelpUtils.class");
        }
        if (isSpace(spName)) {
            spName = context.getPackageName() + "_preferences";
        }
        SpUtils spUtils = SP_UTILS_MAP.get(spName);
        if (spUtils == null) {
            synchronized (SpUtils.class) {
                spUtils = SP_UTILS_MAP.get(spName);
                if (spUtils == null) {
                    spUtils = new SpUtils(context, spName, mode);
                    SP_UTILS_MAP.put(spName, spUtils);
                }
            }
        }
        return spUtils;
    }

    private SpUtils(final Context context, final String spName, final int mode) {
        sp = context.getSharedPreferences(spName, mode);
    }


    /*string*/
    public void put(@NonNull final String key, final String value) {
        sp.edit().putString(key, value).apply();
    }

    public String getString(@NonNull final String key) {
        return getString(key, "");
    }

    public String getString(@NonNull final String key, final String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    /*int*/
    public void put(@NonNull final String key, final int value) {
        sp.edit().putInt(key, value).apply();
    }

    public int getInt(@NonNull final String key) {
        return getInt(key, -1);
    }

    public int getInt(@NonNull final String key, final int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    /*long*/
    public void put(@NonNull final String key, final long value) {
        sp.edit().putLong(key, value).apply();
    }

    public long getLong(@NonNull final String key) {
        return getLong(key, -1L);
    }

    public long getLong(@NonNull final String key, final long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    /*float*/
    public void put(@NonNull final String key, final float value) {
        sp.edit().putFloat(key, value).apply();
    }

    public float getFloat(@NonNull final String key) {
        return getFloat(key, -1F);
    }

    public float getFloat(@NonNull final String key, final float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    /*boolean*/
    public void put(@NonNull final String key, final boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(@NonNull final String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    /*map*/
    public void put(@NonNull final Map<String, Object> map) {
        SharedPreferences.Editor edit = sp.edit();
        for (Map.Entry<String, Object> next : map.entrySet()) {
            if (next.getValue() == null) continue;
            if (next.getValue() instanceof String) {
                edit.putString(next.getKey(), String.valueOf(next.getValue()));
            } else if (next.getValue() instanceof Boolean) {
                edit.putBoolean(next.getKey(), (Boolean) next.getValue());
            } else if (next.getValue() instanceof Integer) {
                edit.putInt(next.getKey(), (Integer) next.getValue());
            } else if (next.getValue() instanceof Float) {
                edit.putFloat(next.getKey(), (Float) next.getValue());
            } else if (next.getValue() instanceof Long) {
                edit.putLong(next.getKey(), (Long) next.getValue());
            } else {
                throw new UnsupportedOperationException("parameter Unsupported type!");
            }
        }
        edit.apply();
    }

    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    /*Set<String>*/
    public void put(@NonNull final String key, final Set<String> value) {
        sp.edit().putStringSet(key, value).apply();
    }

    public Set<String> getStringSet(@NonNull final String key) {
        return getStringSet(key, Collections.<String>emptySet());
    }

    public Set<String> getStringSet(@NonNull final String key, final Set<String> defaultValue) {
        return sp.getStringSet(key, defaultValue);
    }

    /*list*/
    public <T> void put(String key, List<T> list) {
        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(list);
        sp.edit().putString(key, strJson).apply();
    }

    public <T> ArrayList<T> getList(String key, Class<T> cls) {
        ArrayList<T> list = new ArrayList<T>();
        String strJson = sp.getString(key, null);
        if (TextUtils.isEmpty(strJson)) return list;
        Gson gson = new Gson();
        JsonArray array = new JsonParser().parse(strJson).getAsJsonArray();
        for (JsonElement jsonElement : array) {
            list.add(gson.fromJson(jsonElement, cls));
        }
        return list;
    }

    /*Object*/
    public <T> void put(String key, Object object) {
        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(object);
        sp.edit().putString(key, strJson).apply();
    }

    public <T> T getObject(String key, Class<T> cls) {
        T t = null;
        String strJson = sp.getString(key, "");
        if (TextUtils.isEmpty(strJson)) return t;
        Gson gson = new Gson();
        JsonObject object = new JsonParser().parse(strJson).getAsJsonObject();
        t = gson.fromJson(object, cls);
        return t;
    }

    public boolean contains(@NonNull final String key) {
        return sp.contains(key);
    }

    public void remove(@NonNull final String key) {
        sp.edit().remove(key).apply();
    }

    public void clear() {
        sp.edit().clear().apply();
    }

    private static boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}