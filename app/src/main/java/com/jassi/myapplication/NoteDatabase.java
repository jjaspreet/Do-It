package com.jassi.myapplication;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = Note.class, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context){
        if(instance ==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note-database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomcallback)
                    .build();
        }
        return  instance;
    }
    private static RoomDatabase.Callback roomcallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new Populatedbasynctask(instance).execute();
        }
    };
    private static class Populatedbasynctask extends AsyncTask<Void,Void , Void>{
        private NoteDao noteDao;
        private Populatedbasynctask(NoteDatabase db){
            noteDao= db.noteDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.inser(new Note("Title 1", "Description 1", 1));
            noteDao.inser(new Note("Title 2", "Description 2", 2));
            noteDao.inser(new Note("Title 3", "Description 3", 3));
            return null;
        }
    }
}
