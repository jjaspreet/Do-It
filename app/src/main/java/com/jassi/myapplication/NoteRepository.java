package com.jassi.myapplication;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.os.AsyncTask;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();

    }

    public void insert(Note note) {
        new InsertNoteasyncTask(noteDao).execute(note);
    }

    public void update(Note note) {
       new UpdateNoteasyncTask(noteDao).execute(note);
    }

    public void delete(Note note) {
     new DeleteNoteasyncTask(noteDao).execute(note);
    }
    public void deleteAllNotes(){
        new DeleteAllNotesasyncTask(noteDao).execute();

    }
    public LiveData<List<Note>> getAllNotes(){
        return  allNotes;
    }

    private static class InsertNoteasyncTask extends AsyncTask<Note, Void, Void>{
     private NoteDao noteDao;
      private InsertNoteasyncTask (NoteDao noteDao){
          this.noteDao = noteDao;
      }

        @Override
        protected Void doInBackground(Note... notes) {
          noteDao.inser(notes[0]);
            return null;
        }
    }
    private static class UpdateNoteasyncTask extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao;
        private UpdateNoteasyncTask (NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }
    private static class DeleteNoteasyncTask extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao;
        private DeleteNoteasyncTask (NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }
    private static class DeleteAllNotesasyncTask extends AsyncTask<Void, Void, Void>{
        private NoteDao noteDao;
        private DeleteAllNotesasyncTask (NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}