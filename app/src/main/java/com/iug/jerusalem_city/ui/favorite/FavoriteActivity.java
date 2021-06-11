package com.iug.jerusalem_city.ui.favorite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.iug.jerusalem_city.R;
import com.iug.jerusalem_city.data.models.TopicModel;
import com.iug.jerusalem_city.databinding.ActivityFavoriteBinding;
import com.iug.jerusalem_city.ui.city_history.TopicsAdapter;
import com.iug.jerusalem_city.utils.NavigationDrawerSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FavoriteActivity extends AppCompatActivity implements FavoritePresenter.FavoriteListener {

    private ActivityFavoriteBinding binding;

    private List<TopicModel> favorites;
    private TopicsAdapter adapter;

    private FavoritePresenter presenter;

    private static final String TAG = "FavoriteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initRecyclerView();

        presenter = new FavoritePresenter(this, this);
        presenter.loadFavorites();

        refreshData();

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

    private void initRecyclerView() {
        favorites = new ArrayList<>();
        adapter = new TopicsAdapter(this, favorites);
        binding.rvFavoritesTopics.setLayoutManager(new LinearLayoutManager(this));
        binding.rvFavoritesTopics.setHasFixedSize(true);
        binding.rvFavoritesTopics.setAdapter(adapter);
    }

    private void refreshData() {
        binding.swipeContainer.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorSeekBar));
        binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        favorites.clear();
                        adapter.notifyDataSetChanged();

                        presenter.loadFavorites();

                        binding.swipeContainer.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void getAllFavorites(List<TopicModel> favorites) {
        binding.progressBar.setVisibility(View.GONE);
        if (favorites != null) {
            this.favorites.addAll(favorites);
        } else {
            binding.tvNotData.setVisibility(View.VISIBLE);
        }
    }

}