package com.iug.jerusalem_city.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class Utilities {

    public static boolean checkInternetConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static void openUrl(Context context, String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;
        Intent intent = new Intent();
        Uri uri = Uri.parse(url);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);
        context.startActivity(intent);
    }

    public static void subscriptionNotifications() {
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.NOTIFICATIONS_TOPIC)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "SuccessSubscribedNotifications";
                        if (!task.isSuccessful()) {
                            msg = "FailureSubscribedNotifications";
                        }
                        Log.d("subscription", "onComplete: " + msg);
                    }
                });
    }

    public static void unSubscriptionNotifications() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.NOTIFICATIONS_TOPIC)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "SuccessUnSubscribedNotifications";
                        if (!task.isSuccessful()) {
                            msg = "FailureSubscribedNotifications";
                        }
                        Log.d("unSubscription", "onComplete: " + msg);
                    }
                });
    }

}
