package com.w2solo.android.app.account;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.w2solo.android.R;
import com.w2solo.android.app.broadcast.AppBroadcast;
import com.w2solo.android.data.entity.User;
import com.w2solo.android.ui.base.broadcast.BroadcastHelper;
import com.w2solo.android.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录账号管理
 * Project QianjiAnd
 * Package com.mutangtech.qianji
 * Created by github.com/litang0908 on 9/11/16.
 */

public class AccountManager {
    private static final String TAG = AccountManager.class.getSimpleName();
    public static final Long EMPTY_USER_ID = -1L;
    private static AccountManager instance;

    @NonNull
    private final IAccountKeeper<User> keeper;
    private List<AccountCallback> callbacks;

    public static AccountManager getInstance() {
        if (null == instance) {
            synchronized (AccountManager.class) {
                if (null == instance) {
                    instance = new AccountManager();
                }
            }
        }
        return instance;
    }

    private AccountManager() {
        long start = System.currentTimeMillis();
        keeper = new DefaultAccountKeeper<User>() {
            @Override
            public User parseAccountFromKeepInfo(String value) {
                return new Gson().fromJson(value, User.class);
            }

            @Override
            public String convertAccountToKeepInfo(User user) {
                return new Gson().toJson(user);
            }
        };
        Logger.INSTANCE.d(TAG, "tang-----初始化登录账户耗时  " + (System.currentTimeMillis() - start));
    }

    @Nullable
    public User getLoginUser() {
        return keeper.getCurrentUser();
    }

    @NonNull
    public Long getLoginUserID() {
        User user = getLoginUser();
        return user != null ? user.getId() : EMPTY_USER_ID;
    }

    @Nullable
    public Token getUserToken() {
        return keeper.getUserToken();
    }

    /**
     * 保存用户信息
     *
     * @param userInfo 当前登录用户信息
     */
    public boolean onLogin(User userInfo, Token token) {
        boolean result = keeper.onLogin(userInfo, token);
        BroadcastHelper.INSTANCE.sendBroadcast(AppBroadcast.ACCOUNT_CHANGE);
        triggerCallback(true);
        return result;
    }

    public boolean updateToken(Token token) {
        return keeper.updateToken(token);
    }

    public boolean updateUserInfo(User userInfo) {
        boolean result = keeper.onUpdate(userInfo);
        BroadcastHelper.INSTANCE.sendBroadcast(AppBroadcast.ACCOUNT_CHANGE);
        return result;
    }

    public boolean isLogin() {
        return keeper.getCurrentUser() != null;
    }

    public boolean logout() {
        //清空账号信息
        boolean result = keeper.onLogout();
        Logger.INSTANCE.d(TAG, "退出登录 isLogin " + isLogin() + "  clearResult " + result);
        BroadcastHelper.INSTANCE.sendBroadcast(AppBroadcast.ACCOUNT_CHANGE);
        triggerCallback(false);
        return true;
    }

    public static boolean checkLogin(Context context) {
        boolean isLogin = getInstance().isLogin();
        if (!isLogin) {
            Toast.makeText(context, R.string.error_not_login, Toast.LENGTH_SHORT).show();
        }
        return isLogin;
    }

    public void addCallbacks(AccountCallback callback) {
        if (callback == null) {
            return;
        }
        if (callbacks == null) {
            callbacks = new ArrayList<>();
        }
        callbacks.add(callback);
    }

    private void triggerCallback(boolean isLogin) {
        if (callbacks == null || callbacks.size() == 0) {
            return;
        }
        for (AccountCallback c : callbacks) {
            c.onLoginChange(isLogin);
        }
    }

}