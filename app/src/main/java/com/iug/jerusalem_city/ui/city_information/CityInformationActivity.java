package com.iug.jerusalem_city.ui.city_information;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.iug.jerusalem_city.databinding.ActivityCityInformationBinding;
import com.iug.jerusalem_city.utils.NavigationDrawerSetting;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class CityInformationActivity extends AppCompatActivity {

    private ActivityCityInformationBinding binding;
    private StorageReference storageRef;

    private static final String TAG = "CityInformationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCityInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        setUpSliderView(binding.sliderDetails);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        NavigationDrawerSetting.setUpNavigationDrawer(TAG, this, binding.infoDrawerLayout, binding.infoNavigationView, binding.infoToolbar);
        super.onStart();
    }

    private void setUpSliderView(SliderView sliderView) {
        SliderImagesAdapter sliderAdapter = new SliderImagesAdapter(CityInformationActivity.this);
        sliderAdapter.addItem("images/2018-1091x520-c.jpg");
        sliderAdapter.addItem("images/af74dc0e-43c0-4a42-93e6-6c6837c8c965.jpeg");
        sliderAdapter.addItem("images/fdqsmudlk-8887-ssss.jpg");
        sliderView.setSliderAdapter(sliderAdapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

    }

}