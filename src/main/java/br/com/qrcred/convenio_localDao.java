package br.com.qrcred;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface convenio_localDao {

    @Insert(onConflict = REPLACE)
    void insert(Convenio_local convenio_local);

    @Delete
    void delete(Convenio_local convenio_local);

    @Delete
    void reset(List<Convenio_local> convenio_local);

    @Query("UPDATE table_convenio SET logconv = :slogconv, nomeconvenio = :sNomeconvenio WHERE ID = :sIDC")
    void update(int sIDC,String slogconv,String sNomeconvenio);

    @Query("SELECT * FROM table_convenio")
    List<Convenio_local> getAll();
}
