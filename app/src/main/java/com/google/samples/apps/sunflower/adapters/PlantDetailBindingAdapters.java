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

package com.google.samples.apps.sunflower.adapters;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.samples.apps.sunflower.R;

public class PlantDetailBindingAdapters {
    @BindingAdapter("imageFromUrl")
    public static void bindImageFromUrl(ImageView view, @Nullable String imageUrl) {
        if (imageUrl != null &&  !"".equals(imageUrl)) {
            Glide.with(view.getContext())
                    .load(imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(view);
        }
    }

    @BindingAdapter("isGone")
    public static void bindIsGone(FloatingActionButton view, @Nullable Boolean isGone) {
        if (isGone == null || isGone) {
            view.hide();
        } else {
            view.show();
        }
    }

    @BindingAdapter("renderHtml")
    public static void bindRenderHtml(TextView view, @Nullable String description) {
        if (description != null) {
            view.setText(HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT));
            view.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            view.setText("");
        }
    }

    @BindingAdapter("wateringText")
    public static void bindWateringText(TextView textView, @NonNull Integer wateringInterval) {
        Resources resources = textView.getContext().getResources();
        String quantityString = resources.getQuantityString(R.plurals.watering_needs_suffix,
                wateringInterval, wateringInterval);

        SpannableStringBuilder ssb = new SpannableStringBuilder();
        String temp = resources.getString(R.string.watering_needs_prefix);
        SpannableString txtSpannable;
        txtSpannable = new SpannableString(temp);
        txtSpannable.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, temp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(txtSpannable);
        ssb.append(" ");
        txtSpannable= new SpannableString(quantityString);
        txtSpannable.setSpan(new StyleSpan(Typeface.ITALIC), 0, quantityString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(txtSpannable);
    }
}
