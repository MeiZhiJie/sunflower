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

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;
import com.google.samples.apps.sunflower.databinding.FragmentPlantDetailBinding;
import com.google.samples.apps.sunflower.utilities.InjectorUtils;
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel;
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModelFactory;


public class PlantDetailFragment extends Fragment {
    private String shareText;

    @Override
    @Nullable
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        PlantDetailFragmentArgs args = PlantDetailFragmentArgs.fromBundle(getArguments());
        PlantDetailViewModelFactory factory = new InjectorUtils().providePlantDetailViewModelFactory(requireActivity(), args.getPlantId());
        PlantDetailViewModel plantDetailViewModel = ViewModelProviders.of(this, factory)
                .get(PlantDetailViewModel.class);

        FragmentPlantDetailBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_plant_detail, container, false);
        binding.setViewModel(plantDetailViewModel);
        binding.setLifecycleOwner(this);
        binding.fab.setOnClickListener(view -> {
            plantDetailViewModel.addPlantToGarden();
            Snackbar.make(view, R.string.added_plant_to_garden, Snackbar.LENGTH_LONG).show();
        });

        plantDetailViewModel.getPlant().observe(getViewLifecycleOwner(), plant -> {
            if (plant == null) {
                shareText = "";
            } else {
                shareText = getString(R.string.share_text_plant, plant.getName());
            }
        });

        setHasOptionsMenu(true);

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_plant_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                Intent shareIntent = ShareCompat.IntentBuilder.from(getActivity())
                        .setText(shareText)
                        .setType("text/plain")
                        .createChooserIntent();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                } else {
                    shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                }
                startActivity(shareIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
