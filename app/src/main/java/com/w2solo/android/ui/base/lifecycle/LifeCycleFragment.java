package com.w2solo.android.ui.base.lifecycle;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * Created by tangyuchun on 2018/4/16.
 */

public class LifeCycleFragment extends Fragment implements LifecycleOwner {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        addLifecycleObserver(new LoggerLifecycleObserver());
        super.onCreate(savedInstanceState);
    }

    protected void addLifecycleObserver(LifecycleObserver observer) {
        getLifecycle().addObserver(observer);
    }
}
