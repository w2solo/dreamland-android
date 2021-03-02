package com.w2solo.android.ui.base.lifecycle;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * Created by tangyuchun on 2018/4/16.
 */

public class LifeCycleActivity extends AppCompatActivity implements LifecycleOwner {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        addLifecycleObserver(new LoggerLifecycleObserver());
        super.onCreate(savedInstanceState, persistentState);
    }

    protected void addLifecycleObserver(LifecycleObserver observer) {
        if (observer == null) {
            return;
        }
        getLifecycle().addObserver(observer);
    }
}
