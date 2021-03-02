package com.w2solo.android.app.account;

/**
 * Created by github.com/litang0908 on 2018/9/4.
 */

public interface IAccountKeeper<T extends IAccount> {

    T getCurrentUser();

    String getUserToken();

    boolean onLogin(T user, String token);

    boolean onUpdate(T user);

    boolean onLogout();

    boolean updateToken(String token);
}
