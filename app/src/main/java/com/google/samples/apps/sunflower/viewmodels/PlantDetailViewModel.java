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

package com.google.samples.apps.sunflower.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.samples.apps.sunflower.data.GardenPlanting;
import com.google.samples.apps.sunflower.data.GardenPlantingRepository;
import com.google.samples.apps.sunflower.data.Plant;
import com.google.samples.apps.sunflower.data.PlantRepository;

public class PlantDetailViewModel extends ViewModel {
    private GardenPlantingRepository gardenPlantingRepository;
    private String plantId;
    private LiveData<Plant> plant;
    private LiveData<Boolean> isPlanted;

    public PlantDetailViewModel(PlantRepository plantRepository,
                                GardenPlantingRepository gardenPlantingRepository,
                                String plantId) {
        super();
        this.gardenPlantingRepository = gardenPlantingRepository;
        this.plantId = plantId;

        /* The getGardenPlantingForPlant method returns a LiveData from querying the database. The
         * method can return null in two cases: when the database query is running and if no records
         * are found. In these cases isPlanted is false. If a record is found then isPlanted is
         * true. */
        LiveData<GardenPlanting> gardenPlantingForPlant = gardenPlantingRepository.getGardenPlantingForPlant(plantId);
        isPlanted = Transformations.map(gardenPlantingForPlant, planting -> planting != null);
        plant = plantRepository.getPlant(plantId);
    }

    public LiveData<Plant> getPlant() {
        return plant;
    }

    public LiveData<Boolean> getIsPlanted() {
        return isPlanted;
    }

    public void addPlantToGarden() {
            gardenPlantingRepository.createGardenPlanting(plantId);
    }
}
