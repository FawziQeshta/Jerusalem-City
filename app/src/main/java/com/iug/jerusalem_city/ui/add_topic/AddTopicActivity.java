package com.iug.jerusalem_city.ui.add_topic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.iug.jerusalem_city.R;
import com.iug.jerusalem_city.databinding.ActivityAddTopicBinding;
import com.iug.jerusalem_city.utils.Constants;
import com.iug.jerusalem_city.utils.Utilities;

public class AddTopicActivity extends AppCompatActivity {

    private ActivityAddTopicBinding binding;

    private final int IMAGE_REC_CODE = 200;

    private Uri filePath = null;

    private AddTopicPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTopicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgOpenGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasPermissionsGranted()) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select image"), IMAGE_REC_CODE);
                }
            }
        });

        presenter = new AddTopicPresenter(this);

        binding.btnAddTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utilities.checkInternetConnected(AddTopicActivity.this)) {
                    checkInputs();
                } else {
                    Toast.makeText(AddTopicActivity.this, "الرجاء فحص الإتصال بالإنترنت", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.addTopicToolbar.setNavigationIcon(R.drawable.ic_back);
        binding.addTopicToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void checkInputs() {
        if (binding.etNewTopic.getText().toString() == null) {
            Toast.makeText(AddTopicActivity.this, "الرجاء ادخال نص الموضوع.", Toast.LENGTH_SHORT).show();
        } else if (getSectionTitle() == null) {
            Toast.makeText(AddTopicActivity.this, "الرجاء اختيار صنف للموضوع.", Toast.LENGTH_SHORT).show();
        } else if (filePath == null) {
            Toast.makeText(AddTopicActivity.this, "الرجاء اضافة صورة للموضوع.", Toast.LENGTH_SHORT).show();
        } else {
            if (!binding.etNewTopic.getText().toString().trim().isEmpty()) {
                presenter.sendUserTopic(binding.etNewTopic.getText().toString().trim(), getSectionTitle(), filePath);
            } else {
                Toast.makeText(AddTopicActivity.this, "الرجاء ادخال نص الموضوع.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean hasPermissionsGranted() {
        int writePermission = ContextCompat.checkSelfPermission(AddTopicActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            String[] params = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(AddTopicActivity.this, params, IMAGE_REC_CODE);
            return false;

        } else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REC_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            binding.imgOpenGallery.setImageURI(filePath);
        }
    }

    private String getSectionTitle() {
        if (binding.radioButtonClimate.isChecked()) {
            return binding.radioButtonClimate.getText().toString();
        } else if (binding.radioButtonHistory.isChecked()) {
            return binding.radioButtonHistory.getText().toString();
        } else if (binding.radioButtonTouristicMonuments.isChecked()) {
            return binding.radioButtonTouristicMonuments.getText().toString();
        }
        return null;
    }

}