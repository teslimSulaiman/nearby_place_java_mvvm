package com.example.owner.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.example.owner.myapplication.api.ApiResponse;
import com.example.owner.myapplication.home.adapter.NearbyPlaceAdapter;
import com.example.owner.myapplication.mapper.NearbyPlaceInfoMapper;
import com.example.owner.myapplication.model.NearbyPlace;
import com.example.owner.myapplication.model.NearbyPlaceInfo;
import com.example.owner.myapplication.viewModel.NearbyPlaceViewModel;
import com.example.owner.myapplication.viewModel.ViewModelFactory;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class MainActivity extends AppCompatActivity {

    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.place_list)
    protected RecyclerView nearbyPlaceRecyclerView;

    private NearbyPlaceAdapter nearbyPlaceAdapter;
    @Inject
    protected NearbyPlaceInfoMapper nearbyPlaceInfoMapper;

    private ArrayList<NearbyPlaceInfo> nearbyPlaceInfos = new ArrayList<>();
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    NearbyPlaceViewModel viewModel;
    protected FusedLocationProviderClient mFusedLocationClient;

    ProgressDialog progressDialog;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = Utility.getProgressDialog(this, getString(R.string.please_wait));
        unbinder = ButterKnife.bind(this);
        ((MyApplication) getApplication()).getAppComponent().doInjection(this);
        initializeList();

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NearbyPlaceViewModel.class);
        viewModel.fetchNearbyPlace().observe(this, this::consumeResponse);
        if (Utility.isNetworkAvailable(this)) {
            if (isLocationPermissionGranted()) {
                getLocationAndMakeRequest();
            } else Toast.makeText(MainActivity.this, getString(R.string.allow_permission), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

    }

    private void initializeList() {

        SlideInUpAnimator animator = new SlideInUpAnimator(new OvershootInterpolator(1f));
        nearbyPlaceRecyclerView.setItemAnimator(animator);

        nearbyPlaceRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        nearbyPlaceRecyclerView.setLayoutManager(layoutManager);
        nearbyPlaceAdapter = new NearbyPlaceAdapter(getLayoutInflater(), nearbyPlaceInfos);
        nearbyPlaceAdapter.setOnNearbyPlaceClickListener(nearbyPlaceClickListener);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(nearbyPlaceAdapter);
        alphaAdapter.setDuration(1000);
        nearbyPlaceRecyclerView.setAdapter(alphaAdapter);

    }

    private NearbyPlaceAdapter.OnNearbyPlaceClickListener nearbyPlaceClickListener = new NearbyPlaceAdapter.OnNearbyPlaceClickListener() {
        @Override
        public void onClick(View v, NearbyPlaceInfo nearbyPlaceInfo, int position) {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra(DetailActivity.NEARBY_LOCATION, nearbyPlaceInfo);
            Toast.makeText(MainActivity.this, nearbyPlaceInfo.getName(), Toast.LENGTH_SHORT).show();
            startActivity(intent);

        }
    };

    private void consumeResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {

            case Utility.LOADING:
                progressDialog.show();
                break;

            case Utility.SUCCESS:
                progressDialog.dismiss();
                renderSuccessResponse(apiResponse.data);
                break;

            case Utility.ERROR:
                progressDialog.dismiss();
                Utility.makeToast(this, getString(R.string.error_in_response));
                break;

            default:
                break;
        }
    }

    private void renderSuccessResponse(NearbyPlace data) {
        if (data != null) {
            if(data.getStatus().compareToIgnoreCase(Utility.OK) !=0){
                Utility.makeToast(this, "STATUS: " + data.getStatus());
            }else{
                List<NearbyPlaceInfo> nearbyPlaceInfoList = nearbyPlaceInfoMapper.mapNearbyPlaceInfo(data);
                nearbyPlaceAdapter.addNearbyPlaces(nearbyPlaceInfoList);
            }

        } else {
            Utility.makeToast(this, getString(R.string.empty_response));
        }

    }

    @SuppressLint("MissingPermission")
    private void getLocationAndMakeRequest() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(android.location.Location location) {
                        if (location != null) {

                            if (Utility.isNetworkAvailable(getApplicationContext())) {
                                viewModel.getNearbyPlaces(location.getLatitude() + "," + location.getLongitude(), Utility.RADIUS, BuildConfig.PLACE_API_KEY);
                            } else {
                                Utility.makeToast(getApplicationContext(),getString(R.string.permission_not_granted));
                            }

                        }
                    }
                });
    }

    public boolean isLocationPermissionGranted() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        getLocationAndMakeRequest();
                    }

                } else {

                    Utility.makeToast(getApplicationContext(), getString(R.string.permission_not_granted_you_cant_use_app));


                }
                return;
            }

        }
    }

}
