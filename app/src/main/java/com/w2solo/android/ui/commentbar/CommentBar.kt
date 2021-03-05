package com.w2solo.android.ui.commentbar

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.*
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
    private var dataId: Long = -1L
    private var editReplyId: Long = -1L

    override fun onFinishInflate() {
        super.onFinishInflate()
        editText = findViewById(R.id.comment_bar_input)
        sendButton = findViewById(R.id.comment_bar_send)
        sendButton.setOnClickListener {
            if (!AccountManager.checkLogin(it.context)) {
                return@setOnClickListener
            }
            val content = editText.text.toString().trim()
            if (editReplyId > 0L) {
                presenter?.editReply(editReplyId, content)
            } else {
                presenter?.sendReply(dataId, content)
            }
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

    override fun onDetachedFromWindow() {
        presenter?.onDestroy(null)
        super.onDetachedFromWindow()
    }

    fun setDataId(dataId: Long) {
        this.dataId = dataId
    }

    fun setEditComment(comment: Comment?) {
        if (comment != null) {
            editReplyId = comment.id
            val titleView = findViewById<TextView>(R.id.comment_bar_title)
            titleView.visibility = View.VISIBLE
            titleView.setText(R.string.comment_option_edit)
            editText.setText(comment.body)
            editText.setSelection(comment.body?.length ?: 0)
            editText.requestFocus()
            FunctionUtils.showSoftKeyboard(context, editText)
        } else {
            clearStates()
        }
    }

    private fun clearStates() {
        findViewById<View>(R.id.comment_bar_title).visibility = View.GONE
        editReplyId = -1L
        editText.text = null
    }

    fun setCallback(callback: Callback?) {
        this.callback = callback
    }

    override fun onCommentFinished(comment: Comment?) {
        if (comment != null) {
            callback?.onSuccess(comment, false)
            //hide keyboard if comment success
            FunctionUtils.hideSoftKeyboard(context)
            clearStates()
        } else {
            Toast.makeText(context, R.string.error_request_failed, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onEditCommentFinished(comment: Comment?) {
        if (comment == null) {
            Toast.makeText(context, R.string.error_request_failed, Toast.LENGTH_SHORT).show()
        } else {
            callback?.onSuccess(comment, true)
            //hide keyboard if comment success
            FunctionUtils.hideSoftKeyboard(context)
            clearStates()
        }
    }

    interface Callback {
        fun onSuccess(newComment: Comment, isEdit: Boolean)
    }
}