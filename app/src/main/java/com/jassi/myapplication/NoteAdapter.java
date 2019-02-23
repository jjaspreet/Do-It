package com.jassi.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter <NoteAdapter.MyViewHolder> {
     private List<Note> notes = new ArrayList<>();
     private OnItemClickListner listner;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_item, viewGroup, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Note CurrentNote= notes.get(i);
        myViewHolder.Title.setText(CurrentNote.getTitle());
        myViewHolder.Description.setText(CurrentNote.getDescription());
        myViewHolder.Priority.setText(String.valueOf(CurrentNote.getPriority()));

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }
     public Note getNoteAt(int position){
        return notes.get(position);
    }

    public void setOnItemClickListner(OnItemClickListner listner){
        this.listner= listner;
    }

     class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView Title;
        private  TextView Description;
        private  TextView Priority;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.text_view_title);
            Description = itemView.findViewById(R.id.text_view_description);
            Priority= itemView.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listner != null && position != RecyclerView.NO_POSITION ){
                        listner.onItemClick(notes.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListner{
        void onItemClick( Note note);
    }
}
