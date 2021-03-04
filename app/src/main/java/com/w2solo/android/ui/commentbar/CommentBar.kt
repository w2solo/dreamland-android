package com.w2solo.android.ui.commentbar

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.w2solo.android.R
import com.w2solo.android.app.account.AccountManager
import com.w2solo.android.data.entity.Comment
import com.w2solo.android.utils.FunctionUtils

class CommentBar(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs),
    CommentBarContract.View {

    private lateinit var editText: EditText
    private lateinit var sendButton: Button
    private var presenter: CommentPresenterImpl? = null
    private var callback: Callback? = null
    private var dataId: Long = -1

    override fun onFinishInflate() {
        super.onFinishInflate()
        editText = findViewById(R.id.comment_bar_input)
        sendButton = findViewById(R.id.comment_bar_send)
        sendButton.setOnClickListener {
            if (!AccountManager.checkLogin(it.context)) {
                return@setOnClickListener
            }
            val content = editText.text.toString().trim()
            presenter?.sendReplay(dataId, content)
        }
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    sendButton.isEnabled = !TextUtils.isEmpty(s.toString().trim())
                } else {
                    sendButton.isEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        presenter = CommentPresenterImpl(this)
        setOnClickListener {
            if (AccountManager.getInstance().isLogin) {
                editText.requestFocus()
            }
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        if (!AccountManager.checkLogin(context)) {
            return
        }
        super.setOnClickListener(l)
    }

    override fun onDetachedFromWindow() {
        presenter?.onDestroy(null)
        super.onDetachedFromWindow()
    }

    fun setDataId(dataId: Long) {
        this.dataId = dataId
    }

    fun setCallback(callback: Callback?) {
        this.callback = callback
    }

    override fun onCommentFinished(comment: Comment?) {
        if (comment != null) {
            editText.text = null
            callback?.onSuccess(comment)
            //hide keyboard if comment success
            FunctionUtils.hideSoftKeyboard(context)
        } else {
            Toast.makeText(context, R.string.error_request_failed, Toast.LENGTH_SHORT).show()
        }
    }

    interface Callback {
        fun onSuccess(newComment: Comment)
    }
}