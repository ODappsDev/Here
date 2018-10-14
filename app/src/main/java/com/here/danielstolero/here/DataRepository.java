package com.here.danielstolero.here;

import com.here.danielstolero.here.db.AppDatabase;
import com.here.danielstolero.here.db.entities.TaxiEntity;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

/**
 * Repository handling the work with taxis.
 */
public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<TaxiEntity>> mObservableTaxis;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
        mObservableTaxis = new MediatorLiveData<>();

        mObservableTaxis.addSource(mDatabase.taxiDao().loadAllProducts(),
                productEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableTaxis.postValue(productEntities);
                    }
                });
    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    public LiveData<List<TaxiEntity>> getTaxis() {
        return mObservableTaxis;
    }

    public void updateEta(int [] ids) {
        mDatabase.taxiDao().updateEta(ids);
    }

    @Nullable
    public TaxiEntity isFinish() {
        return mDatabase.taxiDao().isFinish();
    }
}
