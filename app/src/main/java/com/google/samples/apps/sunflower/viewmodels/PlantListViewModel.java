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
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.samples.apps.sunflower.data.Plant;
import com.google.samples.apps.sunflower.data.PlantRepository;

import java.util.List;

public class PlantListViewModel extends ViewModel {
    private final int NO_GROW_ZONE = -1;
    private MutableLiveData<Integer> growZoneNumber = new MutableLiveData<>(NO_GROW_ZONE);

    private PlantRepository plantRepository;

    PlantListViewModel(PlantRepository plantRepository) {
        super();
        this.plantRepository = plantRepository;
    }

    public void setGrowZoneNumber(int value) {
        growZoneNumber.setValue(value);
    }

    public void clearGrowZoneNumber() {
        growZoneNumber.setValue(NO_GROW_ZONE);
    }

    public boolean isFiltered() {
        return  growZoneNumber.getValue() != NO_GROW_ZONE;
    }

    public LiveData<List<Plant>> getPlants() {
        return Transformations.switchMap(growZoneNumber, it -> {
            if (it == NO_GROW_ZONE) {
                return plantRepository.getPlants();
            } else {
                return plantRepository.getPlantsWithGrowZoneNumber(it);
            }
        });
    }
}
