package com.iug.jerusalem_city.ui.last_news;

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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.iug.jerusalem_city.R;
import com.iug.jerusalem_city.databinding.ItemTopicsApiLastNewsBinding;
import com.iug.jerusalem_city.databinding.ItemTopicsBinding;
import com.iug.jerusalem_city.models.Article;
import com.iug.jerusalem_city.models.LastNewsModel;
import com.iug.jerusalem_city.models.TopicModel;
import com.iug.jerusalem_city.ui.play_video.PlayVideoActivity;
import com.iug.jerusalem_city.ui.topic_details.TopicDetailsActivity;
import com.iug.jerusalem_city.utils.Utilities;

import java.util.List;

public class LastNewsAdapter extends RecyclerView.Adapter<LastNewsAdapter.TopicHolder> {

    private Context context;
    private List<Article> data;

    private static final String TAG = "LastNewsAdapter";

    public LastNewsAdapter(Context context, List<Article> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public TopicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopicHolder(ItemTopicsApiLastNewsBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TopicHolder holder, int position) {
        Article article = data.get(position);

        holder.binding.tvTitle.setText(article.getTitle());
        holder.binding.tvDescription.setText(article.getDescription());

        Glide.with(context.getApplicationContext())
                .load(article.getUrlToImage())
                .centerCrop()
                .into(holder.binding.ivBackground);

        if (position == 0) {
            holder.binding.view.setVisibility(View.VISIBLE);
        } else {
            holder.binding.view.setVisibility(View.GONE);
        }

        holder.binding.ivBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.openUrl(context, article.getUrl());
            }
        });

        holder.binding.btnReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.openUrl(context, article.getUrl());
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class TopicHolder extends RecyclerView.ViewHolder {
        ItemTopicsApiLastNewsBinding binding;
        public TopicHolder(@NonNull ItemTopicsApiLastNewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
