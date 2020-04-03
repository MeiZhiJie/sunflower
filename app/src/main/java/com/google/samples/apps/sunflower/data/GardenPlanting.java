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

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Calendar;

/**
 * [GardenPlanting] represents when a user adds a [Plant] to their garden, with useful metadata.
 * Properties such as [lastWateringDate] are used for notifications (such as when to water the
 * plant).
 *
 * Declaring the column info allows for the renaming of variables without implementing a
 * database migration, as the column name would not change.
 */
@Entity(
        tableName = "garden_plantings",
        foreignKeys = @ForeignKey(entity = Plant.class, parentColumns = "id", childColumns = "plant_id"),
        indices = @Index("plant_id")
)
public class GardenPlanting {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long gardenPlantingId = 0;

    @ColumnInfo(name = "plant_id")
    private String plantId;

    /**
     * Indicates when the [Plant] was planted. Used for showing notification when it's time
     * to harvest the plant.
     */
    @ColumnInfo(name = "plant_date")
    private Calendar plantDate = Calendar.getInstance();

    /**
     * Indicates when the [Plant] was last watered. Used for showing notification when it's
     * time to water the plant.
     */
    @ColumnInfo(name = "last_watering_date")
    private Calendar lastWateringDate = Calendar.getInstance();

    public GardenPlanting() {

    }

    @Ignore
    public GardenPlanting(String plantId) {
        this.plantId = plantId;
    }

    public long getGardenPlantingId() {
        return gardenPlantingId;
    }

    public void setGardenPlantingId(long gardenPlantingId) {
        this.gardenPlantingId = gardenPlantingId;
    }

    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    public Calendar getPlantDate() {
        return plantDate;
    }

    public void setPlantDate(Calendar plantDate) {
        this.plantDate = plantDate;
    }

    public Calendar getLastWateringDate() {
        return lastWateringDate;
    }

    public void setLastWateringDate(Calendar lastWateringDate) {
        this.lastWateringDate = lastWateringDate;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (obj == this)
//            return true;
//
//        GardenPlanting gardenPlanting = (GardenPlanting) obj;
//
//        return gardenPlanting.getGardenPlantingId() == this.gardenPlantingId
//                && gardenPlanting.getPlantId() == this.plantId
//                && gardenPlanting.getPlantDate() == this.plantDate
//                && gardenPlanting.getLastWateringDate() == this.lastWateringDate
//                ;
//    }
}
