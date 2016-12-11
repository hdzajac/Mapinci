package com.mapi.mapinci.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.mapi.mapinci.R;

public class InputFragment extends Fragment {


    EditText mRadius;
    EditText mLength;
    OnInputFinished callback;
    Double radius;
    Double length;

    public interface OnInputFinished {
        public void OnInputFinished(Double radius, Double length);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            callback = (OnInputFinished) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnInputFinished");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton myFab = (FloatingActionButton) view.findViewById(R.id.goToShape);
        mRadius = (EditText)view.findViewById(R.id.radiusText);
        mLength = (EditText)view.findViewById(R.id.lengthText);

        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToShapeCreator();
            }
        });
    }

    private void goToShapeCreator() {
        Log.v("mRadius", mRadius.getText().toString());
        Log.v("mLength", mLength.getText().toString());
        try{
            radius = Double.valueOf(mRadius.getText().toString());
            length = Double.valueOf(mLength.getText().toString());
            callback.OnInputFinished(radius, length);
        }
        catch(NumberFormatException ex){
            Log.i("input error", " wrong input");
        }
    }
}


