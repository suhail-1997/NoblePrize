package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Designed and Developed by Mohammad suhail ahmed on 26/02/2020
 */
public class SubRecyclerAdapter extends RecyclerView.Adapter<SubRecyclerAdapter.SubViewHolder> {

    private List<PersonDetails> personDetails;

    public SubRecyclerAdapter(List<PersonDetails> personDetails)
    {
        this.personDetails = personDetails;
    }

    public class SubViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView motivation;

        public SubViewHolder(@NonNull View itemView) {
            super(itemView);
            name =itemView.findViewById(R.id.winnername);
            motivation = itemView.findViewById(R.id.motivation);
        }
    }
    @NonNull
    @Override
    public SubRecyclerAdapter.SubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_recycler_item,parent,false);
        return new SubRecyclerAdapter.SubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubRecyclerAdapter.SubViewHolder holder, int position) {
        PersonDetails personDetails = this.personDetails.get(position);
        holder.name.setText(personDetails.getFirstName()+" "+personDetails.getSurName());
        holder.motivation.setText(personDetails.getMotivation());

    }

    @Override
    public int getItemCount() {
        return personDetails.size();
    }
}
