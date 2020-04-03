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

package com.google.samples.apps.sunflower.data;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class GardenPlantingRepository {
    private static GardenPlantingRepository instance = null;
    private GardenPlantingDao gardenPlantingDao;

    private GardenPlantingRepository(GardenPlantingDao gardenPlantingDao) {
        this.gardenPlantingDao = gardenPlantingDao;
    }

    public void createGardenPlanting(String plantId) {
        InsertAsyncTask task = new InsertAsyncTask(gardenPlantingDao);
        task.execute(new GardenPlanting(plantId));
    }

    public void removeGardenPlanting(GardenPlanting gardenPlanting) {
        gardenPlantingDao.deleteGardenPlanting(gardenPlanting);
    }

    public LiveData<GardenPlanting> getGardenPlantingForPlant(String plantId) {
        return gardenPlantingDao.getGardenPlantingForPlant(plantId);
    }


    public LiveData<List<GardenPlanting>> getGardenPlantings() {
        return gardenPlantingDao.getGardenPlantings();
    }

    public LiveData<List<PlantAndGardenPlantings>> getPlantAndGardenPlantings() {
        return gardenPlantingDao.getPlantAndGardenPlantings();
    }

    public static GardenPlantingRepository getInstance(GardenPlantingDao gardenPlantingDao) {
        if (instance == null) {
            synchronized (GardenPlantingRepository.class) {
                if (instance == null) {
                    return new GardenPlantingRepository(gardenPlantingDao);
                }
            }
        }
        return instance;
    }

    private static class InsertAsyncTask extends AsyncTask<GardenPlanting, Void, Void> {

        private GardenPlantingDao asyncTaskDao;

        InsertAsyncTask(GardenPlantingDao dao) {
            this.asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final GardenPlanting... params) {
            asyncTaskDao.insertGardenPlanting(params[0]);
            return null;
        }
    }
}
