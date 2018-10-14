package com.here.danielstolero.here.base;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    private final String TAG = BaseActivity.class.getSimpleName();

    protected SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResource());

        initViews();
        initListeners();
        initData();
    }

    /**
     * set layout mView resource id on activity.
     */
    protected abstract int setLayoutResource();

    /**
     * initialize views of activity.
     */
    protected abstract void initViews();

    /**
     * initialize listeners of activity.
     */
    protected abstract void initListeners();

    /**
     * initialize data of activity.
     */
    protected abstract void initData();
}