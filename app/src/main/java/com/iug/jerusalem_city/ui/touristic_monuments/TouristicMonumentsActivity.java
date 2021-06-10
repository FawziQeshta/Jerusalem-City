package com.iug.jerusalem_city.ui.touristic_monuments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.iug.jerusalem_city.databinding.ActivityTouristicMonumentsBinding;
import com.iug.jerusalem_city.data.models.TopicModel;
import com.iug.jerusalem_city.ui.city_history.TopicsAdapter;
import com.iug.jerusalem_city.utils.NavigationDrawerSetting;

import java.util.ArrayList;
import java.util.List;

public class TouristicMonumentsActivity extends AppCompatActivity implements TouristicMonumentsPresenter.CityTouristicMonumentsListener {

    private ActivityTouristicMonumentsBinding binding;

    private List<TopicModel> data;
    private TopicsAdapter adapter;

    private static final String TAG = "TouristicMonumentsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTouristicMonumentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initRecyclerView();

        TouristicMonumentsPresenter presenter = new TouristicMonumentsPresenter(this, this);
        presenter.loadTouristicMonumentsTopics();

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

    private void initRecyclerView() {
        data = new ArrayList<>();
        adapter = new TopicsAdapter(this, data);
        binding.rvTouristicTopics.setLayoutManager(new LinearLayoutManager(this));
        binding.rvTouristicTopics.setHasFixedSize(true);
        binding.rvTouristicTopics.setAdapter(adapter);
    }

    @Override
    public void getTouristicMonumentsTopics(List<TopicModel> topicData) {
        binding.progressBar.setVisibility(View.GONE);
        data.addAll(topicData);
        adapter.notifyDataSetChanged();
    }

}