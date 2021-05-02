package com.iug.jerusalem_city.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.iug.jerusalem_city.R;
import com.iug.jerusalem_city.databinding.ActivityMainBinding;
import com.iug.jerusalem_city.models.Intro;
import com.iug.jerusalem_city.utils.NavigationDrawerSetting;
import com.iug.jerusalem_city.utils.Utilities;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private StorageReference storageRef;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        Intro intro = new Intro("images/2018-1091x520-c.jpg"
                ,"تعتبر مدينة القدس مدينة مقدسة لدى اتباع الديانات الإبراهيمية الرئيسية وهي الإسلام واليهودية والمسيحية وهي أكبر مدن فلسطين التاريخية المحتلة مساحة وسكان.\n" +
                "\n" +
                "وتعتبر القدس ذات أهمية دينية واقتصادية وعرفت بعدة أسماء منها بيت المقدس والقدس الشريف وأولى القبلتين وغيرها.",
                "images/2018-1091x520-c.jpg"
                ,"إن عدد سكان القدس وفق الجهاز المركزي للإحصاء الفلسطيني يبلغ 435 ألف نسمة وهذه الإحصائية لعام 2017م.\n" +
                "\n" +
                "وغالبية سكان القدس هم من فئة الشباب دون سن 29 سنة، وتشمل هذه الإحصائية المناطق التي فصلها الجدار العازل عن القدس.\n" +
                "\n" +
                "ويوجد في القدس بشكل عام حوالي 95 ألف عائلة بمعدل 4،5 أطفال لكل عائلة وسطياً.\n" +
                "\n" +
                "ولا توجد في الوقت الراهن إحصائية توضع عدد سكان القدس عام 2020م.");

        binding.introTitleText.setText(intro.getTitleText());

//        storageRef.child(intro.getTitleImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Glide.with(getApplicationContext())
//                        .load(uri)
//                        .centerCrop()
//                        .skipMemoryCache(true)
//                        .into(binding.introTitleImage);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle any errors
//                Log.e(TAG, "onFailure: " + exception.getMessage());
//            }
//        });

        binding.introSubTitleText.setText(intro.getSubTitleText());

//        storageRef.child(intro.getSubTitleImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Glide.with(getApplicationContext())
//                        .load(uri)
//                        .centerCrop()
//                        .skipMemoryCache(true)
//                        .into(binding.introSubTitleImage);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle any errors
//                Log.e(TAG, "onFailure: " + exception.getMessage());
//            }
//        });

    }

    @Override
    protected void onStart() {
        NavigationDrawerSetting.setUpNavigationDrawer(TAG, this, binding.mainDrawerLayout, binding.mainNavigationView, binding.mainToolbar, binding.mainMenu);
        super.onStart();
    }
}