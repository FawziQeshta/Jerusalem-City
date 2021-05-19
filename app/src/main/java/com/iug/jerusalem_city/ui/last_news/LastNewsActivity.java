package com.iug.jerusalem_city.ui.last_news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.iug.jerusalem_city.R;
import com.iug.jerusalem_city.databinding.ActivityLastNewsBinding;
import com.iug.jerusalem_city.models.Article;
import com.iug.jerusalem_city.utils.Constants;
import com.iug.jerusalem_city.utils.NavigationDrawerSetting;

import java.util.ArrayList;
import java.util.List;

public class LastNewsActivity extends AppCompatActivity implements LastNewsPresenter.LastNewsListener {

    private ActivityLastNewsBinding binding;

    private List<Article> data;
    private LastNewsAdapter adapter;

    private static final String TAG = "LastNewsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLastNewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initRecyclerView();

        LastNewsPresenter presenter = new LastNewsPresenter(this, this);
        presenter.loadLastNews();

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

    private void initRecyclerView() {
        data = new ArrayList<>();
        adapter = new LastNewsAdapter(this, data);
        binding.rvNewsTopics.setLayoutManager(new LinearLayoutManager(this));
        binding.rvNewsTopics.setHasFixedSize(true);
        binding.rvNewsTopics.setAdapter(adapter);
    }

    @Override
    public void getLastNewsTopics(List<Article> topicData) {
        binding.progressBar.setVisibility(View.GONE);
        data.addAll(topicData);
        adapter.notifyDataSetChanged();
    }

}