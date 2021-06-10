package com.iug.jerusalem_city.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.iug.jerusalem_city.R;
import com.iug.jerusalem_city.databinding.ActivityMainBinding;
import com.iug.jerusalem_city.data.models.SectionModel;
import com.iug.jerusalem_city.utils.NavigationDrawerSetting;
import com.iug.jerusalem_city.utils.SpacesItemDecoration;
import com.iug.jerusalem_city.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import static com.iug.jerusalem_city.utils.Constants.NOTIFICATIONS_KEY;
import static com.iug.jerusalem_city.utils.Constants.SETTINGS_FILE_SHARED_NAME;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SectionsAdapter adapter;

    private List<SectionModel> data;

    private SharedPreferences sharedPreferences;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSections();

        checkSubscriptionNotifications();

    }

    @Override
    protected void onStart() {
        NavigationDrawerSetting.setUpNavigationDrawer(TAG, this, binding.mainDrawerLayout, binding.mainNavigationView, binding.mainToolbar, binding.mainMenu);
        super.onStart();
    }

    private void getSections() {
        sharedPreferences = getSharedPreferences(SETTINGS_FILE_SHARED_NAME, MODE_PRIVATE);

        data = new ArrayList<>();
        data.add(new SectionModel(1, R.drawable.ic_info, "معلومات عن المدينة"));
        data.add(new SectionModel(2, R.drawable.ic_history, "تاريخ المدينة"));
        data.add(new SectionModel(3, R.drawable.ic_climate, "مناخ المدينة"));
        data.add(new SectionModel(4, R.drawable.ic_dome_of_the_rock, "أهم المعالم السياحية"));
        data.add(new SectionModel(5, R.drawable.ic_last_news, "اّخر الأخبار"));
        data.add(new SectionModel(6, R.drawable.ic_add_topic, "إضافة موضوع"));
        data.add(new SectionModel(7, R.drawable.ic_save_menu, "المفضله"));
        data.add(new SectionModel(8, R.drawable.ic_settings, "الإعدادت"));

        adapter = new SectionsAdapter(this, data);
        binding.contentMain.rvSections.setLayoutManager(new GridLayoutManager(this, 2));
        binding.contentMain.rvSections.setHasFixedSize(true);
        binding.contentMain.rvSections.addItemDecoration(new SpacesItemDecoration(10));
        binding.contentMain.rvSections.setAdapter(adapter);

    }

    private void checkSubscriptionNotifications() {
        if (sharedPreferences.getBoolean(NOTIFICATIONS_KEY, true)) {
            Utilities.subscriptionNotifications();
        } else {
            Utilities.unSubscriptionNotifications();
        }
    }

}