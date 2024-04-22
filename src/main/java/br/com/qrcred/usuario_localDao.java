package br.com.qrcred;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface usuario_localDao {

    @Insert(onConflict = REPLACE)
    void insert(Usuario_local usuario_local);

    @Delete
    void delete(Usuario_local usuario_local);

    @Delete
    void reset(List<Usuario_local> usuario_local);

    @Query("UPDATE table_usuario SET cartao = :sCartao, nomeassociado = :sNome, mescorrente = :sMescorrente WHERE ID = :sID")
    void update(int sID,String sCartao,String sNome,String sMescorrente);

    @Query("SELECT * FROM table_usuario")
    List<Usuario_local> getAll();

    @Query("SELECT * FROM table_usuario WHERE cartao = :sCartao AND nomeassociado = :sNome")
    List<Usuario_local> getUser(String sCartao,String sNome);
}
