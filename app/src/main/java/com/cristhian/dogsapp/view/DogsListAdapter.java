package com.cristhian.dogsapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cristhian.dogsapp.R;
import com.cristhian.dogsapp.databinding.ItemDogBinding;
import com.cristhian.dogsapp.model.DogBreed;
import com.cristhian.dogsapp.util.Util;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class DogsListAdapter extends RecyclerView.Adapter<DogsListAdapter.DogViewHolder> {

    private List<DogBreed> dogsList;

    public DogsListAdapter(ArrayList<DogBreed> dogsList) {
        this.dogsList = dogsList;
    }

    public void updateDogsList(List<DogBreed> newDogsList) {
        dogsList.clear();
        dogsList.addAll(newDogsList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemDogBinding view = DataBindingUtil.inflate(inflater, R.layout.item_dog, parent, false);
        return new DogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        holder.itemView.setDogBreed(dogsList.get(position));
//        ImageView image = holder.itemView.findViewById(R.id.imageView);
//
//        TextView name = holder.itemView.findViewById(R.id.dogName);
//        TextView dogLifespan = holder.itemView.findViewById(R.id.dogLifespan);
//
//        LinearLayout layout = holder.itemView.findViewById(R.id.dogLayout);
//
//        name.setText(dogsList.get(position).dogBreed);
//        dogLifespan.setText(dogsList.get(position).lifeSpan);
//
//        Util.loadImage(image, dogsList.get(position).imageUrl, Util.getProgressDrawable(
//                image.getContext()));
//
//        layout.setOnClickListener(v -> {
//            ListFragmentDirections.ActionDetail action = ListFragmentDirections.actionDetail();
//            action.setDogUuid(dogsList.get(position).uuid);
//            Navigation.findNavController(layout).navigate(action);
//        });
    }

    @Override
    public int getItemCount() {
        return dogsList.size();
    }

    class DogViewHolder extends RecyclerView.ViewHolder {

        public ItemDogBinding itemView;

        public DogViewHolder(@NonNull ItemDogBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }

}
