package com.iug.jerusalem_city.ui.city_history;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.iug.jerusalem_city.databinding.ActivityHistoryCityBinding;
import com.iug.jerusalem_city.data.models.TopicModel;
import com.iug.jerusalem_city.utils.NavigationDrawerSetting;

import java.util.ArrayList;
import java.util.List;

public class CityHistoryActivity extends AppCompatActivity implements CityHistoryPresenter.CityHistoryListener {

    private ActivityHistoryCityBinding binding;
    private List<TopicModel> data;
    private TopicsAdapter adapter;

    private static final String TAG = "CityHistoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryCityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initRecyclerView();

        CityHistoryPresenter presenter = new CityHistoryPresenter(this, this);
        presenter.loadHistoryTopics();

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

    private void initRecyclerView() {
        data = new ArrayList<>();
        adapter = new TopicsAdapter(this, data);
        binding.rvHistoryTopics.setLayoutManager(new LinearLayoutManager(this));
        binding.rvHistoryTopics.setHasFixedSize(true);
        binding.rvHistoryTopics.setAdapter(adapter);
    }

    @Override
    public void getHistoryTopics(List<TopicModel> topicData) {
        binding.progressBar.setVisibility(View.GONE);
        data.addAll(topicData);
        adapter.notifyDataSetChanged();
    }

}