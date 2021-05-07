package com.iug.jerusalem_city.ui.city_information;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.iug.jerusalem_city.databinding.ActivityCityInformationBinding;
import com.iug.jerusalem_city.models.InformationModel;
import com.iug.jerusalem_city.utils.Constants;
import com.iug.jerusalem_city.utils.NavigationDrawerSetting;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

public class CityInformationActivity extends AppCompatActivity implements CityInformationPresenter.CityInformationListener {

    private ActivityCityInformationBinding binding;

    private static final String TAG = "CityInformationActivity";

    private SharedPreferences spSettings;
    private int textSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCityInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CityInformationPresenter presenter = new CityInformationPresenter(this, this);
        presenter.loadCityInfo();

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        spSettings = getSharedPreferences(Constants.SETTINGS_FILE_SHARED_NAME, MODE_PRIVATE);
        textSize = spSettings.getInt(Constants.TEXT_SIZE_KEY, 18);
        binding.infoTitleText.setTextSize(textSize);
        NavigationDrawerSetting.setUpNavigationDrawer(TAG, this, binding.infoDrawerLayout, binding.infoNavigationView, binding.infoToolbar, binding.infoMenu);
        super.onStart();
    }

    private void setUpSliderView(SliderView sliderView, List<String> data) {
        SliderImagesAdapter sliderAdapter = new SliderImagesAdapter(CityInformationActivity.this, data);
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

    @Override
    public void getCityInfo(InformationModel data) {
        binding.progressBar.setVisibility(View.GONE);
        binding.containerInfo.setVisibility(View.VISIBLE);
        setUpSliderView(binding.sliderDetails, data.getImages());
        binding.infoTitleText.setText(data.getText());
    }
}