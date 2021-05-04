package com.iug.jerusalem_city.ui.add_topic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.iug.jerusalem_city.R;
import com.iug.jerusalem_city.databinding.ActivityAddTopicBinding;

public class AddTopicActivity extends AppCompatActivity {

    private ActivityAddTopicBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTopicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.addTopicToolbar.setNavigationIcon(R.drawable.ic_back);
        binding.addTopicToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}