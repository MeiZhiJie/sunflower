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
import com.google.samples.apps.sunflower.data.PlantAndGardenPlantings;

import java.util.ArrayList;
import java.util.List;

public class GardenPlantingListViewModel extends ViewModel {
    private GardenPlantingRepository gardenPlantingRepository;

    GardenPlantingListViewModel(GardenPlantingRepository gardenPlantingRepository) {
        super();
        this.gardenPlantingRepository = gardenPlantingRepository;
    }

    public LiveData<List<GardenPlanting>> getGardenPlantings() {
        return gardenPlantingRepository.getGardenPlantings();
    }

    public LiveData<List<PlantAndGardenPlantings>> getPlantAndGardenPlantings() {
        return Transformations.map(gardenPlantingRepository.getPlantAndGardenPlantings(), plantings -> {
            List<PlantAndGardenPlantings> list = new ArrayList<>();
            for (PlantAndGardenPlantings one : plantings) {
                if (!one.getGardenPlantings().isEmpty()) {
                    list.add(one);
                }
            }
            return list;
        });
    }
}
