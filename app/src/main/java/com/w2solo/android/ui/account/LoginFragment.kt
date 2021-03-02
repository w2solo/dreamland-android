package com.w2solo.android.ui.account

import android.text.TextUtils
import android.view.View
import com.google.android.material.textfield.TextInputLayout
import com.w2solo.android.R
import com.w2solo.android.app.account.AccountManager
import com.w2solo.android.data.entity.User
import com.w2solo.android.ui.base.fragment.BaseFragment

class LoginFragment : BaseFragment(), LoginContract.View {

    private val presenter = LoginPresenterImpl(this)

    override fun getLayout() = R.layout.login_fragment

    override fun initViews() {
        fview<View>(R.id.login_btn_confirm)?.setOnClickListener {
            val account =
                fview<TextInputLayout>(R.id.login_input_account)!!.editText!!.text.toString().trim()
            val pwd =
                fview<TextInputLayout>(R.id.login_input_pwd)!!.editText!!.text.toString().trim()
            if (TextUtils.isEmpty(account)) {
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(pwd)) {
                return@setOnClickListener
            }
            presenter.startLogin(account, pwd)
        }
        addLifecycleObserver(presenter)
    }

    override fun onLogin(user: User?) {
        if (AccountManager.getInstance().isLogin) {
            activity?.finish()
        }
    }
}