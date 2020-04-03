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

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Repository module for handling data operations.
 */
public class PlantRepository {
    private static PlantRepository instance = null;
    private PlantDao plantDao;

    private PlantRepository(PlantDao plantDao) {
        this.plantDao = plantDao;
    }

    public LiveData<List<Plant>> getPlants() {
        return plantDao.getPlants();
    }

    public LiveData<Plant> getPlant(String plantId) {
        return plantDao.getPlant(plantId);
    }

    public LiveData<List<Plant>> getPlantsWithGrowZoneNumber(int growZoneNumber) {
        return plantDao.getPlantsWithGrowZoneNumber(growZoneNumber);
    }

    public static PlantRepository getInstance(PlantDao plantDao) {
        if (instance == null) {
            synchronized (PlantRepository.class) {
                if (instance == null) {
                    instance = new PlantRepository(plantDao);
                }
            }
        }
        return instance;
    }
}
