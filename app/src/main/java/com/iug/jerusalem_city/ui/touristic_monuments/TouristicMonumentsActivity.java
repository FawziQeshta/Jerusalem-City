package com.iug.jerusalem_city.ui.touristic_monuments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.iug.jerusalem_city.R;
import com.iug.jerusalem_city.databinding.ActivityTouristicMonumentsBinding;
import com.iug.jerusalem_city.models.TopicData;
import com.iug.jerusalem_city.ui.city_history.TopicsAdapter;
import com.iug.jerusalem_city.utils.NavigationDrawerSetting;

import java.util.ArrayList;
import java.util.List;

public class TouristicMonumentsActivity extends AppCompatActivity {

    private ActivityTouristicMonumentsBinding binding;

    private List<TopicData> data;
    private TopicsAdapter adapter;

    private static final String TAG = "TouristicMonumentsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTouristicMonumentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadData();

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        NavigationDrawerSetting.setUpNavigationDrawer(TAG, this, binding.historyDrawerLayout, binding.touristicNavigationView, binding.touristicToolbar, binding.historyMenu);
        super.onStart();
    }

    private void loadData() {
        data = new ArrayList<>();
        data.add(new TopicData("1", "بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس v بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدس", "images/2018-1091x520-c.jpg", null, false));
        data.add(new TopicData("1", "بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس v بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدس", "images/2018-1091x520-c.jpg", null, false));
        data.add(new TopicData("1", "بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس v بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدس", "images/2018-1091x520-c.jpg", "videos/تعرف على مدينة القدس كأنك فيها.mp4", true));
        data.add(new TopicData("1", "بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس v بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدس", "images/2018-1091x520-c.jpg", null, false));
        data.add(new TopicData("1", "بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس v بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدس", "images/2018-1091x520-c.jpg", null, false));
        data.add(new TopicData("1", "بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدس v بيت المقدس بيت المقدس بيت المقدس بيت المقدس بيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدسبيت المقدس", "images/2018-1091x520-c.jpg", "videos/تعرف على مدينة القدس كأنك فيها.mp4", true));
        adapter = new TopicsAdapter(this, data);
        binding.rvTouristicTopics.setLayoutManager(new LinearLayoutManager(this));
        binding.rvTouristicTopics.setHasFixedSize(true);
        binding.rvTouristicTopics.setAdapter(adapter);
    }

}