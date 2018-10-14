package com.here.danielstolero.here;

import android.app.Application;

import com.here.danielstolero.here.db.AppDatabase;

public class MyApplication extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }

    public AppExecutors getAppExecutors() {
        return mAppExecutors;
    }
}
