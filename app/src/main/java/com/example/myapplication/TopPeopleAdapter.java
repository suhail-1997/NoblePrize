package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Designed and Developed by Mohammad suhail ahmed on 27/02/2020
 */
public class TopPeopleAdapter extends RecyclerView.Adapter<TopPeopleAdapter.TopViewHolder> {
    List<TopPeopleItem> topPeopleItems;

    public class TopViewHolder extends RecyclerView.ViewHolder
    {
        private TextView name,years;

        public TopViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.personname);
            years = itemView.findViewById(R.id.years);
        }
    }

    public TopPeopleAdapter(List<TopPeopleItem> topPeopleItems)
    {
        this.topPeopleItems = topPeopleItems;
    }

    @NonNull
    @Override
    public TopPeopleAdapter.TopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_person_item,parent,false);
        return new TopPeopleAdapter.TopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopPeopleAdapter.TopViewHolder holder, int position) {
        TopPeopleItem topPeopleItem = topPeopleItems.get(position);
        holder.name.setText(topPeopleItem.getName());
        holder.years.setText(topPeopleItem.getYears());

    }

    @Override
    public int getItemCount() {
        return topPeopleItems.size();
    }
    public void setTopPeopleItems(List<TopPeopleItem> topPeopleItems)
    {
        this.topPeopleItems = topPeopleItems;
        notifyDataSetChanged();
    }
}
