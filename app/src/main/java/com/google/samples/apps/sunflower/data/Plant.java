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

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName = "plants")
public class Plant {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private String plantId;

    private String name;
    private String description;
    private int growZoneNumber;
    private int wateringInterval = 7; // how often the plant should be watered, in days
    private String imageUrl = "";

    @NonNull
    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(@NonNull String plantId) {
        this.plantId = plantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGrowZoneNumber() {
        return growZoneNumber;
    }

    public void setGrowZoneNumber(int growZoneNumber) {
        this.growZoneNumber = growZoneNumber;
    }

    public int getWateringInterval() {
        return wateringInterval;
    }

    public void setWateringInterval(int wateringInterval) {
        this.wateringInterval = wateringInterval;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Determines if the plant should be watered.  Returns true if [since]'s date > date of last
     * watering + watering Interval; false otherwise.
     */
    public boolean shouldBeWatered(Calendar since, Calendar lastWateringDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(lastWateringDate.getTime());
        cal.add(Calendar.DAY_OF_YEAR, wateringInterval);
        return since.after(cal);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        Plant plant = (Plant) obj;

        return plant.getPlantId().equals(this.plantId)
                && plant.getName().equals(this.name)
                && plant.getDescription().equals(this.description)
                && plant.getGrowZoneNumber() == this.growZoneNumber
                && plant.getWateringInterval() == this.wateringInterval
                && plant.getImageUrl().equals(this.imageUrl);
    }
}
