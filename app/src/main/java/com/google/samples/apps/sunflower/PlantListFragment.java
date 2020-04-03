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

package com.google.samples.apps.sunflower;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.samples.apps.sunflower.adapters.PlantAdapter;
import com.google.samples.apps.sunflower.databinding.FragmentPlantListBinding;
import com.google.samples.apps.sunflower.utilities.InjectorUtils;
import com.google.samples.apps.sunflower.viewmodels.PlantListViewModel;
import com.google.samples.apps.sunflower.viewmodels.PlantListViewModelFactory;

public class PlantListFragment extends Fragment {
    private PlantListViewModel viewModel;

    @Override
    @Nullable
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        FragmentPlantListBinding binding = FragmentPlantListBinding.inflate(inflater, container, false);
        if (getContext() == null) {
            return binding.getRoot();
        }

        PlantListViewModelFactory factory = new InjectorUtils().providePlantListViewModelFactory(getContext());
        viewModel = ViewModelProviders.of(this, factory).get(PlantListViewModel.class);
        PlantAdapter adapter = new PlantAdapter();
        binding.plantList.setAdapter(adapter);
        subscribeUi(adapter);

        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_plant_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_zone:
                updateData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void subscribeUi(PlantAdapter adapter) {
        viewModel.getPlants().observe(getViewLifecycleOwner(), plants -> {
            if (plants != null) adapter.submitList(plants);
        });
    }

    private void updateData() {
        if (viewModel.isFiltered()) {
            viewModel.clearGrowZoneNumber();
        } else {
            viewModel.setGrowZoneNumber(9);
        }
    }
}
