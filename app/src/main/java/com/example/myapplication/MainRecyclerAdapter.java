package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Designed and Developed by Mohammad suhail ahmed on 26/02/2020
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {
    private Context context;
    private List<WinnerDetails> winnerDetails;

    public MainRecyclerAdapter(List<WinnerDetails> winnerDetails,Context context)
    {
        this.winnerDetails = winnerDetails;
        this.context = context;
    }


    public class MainViewHolder extends RecyclerView.ViewHolder
    {
        private TextView heading;
        private RecyclerView subRecycler;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.maintitle);
            subRecycler = itemView.findViewById(R.id.subrecycler);
        }
    }
    @NonNull
    @Override
    public MainRecyclerAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recycler_item,parent,false);
        return new MainRecyclerAdapter.MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerAdapter.MainViewHolder holder, int position) {
        WinnerDetails wd = winnerDetails.get(position);
        holder.heading.setText("Noble Prize Winner's in "+wd.getCategory()+" in "+wd.getYear());

        SubRecyclerAdapter subRecyclerAdapter = new SubRecyclerAdapter(wd.getPersonDetails());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        holder.subRecycler.setLayoutManager(layoutManager);
        holder.subRecycler.setAdapter(subRecyclerAdapter);


    }

    @Override
    public int getItemCount() {
        return winnerDetails.size();
    }
    public void setWinnerDetails(List<WinnerDetails> winnerDetails)
    {
        this.winnerDetails = winnerDetails;
        notifyDataSetChanged();
    }
}
