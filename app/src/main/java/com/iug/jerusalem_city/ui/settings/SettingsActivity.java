package com.iug.jerusalem_city.ui.settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.iug.jerusalem_city.R;
import com.iug.jerusalem_city.databinding.ActivitySettingsBinding;
import com.iug.jerusalem_city.ui.main.MainActivity;
import com.iug.jerusalem_city.utils.Constants;

import static com.iug.jerusalem_city.utils.Constants.DARK_MODE_KEY;
import static com.iug.jerusalem_city.utils.Constants.NOTIFICATIONS_KEY;
import static com.iug.jerusalem_city.utils.Constants.PROGRESS_TEXT_SIZE_KEY;
import static com.iug.jerusalem_city.utils.Constants.SETTINGS_FILE_SHARED_NAME;
import static com.iug.jerusalem_city.utils.Constants.TEXT_SIZE_KEY;
import static com.iug.jerusalem_city.utils.Utilities.subscriptionNotifications;
import static com.iug.jerusalem_city.utils.Utilities.unSubscriptionNotifications;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static final String TAG = "SettingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        binding.settingsSeekBar.setProgress(sharedPreferences.getInt(PROGRESS_TEXT_SIZE_KEY, 0));

        binding.settingsSeekBar.setOnSeekBarChangeListener(mSeekBarListener);

        binding.darkModeSwitch.setOnCheckedChangeListener(mSwitchDarkModeListener);

        binding.notificationsSwitch.setOnCheckedChangeListener(mSwitchNotificationsListener);

        binding.settingToolbar.setNavigationIcon(R.drawable.ic_back);
        binding.settingToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void init() {
        sharedPreferences = getSharedPreferences(SETTINGS_FILE_SHARED_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean(DARK_MODE_KEY, false)) {
            binding.darkModeSwitch.setChecked(true);
        }

        if (sharedPreferences.getBoolean(NOTIFICATIONS_KEY, true)) {
            binding.notificationsSwitch.setChecked(true);
        }

    }

    private final SeekBar.OnSeekBarChangeListener mSeekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            editor.putInt(PROGRESS_TEXT_SIZE_KEY, progress);
            editor.apply();

            if (progress == 0) {
                editor.putInt(TEXT_SIZE_KEY, 18);
                editor.apply();

            } else if (progress == 1) {
                editor.putInt(TEXT_SIZE_KEY, 20);
                editor.apply();

            } else if (progress == 2) {
                editor.putInt(TEXT_SIZE_KEY, 24);
                editor.apply();
            }

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private final CompoundButton.OnCheckedChangeListener mSwitchDarkModeListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isChecked && !sharedPreferences.getBoolean(DARK_MODE_KEY, false)) {
                        nightMode();
                    } else {
                        lightMode();
                    }
                }
            }, 200);
        }
    };

    private final CompoundButton.OnCheckedChangeListener mSwitchNotificationsListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isChecked && !sharedPreferences.getBoolean(NOTIFICATIONS_KEY, true)) {
                        editor.putBoolean(NOTIFICATIONS_KEY, true);
                        subscriptionNotifications();
                    } else {
                        editor.putBoolean(NOTIFICATIONS_KEY, false);
                        unSubscriptionNotifications();
                    }
                    editor.apply();
                }
            }, 200);
        }
    };

    private void nightMode() {
        editor.putBoolean(DARK_MODE_KEY, true);
        editor.apply();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    private void lightMode() {
        editor.putBoolean(DARK_MODE_KEY, false);
        editor.apply();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

}