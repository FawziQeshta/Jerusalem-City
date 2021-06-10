package com.iug.jerusalem_city.ui.favorite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.iug.jerusalem_city.R;
import com.iug.jerusalem_city.data.models.TopicModel;
import com.iug.jerusalem_city.databinding.ActivityFavoriteBinding;
import com.iug.jerusalem_city.ui.city_history.TopicsAdapter;
import com.iug.jerusalem_city.utils.NavigationDrawerSetting;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity implements FavoritePresenter.FavoriteListener {

    private ActivityFavoriteBinding binding;

    private TopicsAdapter adapter;

    private static final String TAG = "FavoriteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FavoritePresenter presenter = new FavoritePresenter(this, this);
        presenter.loadFavorites();

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        NavigationDrawerSetting.setUpNavigationDrawer(TAG, this, binding.historyDrawerLayout, binding.favoriteNavigationView, binding.favoriteToolbar, binding.favoriteMenu);
        super.onStart();
    }

    private void initRecyclerView(List<TopicModel> favorites) {
        adapter = new TopicsAdapter(this, favorites);
        binding.rvFavoritesTopics.setLayoutManager(new LinearLayoutManager(this));
        binding.rvFavoritesTopics.setHasFixedSize(true);
        binding.rvFavoritesTopics.setAdapter(adapter);
    }

    @Override
    public void getAllFavorites(List<TopicModel> favorites) {
        binding.progressBar.setVisibility(View.GONE);
        if (favorites != null) {
            initRecyclerView(favorites);
        } else {
            binding.tvNotData.setVisibility(View.VISIBLE);
        }
    }

}