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
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.samples.apps.sunflower.adapters.GardenPlantingAdapter;
import com.google.samples.apps.sunflower.databinding.FragmentGardenBinding;
import com.google.samples.apps.sunflower.utilities.InjectorUtils;
import com.google.samples.apps.sunflower.viewmodels.GardenPlantingListViewModel;
import com.google.samples.apps.sunflower.viewmodels.GardenPlantingListViewModelFactory;


public class GardenFragment extends Fragment {
    @Override
    @Nullable
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        FragmentGardenBinding binding = FragmentGardenBinding.inflate(inflater, container, false);
        GardenPlantingAdapter adapter = new GardenPlantingAdapter();
        binding.gardenList.setAdapter(adapter);
        subscribeUi(adapter, binding);
        return binding.getRoot();
    }

    private void subscribeUi(GardenPlantingAdapter adapter, FragmentGardenBinding binding) {

        GardenPlantingListViewModelFactory factory = new InjectorUtils().provideGardenPlantingListViewModelFactory(requireContext());

        GardenPlantingListViewModel viewModel = ViewModelProviders.of(this, factory)
                .get(GardenPlantingListViewModel.class);
        viewModel.getGardenPlantings().observe(getViewLifecycleOwner(),
                plantings -> binding.setHasPlantings(!plantings.isEmpty())
        );

        viewModel.getPlantAndGardenPlantings().observe(getViewLifecycleOwner(), result -> {
            if (!result.isEmpty()) {
                adapter.submitList(result);
            }
        });
    }
}
