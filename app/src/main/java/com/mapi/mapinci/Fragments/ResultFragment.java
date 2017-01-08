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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mapi.mapinci.R;
import com.mapi.mapinci.Utils.graph.Node;
import com.mapi.mapinci.Utils.graph.Nodes;
import com.mapi.mapinci.Utils.graph.segments.Shape;

import java.util.ArrayList;
import java.util.List;

import static com.mapi.mapinci.R.id.map;

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
        mMap.setMinZoomPreference(15.0f);
        mMap.setMaxZoomPreference(20.0f);
        mMap.animateCamera(CameraUpdateFactory.zoomTo( 16.0f ));



        List<Node> nodeList = nodes.getNodes();

//        System.out.println(nodeList.toString());

        ArrayList<LatLng> points = new ArrayList<>();

        for(int i = 0; i<nodeList.size(); i++) {
            LatLng tmp = new LatLng(nodeList.get(i).getLatitude(),nodeList.get(i).getLongitude());

            System.out.println(tmp.toString());

            points.add(tmp);
        }


//        LatLng n0 = new LatLng(50.0683096, 19.9196125);
//        points.add(n0);
//        LatLng n1 = new LatLng(50.0683182, 19.9196063);
//        points.add(n1);
//        LatLng n2 = new LatLng(50.0683096, 19.9196125);
//        points.add(n2);
//        LatLng n3 = new LatLng(50.0682592, 19.9198551);
//        points.add(n3);
//        LatLng n4 = new LatLng(50.0683916, 19.9203469);
//        points.add(n4);
//        LatLng n5 = new LatLng(50.0684266, 19.9204862);
//        points.add(n5);
//        LatLng n6 = new LatLng(50.0684595, 19.9206487);
//        points.add(n6);
//        LatLng n7 = new LatLng( 50.0684266, 19.9204862);
//        points.add(n7);
//        LatLng n8 = new LatLng(50.0683916, 19.9203469);
//        points.add(n8);
//        LatLng n9 = new LatLng(50.0682592, 19.9198551);
//        points.add(n9);
//        LatLng n10 = new LatLng( 50.0682151, 19.9196881);
//        points.add(n10);
//        LatLng n11 = new LatLng(50.0681595, 19.9194873);
//        points.add(n11);
//        LatLng n12 = new LatLng(50.0681126, 19.9193166);
//        points.add(n12);
//        LatLng n13 = new LatLng(50.0680634, 19.9191441);
//        points.add(n13);
//        LatLng n14 = new LatLng(50.068018, 19.9189749);
//        points.add(n14);
//        LatLng n15 = new LatLng( 50.0679717, 19.9188051);
//        points.add(n15);
//        LatLng n16 = new LatLng(50.0679201, 19.918615);
//        points.add(n16);
//        LatLng n17 = new LatLng(50.0679717, 19.9188051);
//        points.add(n17);
//        LatLng n18 = new LatLng(50.068018, 19.9189749);
//        points.add(n18);

        Polyline line = mMap.addPolyline(new PolylineOptions()
                .addAll(points)
                .width(5)
                .color(Color.RED));

        line.setVisible(true);

//        for(int i = 0; i < 19; i++) {
//            LatLng tmp = points.get(i);
//            mMap.addMarker(new MarkerOptions().position(tmp));
//        }

        LatLng cracow = new LatLng(50.0679865, 19.9124113);
        if(points.size() > 0) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(points.get(0)));
        }
    }
}
