package br.com.qrcred;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Usuario_local.class},version = 3,exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    private static RoomDB database;
    private static final String DATABASE_NAME = "database";
    public synchronized static RoomDB getInstance(Context context){
        if(database == null){
            database = Room.databaseBuilder(context.getApplicationContext()
                    ,RoomDB.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract usuario_localDao usuario_localDao();
}
