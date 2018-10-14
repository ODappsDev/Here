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

package com.here.danielstolero.here.db;

import android.graphics.Color;

import com.here.danielstolero.here.db.entities.TaxiEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generates data to pre-populate the database
 */
public class DataGenerator {

    private static final int NUM_OF_TAXIS = 5;
    private static final int MIN_ETA = 30; // Min
    private static final int MAX_ETA = 360; // Min

    private static final String[] TAXIS = new String[]{
            "Castle", "Shekem", "Habima", "Gordon", "Azrieli", "Hadera"};

    public static List<TaxiEntity> generateTaxis() {
        List<TaxiEntity> entities = new ArrayList<>(TAXIS.length * NUM_OF_TAXIS);
        Random rnd = new Random();
        for (int i = 0; i < TAXIS.length; i++) {
            for (int j = 1; j <= NUM_OF_TAXIS; j++) {
                TaxiEntity taxi = new TaxiEntity();
                taxi.setName(TAXIS[i] + " " + j);
                taxi.setEta(MIN_ETA + rnd.nextInt(MAX_ETA));
                taxi.setId(TAXIS.length * i + j + 1);
                taxi.setColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
                entities.add(taxi);
            }
        }
        return entities;
    }
}
