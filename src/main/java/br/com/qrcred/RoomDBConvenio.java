package br.com.qrcred;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Convenio_local.class},version = 4,exportSchema = false)
public abstract class RoomDBConvenio extends RoomDatabase {

    private static RoomDBConvenio database;
    private static final String DATABASE_NAME = "database";
    public synchronized static RoomDBConvenio getInstance(Context context){
        if(database == null){
            database = Room.databaseBuilder(context.getApplicationContext()
                    ,RoomDBConvenio.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
    public abstract convenio_localDao convenio_localDao();
}
