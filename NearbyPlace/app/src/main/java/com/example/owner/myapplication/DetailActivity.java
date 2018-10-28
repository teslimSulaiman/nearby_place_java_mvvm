package com.example.owner.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.owner.myapplication.model.NearbyPlaceInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailActivity extends AppCompatActivity {

    public static final String NEARBY_LOCATION = "nearby_location";

    @BindView(R.id.place_name)
    TextView placeName;
    @BindView(R.id.place_address)
    TextView placeAddress;
    @BindView(R.id.photo)
    ImageView photo;
    @BindView(R.id.button)
    Button button;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        unbinder = ButterKnife.bind(this);
        NearbyPlaceInfo nearbyPlaceInfo = (NearbyPlaceInfo) intent.getSerializableExtra(DetailActivity.NEARBY_LOCATION);
        setTitle(getString(R.string.place_detail));

        placeName.setText(nearbyPlaceInfo.getName());
        placeAddress.setText(nearbyPlaceInfo.getVicinity());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_placeholder);
        requestOptions.error(R.drawable.error);
        requestOptions.centerCrop();

        Glide.with(this)
                .load(Utility.buildPlacePhotoUrl(nearbyPlaceInfo.getPhoto()))
                .apply(requestOptions)

                .into(photo);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String lat = String.valueOf(nearbyPlaceInfo.getLatitude());
                String log = String.valueOf(nearbyPlaceInfo.getLongitude());
                // Do something in response to button click
                Uri gmmIntentUri = Uri.parse("geo:" + lat + "," + log);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

    }
}
