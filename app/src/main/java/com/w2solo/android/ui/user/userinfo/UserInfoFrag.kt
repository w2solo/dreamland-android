package com.w2solo.android.ui.user.userinfo

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.tabs.TabLayout
import com.w2solo.android.R
import com.w2solo.android.app.AppConfigs
import com.w2solo.android.app.account.AccountManager
import com.w2solo.android.data.entity.User
import com.w2solo.android.data.entity.UserMeta
import com.w2solo.android.ui.base.IScrollToTop
import com.w2solo.android.ui.base.activity.BaseToolbarActivity
import com.w2solo.android.ui.base.fragment.BaseFragment
import com.w2solo.android.ui.commonfrag.CommonFragActivity
import com.w2solo.android.ui.topic.userfavorites.UserFavoritesListFrag
import com.w2solo.android.ui.user.follow.UserFollowListFrag

class UserInfoFrag : BaseFragment(), IScrollToTop, UserInfoContract.View {

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_LOGIN = "extra_login"

        fun start(context: Context, user: User) {
            val args = Bundle()
            args.putParcelable(EXTRA_USER, user)
            CommonFragActivity.start(context, R.string.user_info_title, args)
        }
    }

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: UserInfoPagerAdapter
    private val presenter = UserInfoPresenterImpl(this)
    private lateinit var btnFollow: Button

    override fun getLayout() = R.layout.user_info_fragment

    override fun initViews() {
        val user = arguments?.getParcelable<User>(EXTRA_USER)
        val login = if (user != null) user.login else arguments?.getString(EXTRA_LOGIN)
        if (TextUtils.isEmpty(login)) {
            Toast.makeText(context!!, R.string.error_invalid_params, Toast.LENGTH_SHORT).show()
            activity?.finish()
            return
        }
        viewPager2 = fview(R.id.viewpager)!!
        adapter = UserInfoPagerAdapter(login!!, this)
        viewPager2.adapter = adapter

        tabLayout = fview(R.id.tab_layout)!!
        tabLayout.addTab(tabLayout.newTab().setText(R.string.title_following))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.title_follower))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager2.currentItem = tab!!.position
            }
        })
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        //disable toolbar's elevation
        val act = activity
        if (act is BaseToolbarActivity) {
            val appBar = act.getAppbarLayout()
            if (appBar != null) {
                ViewCompat.setElevation(appBar, 0f)
            }
        }
        btnFollow = fview(R.id.user_info_btn_follow_status)!!

        refreshProfile(user, null)
        addLifecycleObserver(presenter)
        presenter.loadUserInfo(login)
    }

    private fun refreshProfile(user: User?, meta: UserMeta?) {
        if (user == null) {
            return
        }
        val avatar = fview<ImageView>(R.id.user_avatar)!!
        val name = fview<TextView>(R.id.user_name)!!
        val location = fview<TextView>(R.id.user_location)!!
        name.text = "${user.name}@${user.login}"
        if (!TextUtils.isEmpty(user.avatar_url)) {
            Glide.with(context!!)
                .load(user.avatar_url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_avatar_default)
                .into(avatar)
        } else {
            avatar.setImageResource(R.drawable.ic_avatar_default)
        }
        val sb = StringBuilder()
        if (!TextUtils.isEmpty(user.levelName)) {
            sb.append(user.levelName)
        }
        if (!TextUtils.isEmpty(user.location)) {
            sb.append(AppConfigs.MIDDLE_DOT).append(user.location)
        }
        if (sb.isNotEmpty()) {
            location.visibility = View.VISIBLE
            location.text = sb
        } else {
            location.visibility = View.GONE
        }
        fview<TextView>(R.id.user_count_following_value)?.text = "${user.followingCount}"
        fview<View>(R.id.user_count_following)?.setOnClickListener {
            val args = Bundle()
            args.putString(UserFollowListFrag.EXTRA_USER_LOGIN, user.login)
            CommonFragActivity.start(it.context, R.string.title_user_follow, args)
        }

        fview<TextView>(R.id.user_count_follower_value)?.text = "${user.followersCount}"
        fview<TextView>(R.id.user_count_score_value)?.text = "${user.score}"
        fview<TextView>(R.id.user_count_favorite_value)?.text = "${user.favoritesCount}"
        fview<View>(R.id.user_count_favorite)?.setOnClickListener {
            val args = Bundle()
            args.putString(UserFavoritesListFrag.EXTRA_LOGIN, user.login)
            CommonFragActivity.start(it.context, R.string.title_user_favorite, args)
        }

        tabLayout.getTabAt(0)!!.text =
            "${getString(R.string.title_user_topic)}(${user.topicsCount})"
        tabLayout.getTabAt(1)!!.text =
            "${getString(R.string.title_user_reply)}(${user.repliesCount})"

        if (meta != null) {
            refreshFollow(meta.followed)
            btnFollow.setOnClickListener {
                if (AccountManager.checkLogin(context)) {
                    presenter.followOrNot(user.login!!, !meta.followed)
                }
            }
        } else {
            fview<Button>(R.id.user_info_btn_follow_status)?.visibility = View.GONE
        }
    }

    private fun refreshFollow(flag: Boolean) {
        btnFollow.visibility = View.VISIBLE
        btnFollow.isSelected = flag
        btnFollow.setText(if (flag) R.string.followed else R.string.not_followed)
    }

    override fun scrollToTop() {
        val frag = adapter.getFragment(viewPager2.currentItem)
        if (frag != null && frag is IScrollToTop) {
            frag.scrollToTop()
        }
    }

    override fun onGetInfo(user: User?, meta: UserMeta?) {
        if (user == null) {
            Toast.makeText(context!!, R.string.error_invalid_params, Toast.LENGTH_SHORT).show()
            activity?.finish()
            return
        }
        refreshProfile(user, meta)
    }

    override fun onFollowOrNot(isSuccess: Boolean, newFollowStatus: Boolean) {
        if (!isSuccess) {
            Toast.makeText(context!!, R.string.error_request_failed, Toast.LENGTH_SHORT).show()
            return
        }
        refreshFollow(newFollowStatus)
    }
}