package com.mapi.mapinci.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mapi.mapinci.R;

public class StartFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button goButton = (Button) view.findViewById(R.id.goButton);

        goButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToMapsFragment();
            }
        });
    }

    private void goToMapsFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment newFragment = new MapsFragment();

        fragmentManager.beginTransaction().replace(R.id.content_frame, newFragment, "MapsFragment").commit();

    }
}


