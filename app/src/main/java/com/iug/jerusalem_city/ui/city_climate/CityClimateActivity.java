package com.iug.jerusalem_city.ui.city_climate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.iug.jerusalem_city.databinding.ActivityCityClimateBinding;
import com.iug.jerusalem_city.models.TopicData;
import com.iug.jerusalem_city.ui.city_history.CityHistoryPresenter;
import com.iug.jerusalem_city.ui.city_history.TopicsAdapter;
import com.iug.jerusalem_city.utils.NavigationDrawerSetting;

import java.util.ArrayList;
import java.util.List;

public class CityClimateActivity extends AppCompatActivity implements CityClimatePresenter.CityClimateListener {

    private ActivityCityClimateBinding binding;

    private List<TopicData> data;
    private TopicsAdapter adapter;

    private static final String TAG = "CityClimateActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCityClimateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initRecyclerView();

        CityClimatePresenter presenter = new CityClimatePresenter(this, this);
        presenter.loadClimateTopics();

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        NavigationDrawerSetting.setUpNavigationDrawer(TAG, this, binding.historyDrawerLayout, binding.climateNavigationView, binding.climateToolbar, binding.climateMenu);
        super.onStart();
    }

    private void initRecyclerView() {
        data = new ArrayList<>();
        adapter = new TopicsAdapter(this, data);
        binding.rvClimateTopics.setLayoutManager(new LinearLayoutManager(this));
        binding.rvClimateTopics.setHasFixedSize(true);
        binding.rvClimateTopics.setAdapter(adapter);
    }


    @Override
    public void getClimateTopics(List<TopicData> topicData) {
        binding.progressBar.setVisibility(View.GONE);
        data.addAll(topicData);
        adapter.notifyDataSetChanged();
    }

}