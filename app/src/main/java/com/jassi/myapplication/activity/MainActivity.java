package com.jassi.myapplication.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jassi.myapplication.Note;
import com.jassi.myapplication.NoteAdapter;
import com.jassi.myapplication.NoteViewModel;
import com.jassi.myapplication.R;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnItemClickListner {
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton buttonAddNote = findViewById(R.id.button_add);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, AddingEditNoteActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        RecyclerView recyclerview = findViewById(R.id.recycler_view);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        final NoteAdapter adapter = new NoteAdapter();
        recyclerview.setAdapter(adapter);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter.setNotes(notes);

            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
             noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "DELETED", Toast.LENGTH_SHORT).show();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerview);
        adapter.setOnItemClickListner(this);
    }

    @Override
    public void onItemClick(Note note) {
        Intent intent = new Intent(MainActivity.this, AddingEditNoteActivity.class);
        intent.putExtra(AddingEditNoteActivity.EXTRA_ID, note.getId());
        intent.putExtra(AddingEditNoteActivity.EXTRA_TITLE, note.getTitle());
        intent.putExtra(AddingEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
        intent.putExtra(AddingEditNoteActivity.EXTRA_PRIORITY, note.getPriority());
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode== RESULT_OK){
            String title = data.getStringExtra(AddingEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddingEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddingEditNoteActivity.EXTRA_PRIORITY, 1);

            Note note = new Note(title, description,priority);
            noteViewModel.insert(note);

            Toast.makeText(this, "NOTE SAVED", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "NOTE NOT SAVED", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuinflator= getMenuInflater();
        menuinflator.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_notes:
                noteViewModel.DeleteAll();
                Toast.makeText(this, "All Notes Deleted ", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
