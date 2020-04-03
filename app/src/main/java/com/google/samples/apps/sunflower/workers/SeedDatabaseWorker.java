/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.samples.apps.sunflower.data.AppDatabase;
import com.google.samples.apps.sunflower.data.Plant;
import com.google.samples.apps.sunflower.utilities.Constants;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class SeedDatabaseWorker extends Worker {
    private final String TAG = SeedDatabaseWorker.class.getSimpleName();

    public SeedDatabaseWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    @NonNull
    public Result doWork() {
        try {
            InputStream inputStream = getApplicationContext().getAssets().open(Constants.PLANT_DATA_FILENAME);
            JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream));
            Type userListType = new TypeToken<ArrayList<Plant>>(){}.getType();
            Gson gson = new Gson();
            ArrayList<Plant> plantList = gson.fromJson(jsonReader, userListType);

            AppDatabase database = AppDatabase.getInstance(getApplicationContext());
            database.plantDao().insertAll(plantList);

            return Result.success();
        } catch (Exception ex) {
            Log.e(TAG, "Error seeding database", ex);
            return Result.failure();
        }
    }
}
