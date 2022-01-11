package com.example.notesapp.notesAppGUI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.example.notesapp.R;
import com.example.notesapp.noteController.TextNote;


public class TrashRecyclerViewAdapter extends RecyclerView.Adapter<TrashRecyclerViewAdapter.ViewHolder> {

    private ArrayList<TextNote> textNotes;
    private final Context context;


    public TrashRecyclerViewAdapter(Context context) {

        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.recycleview_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.getTextView().setText(textNotes.get(position).getShortText());
        holder.getDateView().setText(textNotes.get(position).getDate());
    }


    @Override
    public int getItemCount() {
        return textNotes.size();
    }

    public void setNotes(ArrayList<TextNote> textNotes) {
        this.textNotes = textNotes;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final TextView dateView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recyclerTextView);
            dateView = itemView.findViewById(R.id.dateView);
        }

        public TextView getDateView() {
            return dateView;
        }

        public TextView getTextView() {
            return textView;
        }
    }
}


