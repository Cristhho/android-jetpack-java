package com.cristhian.dogsapp.view;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.palette.graphics.Palette;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cristhian.dogsapp.R;
import com.cristhian.dogsapp.databinding.FragmentDetailBinding;
import com.cristhian.dogsapp.model.DogBreed;
import com.cristhian.dogsapp.model.DogPalette;
import com.cristhian.dogsapp.viewmodel.DetailViewModel;

public class DetailFragment extends Fragment {

    private int dogUuid;
    private DetailViewModel detailViewModel;

    private FragmentDetailBinding binding;

    public DetailFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null) {
            dogUuid = DetailFragmentArgs.fromBundle(getArguments()).getDogUuid();
        }

        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        detailViewModel.getDogBreed(dogUuid);

        observeViewModel();

    }

    private void observeViewModel() {
        detailViewModel.dogBreed.observe(this, dogBreed -> {
            if(dogBreed != null && dogBreed instanceof DogBreed) {
                binding.setDogBreed(dogBreed);
                if(dogBreed.imageUrl != null) {
                    setupBackgroundColor(dogBreed.imageUrl);
                }
            }
        });
    }

    private void setupBackgroundColor(String url) {
        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Palette.from(resource)
                                .generate(palette -> {
                                    int intColor = palette.getLightMutedSwatch().getRgb();
                                    DogPalette mPalette = new DogPalette(intColor);
                                    binding.setPalette(mPalette);
                                });
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

}
