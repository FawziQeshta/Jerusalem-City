package com.iug.jerusalem_city.ui.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.iug.jerusalem_city.R;
import com.iug.jerusalem_city.databinding.ActivitySettingsBinding;

import static com.iug.jerusalem_city.utils.Constants.DARK_MODE_KEY;
import static com.iug.jerusalem_city.utils.Constants.PROGRESS_TEXT_SIZE_KEY;
import static com.iug.jerusalem_city.utils.Constants.SETTINGS_FILE_SHARED_NAME;
import static com.iug.jerusalem_city.utils.Constants.TEXT_SIZE_KEY;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        binding.settingsSeekBar.setProgress(sharedPreferences.getInt(PROGRESS_TEXT_SIZE_KEY, 1));

        binding.settingsSeekBar.setOnSeekBarChangeListener(mSeekBarListener);

        binding.settingsSwitch.setOnCheckedChangeListener(mSwitchDarkModeListener);

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
            binding.settingsSwitch.setChecked(true);
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
                        editor.putBoolean(DARK_MODE_KEY, true);
                    } else {
                        lightMode();
                        editor.putBoolean(DARK_MODE_KEY, false);
                    }
                    editor.apply();
                }
            }, 200);
        }
    };

    private void nightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    private void lightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

}