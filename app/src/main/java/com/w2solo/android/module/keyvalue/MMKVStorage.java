package com.w2solo.android.module.keyvalue;

import android.content.ContentValues;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.tencent.mmkv.MMKV;
import com.w2solo.android.utils.Logger;

import java.util.Map;
import java.util.Set;

/**
 * Created by tangyuchun on 2018/11/12.
 */

class MMKVStorage implements IKVStorage {

    private static final String TAG = "MMKVStorage";

    private MMKV instance;

    MMKVStorage(String storageID) {
        //支持多进程
        instance = MMKV.mmkvWithID(storageID, MMKV.MULTI_PROCESS_MODE);
    }

    @Override
    public int getInt(String key, int defValue) {
        if (key == null) {
            return defValue;
        }
        long start = System.currentTimeMillis();
        int result = instance.decodeInt(key, defValue);
        if (Logger.INSTANCE.isEnabled()) {
            debugReadTime("getInt", key, result, start);
        }
        return result;
    }

    @Override
    public long getLong(String key, Long defValue) {
        if (key == null) {
            return defValue;
        }
        long start = System.currentTimeMillis();
        long result = instance.decodeLong(key, defValue);
        if (Logger.INSTANCE.isEnabled()) {
            debugReadTime("getLong", key, result, start);
        }
        return result;
    }

    @Override
    public float getFloat(String key, Float defValue) {
        if (key == null) {
            return defValue;
        }
        long start = System.currentTimeMillis();
        float result = instance.decodeFloat(key, defValue);
        if (Logger.INSTANCE.isEnabled()) {
            debugReadTime("getFloat", key, result, start);
        }
        return result;
    }

    @Override
    public double getDouble(String key, Double defValue) {
        if (key == null) {
            return defValue;
        }
        long start = System.currentTimeMillis();
        double result = instance.decodeDouble(key, defValue);
        if (Logger.INSTANCE.isEnabled()) {
            debugReadTime("getDouble", key, result, start);
        }
        return result;
    }

    @Override
    public boolean getBool(String key, boolean defValue) {
        if (key == null) {
            return defValue;
        }
        long start = System.currentTimeMillis();
        boolean result = instance.decodeBool(key, defValue);
        if (Logger.INSTANCE.isEnabled()) {
            debugReadTime("getBool", key, result, start);
        }
        return result;
    }

    @Override
    public String getString(String key, @Nullable String defValue) {
        if (key == null) {
            return defValue;
        }
        long start = System.currentTimeMillis();
        String result = instance.decodeString(key, defValue);
        if (Logger.INSTANCE.isEnabled()) {
            debugReadTime("getString", key, result, start);
        }
        return result;
    }

    private void debugReadTime(String msg, String key, Object value, long time) {
        if (Logger.INSTANCE.isEnabled()) {
            Logger.INSTANCE.d(TAG, msg + " key=" + key + " ,value=" + value + ", time=" + (System.currentTimeMillis() - time));
        }
    }

    @Override
    public Set<String> getStringSet(String key, @Nullable Set<String> defValue) {
        if (key == null) {
            return defValue;
        }
        long start = System.currentTimeMillis();
        Set<String> result = instance.decodeStringSet(key, defValue);
        if (Logger.INSTANCE.isEnabled()) {
            debugReadTime("getStringSet", key, result, start);
        }
        return result;
    }

    /**
     * 存储，如果是 null ，则等同于 remove 方法
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean save(String key, Object value) {
        long start = System.currentTimeMillis();
        boolean result = false;
        if (value == null) {//删除
            instance.removeValueForKey(key);
            result = true;
        } else if (value instanceof Integer) {
            result = instance.encode(key, (Integer) value);
        } else if (value instanceof Float) {
            result = instance.encode(key, (Float) value);
        } else if (value instanceof Double) {
            result = instance.encode(key, (Double) value);
        } else if (value instanceof Long) {
            result = instance.encode(key, (Long) value);
        } else if (value instanceof Boolean) {
            result = instance.encode(key, (Boolean) value);
        } else if (value instanceof String) {
            result = instance.encode(key, (String) value);
        } else if (value instanceof Set) {
            result = instance.encode(key, (Set) value);
        }
        if (Logger.INSTANCE.isEnabled() && result) {
            Logger.INSTANCE.d(TAG, "MMKVStorage.save key=" + key + ",value=" + value + " time=" + (System.currentTimeMillis() - start));
        }
        if (!result) {
            Logger.INSTANCE.e(TAG, "MMKVStorage.save(String key,Object value) error: wrong value type,key=" + key + " value=" + value + " time=" + (System.currentTimeMillis() - start));
        }
        return result;
    }

    @Override
    public boolean save(ContentValues values) {
        if (values == null || values.size() == 0) {
            return false;
        }
        String key;
        int count = 0;
        for (Map.Entry<String, Object> entry : values.valueSet()) {
            key = entry.getKey();
            if (TextUtils.isEmpty(key)) {
                Logger.INSTANCE.e(TAG, "MMKVStorage.save(ContentValues) error:empty key!!!");
                continue;
            }
            if (save(key, entry.getValue())) {
                count++;
            }
        }
        return count == values.size();
    }

    @Override
    public boolean containsKey(String key) {
        return instance.containsKey(key);
    }

    @Override
    public void remove(String key) {
        instance.removeValueForKey(key);
    }

    @Override
    public String[] allKeys() {
        return instance.allKeys();
    }
}
