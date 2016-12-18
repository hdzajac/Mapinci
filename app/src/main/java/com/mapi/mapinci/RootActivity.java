package com.mapi.mapinci;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.model.LatLng;
import com.mapi.mapinci.Fragments.DrawingFragment;
import com.mapi.mapinci.Fragments.InputFragment;
import com.mapi.mapinci.Fragments.MapsFragment;
import com.mapi.mapinci.Fragments.ResultFragment;
import com.mapi.mapinci.Utils.graph.Nodes;

public class RootActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MapsFragment.OnStartingPointSelected, InputFragment.OnInputFinished, DrawingFragment.OnSuccessResponse {

    LatLng startingPoint;
    Double radius;
    Double length;
    int fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment newFragment = new MapsFragment();

        fragmentManager.beginTransaction().replace(R.id.content_frame, newFragment).commit();

    }



    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.root, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment newFragment;

        if (id == R.id.nav_new) {
            newFragment = new MapsFragment();
        } else if (id == R.id.nav_saved) {
            newFragment = new DrawingFragment();
        } else if (id == R.id.nav_settings) {
            newFragment = new DrawingFragment();
        } else {
            newFragment = new DrawingFragment();
        }
        fragmentManager.beginTransaction().replace(R.id.content_frame, newFragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStartingPointSelected(LatLng startingPoint) {
        this.startingPoint = startingPoint;

        InputFragment newFragment = new InputFragment();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, newFragment, "NewFragmentTag");
        ft.commit();
    }

    @Override
    public void OnInputFinished(Double radius, Double length) {
        this.radius = radius;
        this.length = length;
        DrawingFragment newFragment = new DrawingFragment();
        newFragment.setData(startingPoint, radius, length);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, newFragment, "NewFragmentTag");
        ft.commit();
    }

    @Override
    public void OnSuccessResponse(Nodes nodes) {
        ResultFragment newFragment = new ResultFragment();
        newFragment.setNodes(nodes);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, newFragment, "NewFragmentTag");
        ft.commit();
    }
}
