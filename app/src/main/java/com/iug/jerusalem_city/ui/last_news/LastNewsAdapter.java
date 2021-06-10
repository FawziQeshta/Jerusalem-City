package com.iug.jerusalem_city.ui.last_news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iug.jerusalem_city.databinding.ItemTopicsApiLastNewsBinding;
import com.iug.jerusalem_city.data.models.Article;
import com.iug.jerusalem_city.data.models.LastNewsModel;
import com.iug.jerusalem_city.data.models.TopicModel;
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
