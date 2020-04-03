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

package com.google.samples.apps.sunflower.utilities;

import android.content.Context;

import com.google.samples.apps.sunflower.data.AppDatabase;
import com.google.samples.apps.sunflower.data.GardenPlantingRepository;
import com.google.samples.apps.sunflower.data.PlantRepository;
import com.google.samples.apps.sunflower.viewmodels.GardenPlantingListViewModelFactory;
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModelFactory;
import com.google.samples.apps.sunflower.viewmodels.PlantListViewModelFactory;

public class InjectorUtils {

    private PlantRepository getPlantRepository(Context context) {
        return PlantRepository.getInstance(
                AppDatabase.getInstance(context.getApplicationContext()).plantDao());
    }

    private GardenPlantingRepository getGardenPlantingRepository(Context context) {
        return GardenPlantingRepository.getInstance(
                AppDatabase.getInstance(context.getApplicationContext()).gardenPlantingDao());
    }

    public GardenPlantingListViewModelFactory provideGardenPlantingListViewModelFactory(Context context)  {
        GardenPlantingRepository repository = getGardenPlantingRepository(context);
        return new GardenPlantingListViewModelFactory(repository);
    }

    public PlantListViewModelFactory providePlantListViewModelFactory(Context context) {
        PlantRepository repository = getPlantRepository(context);
        return new PlantListViewModelFactory(repository);
    }

    public PlantDetailViewModelFactory providePlantDetailViewModelFactory(
            Context context,
            String plantId) {
        return new PlantDetailViewModelFactory(getPlantRepository(context),
                getGardenPlantingRepository(context), plantId);
    }
}
