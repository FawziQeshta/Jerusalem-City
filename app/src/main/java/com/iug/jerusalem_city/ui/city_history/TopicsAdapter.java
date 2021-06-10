package com.iug.jerusalem_city.ui.city_history;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.iug.jerusalem_city.R;
import com.iug.jerusalem_city.data.room_database.RoomDB;
import com.iug.jerusalem_city.databinding.ItemTopicsBinding;
import com.iug.jerusalem_city.data.models.TopicModel;
import com.iug.jerusalem_city.ui.play_video.PlayVideoActivity;
import com.iug.jerusalem_city.ui.topic_details.TopicDetailsActivity;

import java.util.List;

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.TopicHolder> {

    private Context context;
    private List<TopicModel> data;
    private StorageReference storageRef;
    private RoomDB db;

    private static final String TAG = "TopicsAdapter";

    private final int MAX_LINES = 3;
    private final String TWO_SPACES = " ";

    public TopicsAdapter(Context context, List<TopicModel> data) {
        this.context = context;
        this.data = data;
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        db = RoomDB.getInstance(context);
    }

    @NonNull
    @Override
    public TopicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopicHolder(ItemTopicsBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TopicHolder holder, int position) {
        TopicModel topic = data.get(position);

        holder.binding.tvTitle.setText(topic.getText());

        if (context.getClass().getSimpleName().equals("FavoriteActivity")) {
            topic.setSaved(true);
        }

        StorageReference pathReference = storageRef.child(topic.getImageUrl());

        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context.getApplicationContext())
                        .load(uri)
                        .centerCrop()
                        .into(holder.binding.ivBackground);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.e(TAG, "onFailure: " + exception.getMessage());
            }
        });

        addReadMore(holder, topic.getText());

        if (topic.isHasVideo()) {
            holder.binding.ivPlayVideo.setVisibility(View.VISIBLE);

            holder.binding.ivPlayVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayVideoActivity.class);
                    intent.putExtra("videoUrl", topic.getVideoUrl());
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(0, 0);
                }
            });

        } else {
            holder.binding.ivPlayVideo.setVisibility(View.GONE);
        }

        if (topic.isSaved()) {
            holder.binding.customTopicSave.setImageResource(R.drawable.ic_save_white_dark);
        } else {
            holder.binding.customTopicSave.setImageResource(R.drawable.ic_save_white);
        }

        holder.binding.customTopicSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTopic(topic, holder.binding.customTopicSave, position);
            }
        });

        holder.binding.ivBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TopicDetailsActivity.class);
                intent.putExtra("topic", topic);
                context.startActivity(intent);
            }
        });

        holder.binding.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TopicDetailsActivity.class);
                intent.putExtra("topic", topic);
                context.startActivity(intent);
            }
        });

        if (position == 0) {
            holder.binding.view.setVisibility(View.VISIBLE);
        } else {
            holder.binding.view.setVisibility(View.GONE);
        }

    }

    private void saveTopic(TopicModel topic, ImageView imageSrc, int position) {
        if (topic.isSaved()) {
            db.daoAccess().deleteSpecificFavorites(topic.getId());
            imageSrc.setImageResource(R.drawable.ic_save_white);
            topic.setSaved(false);
            removeItem(position);
        } else {
            db.daoAccess().insertFavorites(topic);
            imageSrc.setImageResource(R.drawable.ic_save_white_dark);
            topic.setSaved(true);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class TopicHolder extends RecyclerView.ViewHolder {

        ItemTopicsBinding binding;

        public TopicHolder(@NonNull ItemTopicsBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

        }
    }

    private void removeItem(int position) {
        if (context.getClass().getSimpleName().equals("FavoriteActivity")) {
            data.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }
    }

    private void addReadMore(TopicHolder holder, String text) {
        holder.binding.tvTitle.post(new Runnable() {
            @Override
            public void run() {
                // Past the maximum number of lines we want to display.
                if (holder.binding.tvTitle.getLineCount() > MAX_LINES) {
                    int lastCharShown = holder.binding.tvTitle.getLayout().getLineVisibleEnd(MAX_LINES - 1);

                    holder.binding.tvTitle.setMaxLines(MAX_LINES);

                    String moreString = "إقرأ المزيد";
                    String suffix = TWO_SPACES + moreString;

                    String actionDisplayText = text.substring(0, lastCharShown - suffix.length()) + "..." + suffix;

                    SpannableString truncatedSpannableString = new SpannableString(actionDisplayText);
                    int startIndex = actionDisplayText.indexOf(moreString);
                    truncatedSpannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorReadMore)), startIndex, startIndex + moreString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    holder.binding.tvTitle.setText(truncatedSpannableString);
                }
            }
        });
    }

}
