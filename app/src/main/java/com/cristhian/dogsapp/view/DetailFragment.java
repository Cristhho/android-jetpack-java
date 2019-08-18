package com.cristhian.dogsapp.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cristhian.dogsapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailFragment extends Fragment {

    @BindView(R.id.listActionButton)
    FloatingActionButton fab;

    public DetailFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fab.setOnClickListener(view1 -> goToList());
    }

    private void goToList() {
        NavDirections action = DetailFragmentDirections.actionList();
        Navigation.findNavController(fab).navigate(action);
    }
}
