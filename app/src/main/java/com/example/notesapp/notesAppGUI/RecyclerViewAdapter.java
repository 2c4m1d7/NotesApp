package com.example.notesapp.notesAppGUI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.example.notesapp.R;
import com.example.notesapp.noteController.TextNote;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final ArrayList<TextNote> textNotes;
    private final Context context;




    public RecyclerViewAdapter(ArrayList<TextNote> textNotes, Context context) {
        this.textNotes = textNotes;
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

        holder.getTextView().setOnClickListener((v) -> {
            Intent intent = new Intent(v.getContext(), AddNote.class);

            intent.putExtra(MainMenu.EXTRA_MESSAGE_ID, String.valueOf(textNotes.get(position).getId()));
            context.startActivity(intent);
        });

    }


    @Override
    public int getItemCount() {
        return textNotes.size();
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
