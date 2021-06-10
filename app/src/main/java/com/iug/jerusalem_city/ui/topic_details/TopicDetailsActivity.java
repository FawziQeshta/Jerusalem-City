package com.iug.jerusalem_city.ui.topic_details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.iug.jerusalem_city.R;
import com.iug.jerusalem_city.data.models.TopicModel;
import com.iug.jerusalem_city.data.room_database.RoomDB;
import com.iug.jerusalem_city.databinding.ActivityTopicDetailsBinding;
import com.iug.jerusalem_city.ui.play_video.PlayVideoActivity;
import com.iug.jerusalem_city.ui.settings.SettingsActivity;
import com.iug.jerusalem_city.utils.Constants;

public class TopicDetailsActivity extends AppCompatActivity {

    private ActivityTopicDetailsBinding binding;
    private StorageReference storageRef;

    private static final String TAG = "TopicDetailsActivity";

    private SharedPreferences spSettings;
    private RoomDB db;
    private int textSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTopicDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        getDataIntent();

        binding.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void init() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        db = RoomDB.getInstance(this);
    }

    private void getDataIntent() {
        Intent intent = getIntent();
        TopicModel topic = (TopicModel) intent.getSerializableExtra("topic");
        String text = topic.getText();
        boolean isVideo = topic.isHasVideo();
        String image = topic.getImageUrl();
        String videoUrl = topic.getVideoUrl();

        binding.detailsTitleText.setText(text);

        if (topic.isSaved()) {
            binding.saveIcon.setImageResource(R.drawable.ic_save_white_dark);
        }

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

        binding.containerSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTopic(topic);
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
                    overridePendingTransition(0, 0);
                }
            });
        }

    }

    private void saveTopic(TopicModel topic) {
        if (topic.isSaved()) {
            db.daoAccess().deleteSpecificFavorites(topic.getId());
            binding.saveIcon.setImageResource(R.drawable.ic_save_white);
            topic.setSaved(false);
            Toast.makeText(this, "تم ازالة الموضوع في المفضله بنجاح", Toast.LENGTH_SHORT).show();
        } else {
            db.daoAccess().insertFavorites(topic);
            binding.saveIcon.setImageResource(R.drawable.ic_save_white_dark);
            topic.setSaved(true);
            Toast.makeText(this, "تم حفظ الموضوع في المفضله بنجاح", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        spSettings = getSharedPreferences(Constants.SETTINGS_FILE_SHARED_NAME, MODE_PRIVATE);
        textSize = spSettings.getInt(Constants.TEXT_SIZE_KEY, 18);
        binding.detailsTitleText.setTextSize(textSize);
        super.onStart();
    }

}