package com.iug.jerusalem_city.ui.add_topic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.iug.jerusalem_city.utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class AddTopicPresenter {

    private Activity context;

    private FirebaseFirestore db;
    private StorageReference storageRef;
    private ProgressDialog progressDialog;

    private String imgStoragePath;
    private String videoStoragePath;
    private boolean hasVideo = false;

    private static final String TAG = "AddTopicPresenter";

    public AddTopicPresenter(Activity context) {
        this.context = context;
        db = FirebaseFirestore.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        dialogInit();
    }

    private void dialogInit() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("جار إضافة الموضوع...");
    }

    public void sendUserTopic(String textTopic, String choiceText, Uri imageUriPath, Uri videoUriPath) {
        progressDialog.show();

        imgStoragePath = "images/" + System.currentTimeMillis() + ".jpg";

        if (videoUriPath != null) {
            videoStoragePath = "videos/" + System.currentTimeMillis() + ".mp4";
            hasVideo = true;
            uploadVideoToStorage(textTopic, choiceText, videoUriPath);
        } else {
            videoStoragePath = "";
            hasVideo = false;
        }

        uploadImageToStorage(textTopic, choiceText, imageUriPath, videoUriPath);

    }

    private void uploadVideoToStorage(String textTopic, String choiceText, Uri videoUriPath) {
        StorageReference addNewVideosRef = storageRef.child(videoStoragePath);
        addNewVideosRef.putFile(videoUriPath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(context, "حدث خطأ أثناء حفظ الفيديو", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onComplete: " + task.getException());
                    return;
                }
                Log.d(TAG, "Uploaded Video");
                uploadUserTopic(textTopic, choiceText);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(context, "حدث خطأ أثناء حفظ الفيديو", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + e.getMessage());
            }
        });
    }

    private void uploadImageToStorage(String textTopic, String choiceText, Uri imageUriPath, Uri videoUriPath) {
        StorageReference addNewImagesRef = storageRef.child(imgStoragePath);
        addNewImagesRef.putFile(imageUriPath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(context, "حدث خطأ أثناء حفظ الصورة", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onComplete: " + task.getException());
                    return;
                }
                Log.d(TAG, "Uploaded Image");
                if (videoUriPath == null) {
                    uploadUserTopic(textTopic, choiceText);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(context, "حدث خطأ أثناء حفظ الصورة", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + e.getMessage());
            }
        });
    }

    private void uploadUserTopic(String textTopic, String choiceText) {
        Map<String, Object> map = new HashMap<>();
        map.put("text", textTopic);
        map.put("imageUrl", imgStoragePath);
        map.put("videoUrl", videoStoragePath);
        map.put("hasVideo", hasVideo);

        DocumentReference bulletinRef = db.collection(Constants.KEY_MAIN_COLLECTION).document(Constants.KEY_MAIN_COLLECTION_ID);

        String collectionName = getCollectionUserChoosed(choiceText);

        if (collectionName != null) {
            bulletinRef.collection(collectionName)
                    .add(map)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "تم اضافة الموضوع بنجاح", Toast.LENGTH_SHORT).show();
                            context.finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "حدث خطأ أثناء حفظ الموضوع", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private String getCollectionUserChoosed(String text) {
        switch (text) {
            case "تاريخ المدينة":
                return Constants.KEY_HISTORY_COLLECTION;

            case "مناخ المدينة":
                return Constants.KEY_CLIMATE_COLLECTION;

            case "أهم المعالم السياحية":
                return Constants.KEY_TOURISTICAL_COLLECTION;

            default:
                return null;
        }
    }

}
