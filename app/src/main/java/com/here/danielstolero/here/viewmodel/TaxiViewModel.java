/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.here.danielstolero.here.viewmodel;

import android.app.Application;

import com.here.danielstolero.here.MyApplication;
import com.here.danielstolero.here.db.entities.TaxiEntity;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class TaxiViewModel extends AndroidViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<TaxiEntity>> mObservableTaxis;

    public TaxiViewModel(Application application) {
        super(application);

        mObservableTaxis = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableTaxis.setValue(null);

        LiveData<List<TaxiEntity>> taxis = ((MyApplication) application).getRepository().getTaxis();

        // observe the changes of the taxis from the database and forward them
        mObservableTaxis.addSource(taxis, mObservableTaxis::setValue);
    }

    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    public LiveData<List<TaxiEntity>> getTaxis() {
        return mObservableTaxis;
    }
}
