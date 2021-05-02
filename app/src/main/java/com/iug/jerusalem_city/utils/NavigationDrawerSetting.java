package com.iug.jerusalem_city.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.iug.jerusalem_city.R;
import com.iug.jerusalem_city.ui.city_history.HistoryCityActivity;
import com.iug.jerusalem_city.ui.city_information.CityInformationActivity;
import com.iug.jerusalem_city.ui.main.MainActivity;
import com.iug.jerusalem_city.ui.settings.SettingsActivity;

public class NavigationDrawerSetting {

    private static DrawerLayout mDrawerLayout;

    public static NavigationView setUpNavigationDrawer(final String hostScreen, Activity activity,
                                                       DrawerLayout drawerLayout, NavigationView navigationView,
                                                       Toolbar toolbar, LinearLayout ivContainer) {

        mDrawerLayout = drawerLayout;
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_drawer_home_icon:
                        if (!hostScreen.equals("MainActivity")) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intentSettings = new Intent(activity.getBaseContext(), MainActivity.class);
                                    activity.startActivity(intentSettings);
                                    activity.finish();
                                }
                            }, 280);
                        }
                        break;

                    case R.id.menu_drawer_info_of_city_icon:
                        if (!hostScreen.equals("CityInformationActivity")) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intentSettings = new Intent(activity.getBaseContext(), CityInformationActivity.class);
                                    activity.startActivity(intentSettings);
                                }
                            }, 280);
                        }
                        break;

                    case R.id.menu_drawer_history_city_icon:
                        if (!hostScreen.equals("HistoryCityActivity")) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intentSettings = new Intent(activity.getBaseContext(), HistoryCityActivity.class);
                                    activity.startActivity(intentSettings);
                                }
                            }, 280);
                        }
                        break;

                    case R.id.menu_drawer_settings_icon:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intentSettings = new Intent(activity.getBaseContext(), SettingsActivity.class);
                                activity.startActivity(intentSettings);
                            }
                        }, 280);
                        break;
                }

                NavigationDrawerSetting.closeMenu();

                return false;
            }
        });

        toolbar.setNavigationIcon(null);
        ivContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenu();
            }
        });

        return navigationView;
    }

    public static DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    @SuppressLint("WrongConstant")
    public static void openMenu() {
        NavigationDrawerSetting.getDrawerLayout().openDrawer(Gravity.START);
    }

    @SuppressLint("WrongConstant")
    public static void closeMenu() {
        NavigationDrawerSetting.getDrawerLayout().closeDrawer(Gravity.START);
    }

}
