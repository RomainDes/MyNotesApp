package com.example.exercice3_notes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<String> nameData;
    private List<String> bodyData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    private MaterialCardView colorRow;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, List<String> nameData, List<String> bodyData) {
        this.mInflater = LayoutInflater.from(context);
        this.nameData = nameData;
        this.bodyData = bodyData;


    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(MainActivity.deletingNotes){
            view = mInflater.inflate(R.layout.custom_notes_row_delete, parent, false);
        }
        else{
            view = mInflater.inflate(R.layout.custom_notes_row, parent, false);

        }

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = nameData.get(position);
        String body = bodyData.get(position);
        holder.myTextView.setText(name);
        holder.myTextView2.setText(body);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return nameData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        TextView myTextView2;


        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.nameTV);
            myTextView2 = itemView.findViewById(R.id.bodyTV);
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return nameData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }




    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
