package com.iug.jerusalem_city.ui.last_news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.iug.jerusalem_city.R;
import com.iug.jerusalem_city.databinding.ActivityLastNewsBinding;
import com.iug.jerusalem_city.models.TopicData;
import com.iug.jerusalem_city.ui.city_history.TopicsAdapter;
import com.iug.jerusalem_city.utils.NavigationDrawerSetting;

import java.util.ArrayList;
import java.util.List;

public class LastNewsActivity extends AppCompatActivity {

    private ActivityLastNewsBinding binding;

    private List<TopicData> data;
    private TopicsAdapter adapter;

    private static final String TAG = "LastNewsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLastNewsBinding.inflate(getLayoutInflater());
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
        NavigationDrawerSetting.setUpNavigationDrawer(TAG, this, binding.historyDrawerLayout, binding.newsNavigationView, binding.newsToolbar, binding.newsMenu);
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
        binding.rvNewsTopics.setLayoutManager(new LinearLayoutManager(this));
        binding.rvNewsTopics.setHasFixedSize(true);
        binding.rvNewsTopics.setAdapter(adapter);
    }

}