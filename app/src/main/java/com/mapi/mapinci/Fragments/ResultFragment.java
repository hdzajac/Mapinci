package com.mapi.mapinci.Fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mapi.mapinci.R;
import com.mapi.mapinci.Utils.graph.Node;
import com.mapi.mapinci.Utils.graph.Nodes;
import com.mapi.mapinci.Utils.graph.segments.Shape;

import java.util.ArrayList;
import java.util.List;

public class ResultFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Nodes nodes;

    public void setNodes(Nodes nodes) {
        this.nodes = nodes;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_result);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(12.0f);
        mMap.setMaxZoomPreference(20.0f);
        mMap.animateCamera(CameraUpdateFactory.zoomTo( 18.0f ));

        PolylineOptions lineOptions = null;

        List<Node> nodeList = nodes.getNodes();
        ArrayList<LatLng> points = new ArrayList<>();

        for(int i = 0; i<nodeList.size(); i++) {
            LatLng tmp = new LatLng(nodeList.get(i).getLatitude(),nodeList.get(i).getLongitude());
            points.add(tmp);
        }
        lineOptions.addAll(points);
        lineOptions.width(10);
        lineOptions.color(Color.RED);

        mMap.addPolyline(lineOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(points.get(0)));
    }
}
