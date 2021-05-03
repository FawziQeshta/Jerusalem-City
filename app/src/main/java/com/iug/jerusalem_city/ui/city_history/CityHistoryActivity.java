package com.iug.jerusalem_city.ui.city_history;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.iug.jerusalem_city.databinding.ActivityHistoryCityBinding;
import com.iug.jerusalem_city.models.TopicData;
import com.iug.jerusalem_city.utils.NavigationDrawerSetting;

import java.util.ArrayList;
import java.util.List;

public class CityHistoryActivity extends AppCompatActivity {

    private ActivityHistoryCityBinding binding;
    private List<TopicData> data;
    private TopicsAdapter adapter;

    private static final String TAG = "CityHistoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryCityBinding.inflate(getLayoutInflater());
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
        NavigationDrawerSetting.setUpNavigationDrawer(TAG, this, binding.historyDrawerLayout, binding.historyNavigationView, binding.historyToolbar, binding.historyMenu);
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
        binding.rvHistoryTopics.setLayoutManager(new LinearLayoutManager(this));
        binding.rvHistoryTopics.setHasFixedSize(true);
        binding.rvHistoryTopics.setAdapter(adapter);
    }
}