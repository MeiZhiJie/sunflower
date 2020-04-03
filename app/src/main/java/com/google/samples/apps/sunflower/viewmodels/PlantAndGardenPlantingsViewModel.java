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

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.ViewModel;

import com.google.samples.apps.sunflower.data.GardenPlanting;
import com.google.samples.apps.sunflower.data.Plant;
import com.google.samples.apps.sunflower.data.PlantAndGardenPlantings;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class PlantAndGardenPlantingsViewModel extends ViewModel {
    private Plant plant;
    private GardenPlanting gardenPlanting;
    private final static SimpleDateFormat dateFormat = new  SimpleDateFormat("MMM d, yyyy", Locale.US);

    public PlantAndGardenPlantingsViewModel(PlantAndGardenPlantings plantings) {
        super();
        plant = plantings.getPlant();
        gardenPlanting = plantings.getGardenPlantings().get(0);
    }

    public ObservableField<String> getWaterDateString() {
        return new ObservableField<>(dateFormat.format(gardenPlanting.getLastWateringDate().getTime()));
    }

    public ObservableInt getWateringInterval() {
        return new ObservableInt(plant.getWateringInterval());
    }

    public ObservableField<String> getImageUrl() {
        return new ObservableField<>(plant.getImageUrl());
    }

    public ObservableField<String> getPlantName() {
        return new ObservableField<>(plant.getName());
    }

    public  ObservableField<String> getPlantDateString() {
        return new ObservableField<>(dateFormat.format(gardenPlanting.getPlantDate().getTime()));
    }
}
