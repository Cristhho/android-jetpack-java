package com.cristhian.dogsapp.viewmodel;

import com.cristhian.dogsapp.model.DogBreed;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetailViewModel extends ViewModel {

    public MutableLiveData<DogBreed> dogBreed = new MutableLiveData<>();

    public void getDogBreed() {
        DogBreed dog1 = new DogBreed("1", "corgi", "15 years", "", "companionship", "calm and friendly", "");

        dogBreed.setValue(dog1);
    }

}
