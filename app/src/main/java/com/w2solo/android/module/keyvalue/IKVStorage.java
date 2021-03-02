package com.w2solo.android.module.keyvalue;

import android.content.ContentValues;

import androidx.annotation.Nullable;

import java.util.Set;

/**
 * Created by tangyuchun on 2018/11/12.
 */

public interface IKVStorage {

    int getInt(String key, int defValue);

    long getLong(String key, Long defValue);

    float getFloat(String key, Float defValue);

    double getDouble(String key, Double defValue);

    boolean getBool(String key, boolean defValue);

    String getString(String key, @Nullable String defValue);

    Set<String> getStringSet(String key, @Nullable Set<String> defValue);

    boolean save(String key, @Nullable Object value);

    boolean save(@Nullable ContentValues values);

    boolean containsKey(String key);

    void remove(String key);

    String[] allKeys();
}
