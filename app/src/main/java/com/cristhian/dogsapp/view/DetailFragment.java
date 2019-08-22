package com.cristhian.dogsapp.view;


import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.palette.graphics.Palette;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cristhian.dogsapp.R;
import com.cristhian.dogsapp.databinding.FragmentDetailBinding;
import com.cristhian.dogsapp.databinding.SendSmsDialogBinding;
import com.cristhian.dogsapp.model.DogBreed;
import com.cristhian.dogsapp.model.DogPalette;
import com.cristhian.dogsapp.model.SmsInfo;
import com.cristhian.dogsapp.viewmodel.DetailViewModel;

public class DetailFragment extends Fragment {

    private int dogUuid;
    private DetailViewModel detailViewModel;

    private FragmentDetailBinding binding;

    private boolean sendSmsStarted = false;

    private DogBreed currentDog;

    public DetailFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        setHasOptionsMenu(true);
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
                currentDog = dogBreed;
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detail_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_send_sms: {
                if(!sendSmsStarted) {
                    sendSmsStarted = true;
                    ((MainActivity) getActivity()).checkSmsPermission();
                }
                break;
            }
            case R.id.action_share: {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this doggy");
                intent.putExtra(Intent.EXTRA_TEXT, currentDog.dogBreed + " bred for " + currentDog.bredFor);
                intent.putExtra(Intent.EXTRA_STREAM, currentDog.imageUrl);
                startActivity(Intent.createChooser(intent, "Share with"));
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void onPermissionResult(boolean permissionGranted) {
        if(isAdded() && sendSmsStarted && permissionGranted) {
            SmsInfo info = new SmsInfo("", currentDog.dogBreed + " bred for " + currentDog.bredFor, currentDog.imageUrl);
            SendSmsDialogBinding dialogBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(getContext()),
                    R.layout.send_sms_dialog,
                    null,
                    false);

            new AlertDialog.Builder(getContext())
                    .setView(dialogBinding.getRoot())
                    .setPositiveButton("Semd SMS", (dialogInterface, i) -> {
                        if(!dialogBinding.smsDestination.getText().toString().isEmpty()) {
                            info.to = dialogBinding.smsDestination.getText().toString();
                            sendSms(info);
                        }
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {})
                    .show();
            sendSmsStarted = false;

            dialogBinding.setSmsInfo(info);
        }
    }

    private void sendSms(SmsInfo info) {
        Intent intent = new Intent(getContext(), MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(getContext(), 0, intent, 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(info.to, null, info.text, pi, null);
    }
}
