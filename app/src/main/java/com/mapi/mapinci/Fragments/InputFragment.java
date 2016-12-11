package com.mapi.mapinci.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mapi.mapinci.R;

public class InputFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton myFab = (FloatingActionButton) view.findViewById(R.id.goToShape);

        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToShapeCreator();
            }
        });
    }

    private void goToShapeCreator() {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new DrawingFragment(), "NewFragmentTag");
        ft.commit();
    }
}


