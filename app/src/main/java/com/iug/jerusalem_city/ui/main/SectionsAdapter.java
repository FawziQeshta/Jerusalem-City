package com.iug.jerusalem_city.ui.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iug.jerusalem_city.databinding.ItemSectionsBinding;
import com.iug.jerusalem_city.models.Section;
import com.iug.jerusalem_city.ui.city_climate.CityClimateActivity;
import com.iug.jerusalem_city.ui.city_history.CityHistoryActivity;
import com.iug.jerusalem_city.ui.city_information.CityInformationActivity;
import com.iug.jerusalem_city.ui.last_news.LastNewsActivity;
import com.iug.jerusalem_city.ui.settings.SettingsActivity;
import com.iug.jerusalem_city.ui.touristic_monuments.TouristicMonumentsActivity;

import java.util.List;

public class SectionsAdapter extends RecyclerView.Adapter<SectionsAdapter.SectionsHolder> {

    private Context context;
    private List<Section> data;

    public SectionsAdapter(Context context, List<Section> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public SectionsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SectionsAdapter.SectionsHolder(ItemSectionsBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SectionsHolder holder, int position) {
        Section section = data.get(position);

        holder.binding.sectionImage.setImageResource(section.getImage());

        holder.binding.sectionName.setText(section.getName());

        holder.binding.sectionContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sectionContainerClicked(section.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class SectionsHolder extends RecyclerView.ViewHolder {

        ItemSectionsBinding binding;

        public SectionsHolder(@NonNull ItemSectionsBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

        }
    }

    private void sectionContainerClicked(int id) {
        Intent intent;
        switch (id) {
            case 1:
                intent = new Intent(context, CityInformationActivity.class);
                context.startActivity(intent);
                break;

            case 2:
                intent = new Intent(context, CityHistoryActivity.class);
                context.startActivity(intent);
                break;

            case 3:
                intent = new Intent(context, CityClimateActivity.class);
                context.startActivity(intent);
                break;

            case 4:
                intent = new Intent(context, TouristicMonumentsActivity.class);
                context.startActivity(intent);
                break;

            case 5:
                intent = new Intent(context, LastNewsActivity.class);
                context.startActivity(intent);
                break;

            case 6:
                intent = new Intent(context, SettingsActivity.class);
                context.startActivity(intent);
                break;
        }
    }

}
