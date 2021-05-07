package com.iug.jerusalem_city.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.iug.jerusalem_city.R;
import com.iug.jerusalem_city.databinding.ActivityMainBinding;
import com.iug.jerusalem_city.models.SectionModel;
import com.iug.jerusalem_city.utils.NavigationDrawerSetting;
import com.iug.jerusalem_city.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SectionsAdapter adapter;

    private List<SectionModel> data;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSections();

    }

    @Override
    protected void onStart() {
        NavigationDrawerSetting.setUpNavigationDrawer(TAG, this, binding.mainDrawerLayout, binding.mainNavigationView, binding.mainToolbar, binding.mainMenu);
        super.onStart();
    }

    private void getSections() {
        data = new ArrayList<>();
        data.add(new SectionModel(1, R.drawable.ic_info, "معلومات عن المدينة"));
        data.add(new SectionModel(2, R.drawable.ic_history, "تاريخ المدينة"));
        data.add(new SectionModel(3, R.drawable.ic_climant, "مناخ المدينة"));
        data.add(new SectionModel(4, R.drawable.ic_dome_of_the_rock, "أهم المعالم السياحية"));
        data.add(new SectionModel(5, R.drawable.ic_last_news, "اّخر الأخبار"));
        data.add(new SectionModel(6, R.drawable.ic_add_topic, "إضافة موضوع"));
        data.add(new SectionModel(7, R.drawable.ic_settings, "الإعدادت"));

        adapter = new SectionsAdapter(this, data);
        binding.contentMain.rvSections.setLayoutManager(new GridLayoutManager(this, 2));
        binding.contentMain.rvSections.setHasFixedSize(true);
        binding.contentMain.rvSections.addItemDecoration(new SpacesItemDecoration(10));
        binding.contentMain.rvSections.setAdapter(adapter);

    }

}