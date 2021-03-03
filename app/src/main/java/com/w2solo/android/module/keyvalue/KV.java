package com.w2solo.android.module.keyvalue;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tencent.mmkv.MMKV;
import com.w2solo.android.utils.AppLog;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 使用MMKV做新的KV存储系统，同时需要兼容旧的SP
 * 原谅我写这么诡异的代码，SP目前没法直接迁移 = =
 * <p>
 * Created by tanjie on 15-1-28.
 *
 * @author tanjie
 */
public class KV {

    public static final int TYPE_SYSTEM = 0;
    public static final int TYPE_USER = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TYPE_SYSTEM, TYPE_USER})
    @interface Type {
    }

    private static final String TAG = "MMKV-KV";

    private static volatile IKVStorage sysStorage;
    private static volatile Map<String, IKVStorage> userStorageMap = new HashMap<>();
    private static volatile IKvKit kit;

    /*************** START Base ***************/
    public static void init(Context context, @NonNull IKvKit accountKit) {
        kit = accountKit;
        long start = System.currentTimeMillis();
        MMKV.initialize(context);
        AppLog.INSTANCE.d(TAG, "MMKV init time=" + (System.currentTimeMillis() - start));
    }

    public static IKVStorage getStorageInstance(@Type int type) {
        return getStorage(type);
    }

    public static String getUserId() {
        return kit.getUserId();
    }

    private static boolean checkUser() {
        return !TextUtils.isEmpty(kit.getUserId());
    }

    @NonNull
    static IKVStorage getStorage(@Type int type) {
        if (type == TYPE_USER) {
            String userid = KV.getUserId();
            IKVStorage storage = userStorageMap.get(userid);
            if (storage == null) {
                storage = new MMKVStorage("kvu_" + userid);
                userStorageMap.put(userid, storage);
            }
            return storage;
        }
        if (type == TYPE_SYSTEM) {
            if (null == sysStorage) {
                //通用的KV存储值，写死 system 即可
                sysStorage = new MMKVStorage("kv_system");
            }
            return sysStorage;
        }
        return null;
    }

    /*************** END Base ***************/


    /*************** User Get *******************/

    public static int getUserInt(String key, int defValue) {
        if (!checkUser()) {
            AppLog.INSTANCE.e(TAG, "getUserInt wrong, not login key=" + key);
            return defValue;
        }
        return getStorage(TYPE_USER).getInt(key, defValue);
    }

    public static long getUserLong(String key, Long defValue) {
        if (!checkUser()) {
            AppLog.INSTANCE.e(TAG, "getUserLong wrong, not login key=" + key);
            return defValue;
        }
        return getStorage(TYPE_USER).getLong(key, defValue);
    }

    public static float getUserFloat(String key, Float defValue) {
        if (!checkUser()) {
            AppLog.INSTANCE.e(TAG, "getUserFloat wrong, not login key=" + key);
            return defValue;
        }
        return getStorage(TYPE_USER).getFloat(key, defValue);
    }

    public static double getUserDouble(String key, Double defValue) {
        if (!checkUser()) {
            AppLog.INSTANCE.e(TAG, "getUserDouble wrong, not login key=" + key);
            return defValue;
        }
        return getStorage(TYPE_USER).getDouble(key, defValue);
    }

    public static boolean getUserBool(String key, boolean defValue) {
        if (!checkUser()) {
            AppLog.INSTANCE.e(TAG, "getUserBool wrong, not login key=" + key);
            return defValue;
        }
        return getStorage(TYPE_USER).getBool(key, defValue);
    }

    public static String getUserStr(String key, @Nullable String defValue) {
        if (!checkUser()) {
            AppLog.INSTANCE.e(TAG, "getUserStr wrong, not login key=" + key);
            return defValue;
        }
        return getStorage(TYPE_USER).getString(key, defValue);
    }

    public static Set<String> getUserStrSet(String key, @Nullable Set<String> defValue) {
        if (!checkUser()) {
            AppLog.INSTANCE.e(TAG, "getUserStrSet wrong, not login key=" + key);
            return defValue;
        }
        return getStorage(TYPE_USER).getStringSet(key, defValue);
    }

    /**
     * 仅支持 int,long,float,double,boolean,string,Set<String> 这几种类型
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean saveUserValue(String key, Object value) {
        if (!checkUser()) {
            AppLog.INSTANCE.e(TAG, "saveUserValue wrong, not login key=" + key);
            return false;
        }
        return getStorage(TYPE_USER).save(key, value);
    }

    /**
     * 批量保存：仅支持 int,long,float,double,boolean,string,Set<String> 这几种类型
     *
     * @param values
     * @return
     */
    public static boolean saveUserValues(ContentValues values) {
        if (!checkUser()) {
            AppLog.INSTANCE.e(TAG, "saveUserValues wrong, not login values=" + values);
            return false;
        }
        return getStorage(TYPE_USER).save(values);
    }

    /*************** User Other *******************/

    public static boolean containsUserKey(String key) {
        if (!checkUser()) {
            AppLog.INSTANCE.e(TAG, "containsUserKey wrong, not login key=" + key);
            return false;
        }
        return getStorage(TYPE_USER).containsKey(key);
    }

    public static void removeUserValue(String key) {
        if (!checkUser()) {
            AppLog.INSTANCE.e(TAG, "removeUserValue wrong, not login key=" + key);
            return;
        }
        getStorage(TYPE_USER).remove(key);
    }

    @Nullable
    public static String[] allUserKeys() {
        if (!checkUser()) {
            AppLog.INSTANCE.e(TAG, "allUserKeys wrong, not login");
            return null;
        }
        return getStorage(TYPE_USER).allKeys();
    }

    /*************** System Read *******************/

    public static int getSysInt(String key, int defValue) {
        return getStorage(TYPE_SYSTEM).getInt(key, defValue);
    }

    public static long getSysLong(String key, Long defValue) {
        return getStorage(TYPE_SYSTEM).getLong(key, defValue);
    }

    public static float getSysFloat(String key, Float defValue) {
        return getStorage(TYPE_SYSTEM).getFloat(key, defValue);
    }

    public static double getSysDouble(String key, Double defValue) {
        return getStorage(TYPE_SYSTEM).getDouble(key, defValue);
    }

    public static boolean getSysBool(String key, boolean defValue) {
        return getStorage(TYPE_SYSTEM).getBool(key, defValue);
    }

    public static String getSysStr(String key, @Nullable String defValue) {
        return getStorage(TYPE_SYSTEM).getString(key, defValue);
    }

    public static Set<String> getSysStrSet(String key, @Nullable Set<String> defValue) {
        return getStorage(TYPE_SYSTEM).getStringSet(key, defValue);
    }


    /*************** System Put *******************/

    public static boolean saveSysValue(String key, @Nullable Object value) {
        return getStorage(TYPE_SYSTEM).save(key, value);
    }

    public static boolean saveSysValues(@Nullable ContentValues values) {
        return getStorage(TYPE_SYSTEM).save(values);
    }


    /*************** System Other *******************/

    public static boolean containsSysKey(String key) {
        return getStorage(TYPE_SYSTEM).containsKey(key);
    }

    public static void removeSysValue(String key) {
        getStorage(TYPE_SYSTEM).remove(key);
    }

    @Nullable
    public static String[] allSysKeys() {
        return getStorage(TYPE_SYSTEM).allKeys();
    }

    /*************** Other *******************/
}
