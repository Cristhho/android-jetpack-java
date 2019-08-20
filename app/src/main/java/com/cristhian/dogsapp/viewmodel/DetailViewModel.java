package com.cristhian.dogsapp.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import com.cristhian.dogsapp.model.DogBreed;
import com.cristhian.dogsapp.model.DogDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class DetailViewModel extends AndroidViewModel {

    public MutableLiveData<DogBreed> dogBreed = new MutableLiveData<>();

    private AsyncTask<Integer, Void, DogBreed> fetchDogTask;

    public DetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void getDogBreed(int uuid) {
        fetchDogTask = new RetrieveDogTask();
        fetchDogTask.execute(uuid);
    }

    private void dogRetrieved(DogBreed breed) {
        dogBreed.setValue(breed);
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if(fetchDogTask != null) {
            fetchDogTask.cancel(true);
            fetchDogTask = null;
        }
    }

    private class RetrieveDogTask extends AsyncTask<Integer, Void, DogBreed> {

        @Override
        protected DogBreed doInBackground(Integer... integers) {
            return DogDatabase.getInstance(getApplication()).dogDao().getDog(integers[0]);
        }

        @Override
        protected void onPostExecute(DogBreed dogBreed) {
            dogRetrieved(dogBreed);
        }
    }

}
