package com.iug.jerusalem_city.ui.topic_details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.iug.jerusalem_city.R;
import com.iug.jerusalem_city.databinding.ActivityTopicDetailsBinding;
import com.iug.jerusalem_city.ui.play_video.PlayVideoActivity;

public class TopicDetailsActivity extends AppCompatActivity {

    private ActivityTopicDetailsBinding binding;
    private StorageReference storageRef;

    private static final String TAG = "TopicDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTopicDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        getDataIntent();

        binding.detailsToolbar.setNavigationIcon(R.drawable.ic_back);
        binding.detailsToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getDataIntent() {
        Intent intent = getIntent();
        String text = intent.getStringExtra("text");
        boolean isVideo = intent.getBooleanExtra("isVideo", false);
        String image = intent.getStringExtra("image");
        String videoUrl = intent.getStringExtra("videoUrl");

        binding.detailsTitleText.setText(text);

        StorageReference pathReference = storageRef.child(image);

        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext())
                        .load(uri)
                        .centerCrop()
                        .skipMemoryCache(true)
                        .into(binding.detailsImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.e(TAG, "onFailure: " + exception.getMessage());
            }
        });

        if (isVideo) {
            binding.ivPlayVideo.setVisibility(View.VISIBLE);
            binding.ivPlayVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), PlayVideoActivity.class);
                    intent.putExtra("videoUrl", videoUrl);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                }
            });
        }

    }
}