package com.w2solo.android.app.account

import android.text.TextUtils
import com.google.gson.Gson
import com.w2solo.android.module.keyvalue.KV

abstract class DefaultAccountKeeper<T : IAccount> : IAccountKeeper<T> {

    private val USER_INFO = "login_user_info"
    private val USER_SEC_TOKEN = "login_user_tokenv2"

    private var userInfo: T? = null
    private var curToken: Token? = null

    init {
        userInfo = readUserInfo()
        curToken = userToken
    }

    abstract fun parseAccountFromKeepInfo(value: String?): T?

    abstract fun convertAccountToKeepInfo(user: T?): String?

    override fun getCurrentUser(): T? {
        return userInfo
    }

    override fun onLogin(user: T?, token: Token?): Boolean {
        return saveUserInfo(user) && saveUserToken(token)
    }

    override fun onUpdate(user: T?): Boolean {
        return saveUserInfo(user)
    }

    override fun onLogout(): Boolean {
        return saveUserInfo(null) && saveUserToken(null)
    }

    override fun updateToken(token: Token?): Boolean {
        return saveUserToken(token)
    }

    private fun readUserInfo(): T? {
        val value = KV.getSysStr(USER_INFO, null)
        return if (TextUtils.isEmpty(value)) {
            null
        } else parseAccountFromKeepInfo(value)
    }

    private fun saveUserToken(token: Token?): Boolean {
        this.curToken = token
        var value = "";
        if (token != null) {
            value = Gson().toJson(token)
        }
        return KV.saveSysValue(USER_SEC_TOKEN, value)
    }

    override fun getUserToken(): Token? {
        if (curToken != null) {
            return curToken
        }
        val value = KV.getSysStr(USER_SEC_TOKEN, "")
        if (!TextUtils.isEmpty(value)) {
            curToken = Gson().fromJson(value, Token::class.java)
        }
        return curToken
    }

    private fun saveUserInfo(user: T?): Boolean {
        userInfo = user
        return KV.saveSysValue(USER_INFO, convertAccountToKeepInfo(user))
    }
}