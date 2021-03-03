package com.w2solo.android.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.inputmethodservice.InputMethodService;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.PermissionChecker;

import com.w2solo.android.app.AppHolder;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;

/**
 * Created by tangyuchun on 2014/8/17.
 */
public class FunctionUtils {
    public static double parseDouble(String value) {
        if (TextUtils.isEmpty(value) || TextUtils.isEmpty(value.trim())) {
            return 0d;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (Throwable thr) {
            thr.printStackTrace();
        }
        return 0d;
    }

    /**
     * 隐藏输入法键盘
     */
    public static void hideSoftKeyboard(@Nullable Context context) {
        if (!(context instanceof Activity)) {
            return;
        }
        Activity activity = (Activity) context;
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void hideSoftKeyboardOfWindow(Window window) {
        if (window != null) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        } else {
            AppLog.INSTANCE.w("FunctionUtils", "隐藏输入法失败，method=hideSoftKeyboardOfWindow msg=window is NULL");
        }
    }

    /**
     * 显示输入法键盘
     */
    public static void showSoftKeyboard(Context context, View view) {
        if (view.requestFocus()) {
            InputMethodManager inputMethodManager = (InputMethodManager) context
                    .getSystemService(InputMethodService.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static String getCurrentLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 是否中文（包含香港台湾）
     *
     * @return
     */
    public static boolean isChineseLanguage() {
        String language = getCurrentLanguage().toLowerCase();
        return !TextUtils.isEmpty(language) && language.startsWith("zh");
    }

    /**
     * 中国大陆地区
     *
     * @return
     */
    public static boolean isChineseMain() {
        return "CN".equalsIgnoreCase(getCurrentRegion());
    }

    public static String getCurrentRegion() {
        return Locale.getDefault().getCountry();
    }

    private static int versionCode = -1;
    private static String versionName = "";
    private static String marketName = "none";

    public static int getVersionCode(Context context) {
        if (versionCode < 0) {
            PackageManager pm = context.getPackageManager();
            if (pm == null) {
                return 0;
            }
            try {
                PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
                if (pi != null) {
                    versionCode = pi.versionCode;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                versionCode = 0;
            }
        }
        return versionCode;
    }

    public static String getVersionName(Context context) {
        if (TextUtils.isEmpty(versionName)) {
            PackageManager pm = context.getPackageManager();
            if (pm == null) {
                return "";
            }
            try {
                PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
                if (pi != null) {
                    versionName = pi.versionName;
                }
            } catch (PackageManager.NameNotFoundException e) {
                versionName = "";
            }
        }
        return versionName;
    }

    public static String getMarketName() {
        if (TextUtils.isEmpty(marketName) || TextUtils.equals(marketName, "none")) {
            try {
                Application context = AppHolder.INSTANCE.getApplication();
                ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                        PackageManager.GET_META_DATA);
                if (info.metaData != null) {
                    String v = info.metaData.getString("APP_MARKET_NAME");
                    if (!TextUtils.isEmpty(v)) {
                        marketName = v;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return marketName;
    }

    public static boolean isGoogleMarket() {
        return "google".equals(getMarketName());
    }

    public static boolean isHuaweiMarket() {
        return "huawei".equals(getMarketName());
    }

    public static boolean isInBeta(Context context) {
        return getVersionName(context).contains("b");
    }

    public static boolean isYingYongBaoMarket() {
        String name = getMarketName();
        return !TextUtils.isEmpty(name) && (name.startsWith("yingyongbao") || name.startsWith("yybao_"));
    }

    public static Resources getResources() {
        return AppHolder.INSTANCE.getApplication().getResources();
    }

    @ColorInt
    public static int getColor(@ColorRes int colorResId) {
        return getResources().getColor(colorResId);
    }

    public static String getString(@StringRes int resId) {
        return getResources().getString(resId);
    }

    public static int parseColor(String color) {
        if (!TextUtils.isEmpty(color)) {
            try {
                return Color.parseColor(color);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Color.rgb(0, 190, 144);
    }

    public static String generateUUID() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid;
    }

    /**
     * 手机号验证
     *
     * @return 验证通过返回true
     */
    public static boolean isPhoneNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            Matcher m = Patterns.PHONE.matcher(str);
            return m.matches();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean isEmail(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(str);
        return matcher.matches();
    }

    public static boolean isUrl(String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        return text.startsWith("http://") || text.startsWith("https://");
    }

    public static boolean isAppHostUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        return url.contains("qianjiapp.com");
    }

    public static long generateId() {
        Random random = new Random();
        int seed = random.nextInt(10000) + 10000;
        return Long.valueOf(System.currentTimeMillis() + "" + seed);
    }


    /**
     * 是否需要检测权限，锤子手机需要读取存储权限才能使用ContentResolver，坑爹
     *
     * @param context
     * @return
     */
    public static boolean shouldCheckStoragePermission(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            return PermissionChecker.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PermissionChecker.PERMISSION_GRANTED;
        }
        return false;
    }

    public static void openWechat(Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用Intent打开网页
     *
     * @param context
     * @param scheme
     * @return
     */
    public static boolean openScheme(Context context, @Nullable String scheme) {
        if (TextUtils.isEmpty(scheme)) {
            return false;
        }
        try {
            Uri uri = Uri.parse(scheme);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String twoDigit(int v) {
        return v < 10 ? "0" + v : v + "";
    }

    public static String dumpStackTrace(Throwable ex) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            return sw.toString();
        } catch (Throwable thr) {
            thr.printStackTrace();
        }
        return "EMPTY";
    }
}

