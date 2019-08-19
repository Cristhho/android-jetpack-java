package com.cristhian.dogsapp.model;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DogDao {

    @Insert
    List<Long> insertAll(DogBreed... dogs);

    @Query("Select * from dogbreed")
    List<DogBreed> getAllDogs();

    @Query("Select * from dogbreed where uuid = :dogId")
    DogBreed getDog(int dogId);

    @Query("delete from dogbreed")
    void deleteAllDogs();

}
