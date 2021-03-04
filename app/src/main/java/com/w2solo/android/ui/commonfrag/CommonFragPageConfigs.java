package com.w2solo.android.ui.commonfrag;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.w2solo.android.R;
import com.w2solo.android.ui.account.LoginFragment;
import com.w2solo.android.ui.topic.detail.TopicDetailFragment;
import com.w2solo.android.ui.user.userinfo.UserInfoFrag;

import java.util.HashMap;
import java.util.Map;

class CommonFragPageConfigs {

    private static final Map<Integer, Class> configs = new HashMap<>();

    static {
        configs.put(R.string.title_login, LoginFragment.class);
        configs.put(R.string.title_topic_detail, TopicDetailFragment.class);
        configs.put(R.string.user_info_title, UserInfoFrag.class);
    }

    @Nullable
    static Fragment newFragment(@StringRes int titleResId, @Nullable Bundle args) {
        Class cls = configs.get(titleResId);
        if (cls == null) {
            return null;
        }
        try {
            Fragment frag = (Fragment) cls.newInstance();
            if (args != null) {
                frag.setArguments(args);
            }
            return frag;
        } catch (Throwable thr) {
            thr.printStackTrace();
        }
        return null;
    }
}
