package com.w2solo.android.app.account

import android.text.TextUtils
import com.w2solo.android.module.keyvalue.KV

abstract class DefaultAccountKeeper<T : IAccount> : IAccountKeeper<T> {

    private val USER_INFO = "login_user_info"
    private val USER_SEC_TOKEN = "login_user_tokenv2"

    private var userInfo: T? = null

    init {
        userInfo = readUserInfo()
    }

    abstract fun parseAccountFromKeepInfo(value: String?): T?

    abstract fun convertAccountToKeepInfo(user: T?): String?

    override fun getCurrentUser(): T? {
        return userInfo
    }

    override fun onLogin(user: T?, token: String?): Boolean {
        userInfo = user
        return saveUserInfo(user) && saveUserToken(token)
    }

    override fun onUpdate(user: T?): Boolean {
        userInfo = user
        return saveUserInfo(user)
    }

    override fun onLogout(): Boolean {
        userInfo = null
        return saveUserInfo(null) && saveUserToken(null)
    }

    override fun updateToken(token: String?): Boolean {
        return saveUserToken(token)
    }

    private fun readUserInfo(): T? {
        val value = KV.getSysStr(USER_INFO, null)
        return if (TextUtils.isEmpty(value)) {
            null
        } else parseAccountFromKeepInfo(value)
    }

    private fun saveUserToken(token: String?): Boolean {
        return KV.saveSysValue(USER_SEC_TOKEN, token)
    }

    override fun getUserToken(): String? {
        return KV.getSysStr(USER_SEC_TOKEN, "")
    }

    private fun saveUserInfo(user: T?): Boolean {
        return KV.saveSysValue(USER_INFO, convertAccountToKeepInfo(user))
    }
}