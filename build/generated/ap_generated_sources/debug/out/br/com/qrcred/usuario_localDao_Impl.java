package br.com.qrcred;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class usuario_localDao_Impl implements usuario_localDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Usuario_local> __insertionAdapterOfUsuario_local;

  private final EntityDeletionOrUpdateAdapter<Usuario_local> __deletionAdapterOfUsuario_local;

  private final SharedSQLiteStatement __preparedStmtOfUpdate;

  public usuario_localDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUsuario_local = new EntityInsertionAdapter<Usuario_local>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `table_usuario` (`ID`,`cartao`,`nomeassociado`,`mescorrente`,`senha_assoc`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Usuario_local entity) {
        statement.bindLong(1, entity.getID());
        if (entity.getCartao() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getCartao());
        }
        if (entity.getNomeassociado() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getNomeassociado());
        }
        if (entity.getMescorrente() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getMescorrente());
        }
        if (entity.getSenhaassoc() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getSenhaassoc());
        }
      }
    };
    this.__deletionAdapterOfUsuario_local = new EntityDeletionOrUpdateAdapter<Usuario_local>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `table_usuario` WHERE `ID` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Usuario_local entity) {
        statement.bindLong(1, entity.getID());
      }
    };
    this.__preparedStmtOfUpdate = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE table_usuario SET cartao = ?, nomeassociado = ?, mescorrente = ? WHERE ID = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Usuario_local usuario_local) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfUsuario_local.insert(usuario_local);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Usuario_local usuario_local) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfUsuario_local.handle(usuario_local);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void reset(final List<Usuario_local> usuario_local) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfUsuario_local.handleMultiple(usuario_local);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final int sID, final String sCartao, final String sNome,
      final String sMescorrente) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdate.acquire();
    int _argIndex = 1;
    if (sCartao == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, sCartao);
    }
    _argIndex = 2;
    if (sNome == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, sNome);
    }
    _argIndex = 3;
    if (sMescorrente == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, sMescorrente);
    }
    _argIndex = 4;
    _stmt.bindLong(_argIndex, sID);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfUpdate.release(_stmt);
    }
  }

  @Override
  public List<Usuario_local> getAll() {
    final String _sql = "SELECT * FROM table_usuario";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfID = CursorUtil.getColumnIndexOrThrow(_cursor, "ID");
      final int _cursorIndexOfCartao = CursorUtil.getColumnIndexOrThrow(_cursor, "cartao");
      final int _cursorIndexOfNomeassociado = CursorUtil.getColumnIndexOrThrow(_cursor, "nomeassociado");
      final int _cursorIndexOfMescorrente = CursorUtil.getColumnIndexOrThrow(_cursor, "mescorrente");
      final int _cursorIndexOfSenhaassoc = CursorUtil.getColumnIndexOrThrow(_cursor, "senha_assoc");
      final List<Usuario_local> _result = new ArrayList<Usuario_local>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Usuario_local _item;
        _item = new Usuario_local();
        final int _tmpID;
        _tmpID = _cursor.getInt(_cursorIndexOfID);
        _item.setID(_tmpID);
        final String _tmpCartao;
        if (_cursor.isNull(_cursorIndexOfCartao)) {
          _tmpCartao = null;
        } else {
          _tmpCartao = _cursor.getString(_cursorIndexOfCartao);
        }
        _item.setCartao(_tmpCartao);
        final String _tmpNomeassociado;
        if (_cursor.isNull(_cursorIndexOfNomeassociado)) {
          _tmpNomeassociado = null;
        } else {
          _tmpNomeassociado = _cursor.getString(_cursorIndexOfNomeassociado);
        }
        _item.setNomeassociado(_tmpNomeassociado);
        final String _tmpMescorrente;
        if (_cursor.isNull(_cursorIndexOfMescorrente)) {
          _tmpMescorrente = null;
        } else {
          _tmpMescorrente = _cursor.getString(_cursorIndexOfMescorrente);
        }
        _item.setMescorrente(_tmpMescorrente);
        final String _tmpSenhaassoc;
        if (_cursor.isNull(_cursorIndexOfSenhaassoc)) {
          _tmpSenhaassoc = null;
        } else {
          _tmpSenhaassoc = _cursor.getString(_cursorIndexOfSenhaassoc);
        }
        _item.setSenhaassoc(_tmpSenhaassoc);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Usuario_local> getUser(final String sCartao, final String sNome) {
    final String _sql = "SELECT * FROM table_usuario WHERE cartao = ? AND nomeassociado = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (sCartao == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, sCartao);
    }
    _argIndex = 2;
    if (sNome == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, sNome);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfID = CursorUtil.getColumnIndexOrThrow(_cursor, "ID");
      final int _cursorIndexOfCartao = CursorUtil.getColumnIndexOrThrow(_cursor, "cartao");
      final int _cursorIndexOfNomeassociado = CursorUtil.getColumnIndexOrThrow(_cursor, "nomeassociado");
      final int _cursorIndexOfMescorrente = CursorUtil.getColumnIndexOrThrow(_cursor, "mescorrente");
      final int _cursorIndexOfSenhaassoc = CursorUtil.getColumnIndexOrThrow(_cursor, "senha_assoc");
      final List<Usuario_local> _result = new ArrayList<Usuario_local>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Usuario_local _item;
        _item = new Usuario_local();
        final int _tmpID;
        _tmpID = _cursor.getInt(_cursorIndexOfID);
        _item.setID(_tmpID);
        final String _tmpCartao;
        if (_cursor.isNull(_cursorIndexOfCartao)) {
          _tmpCartao = null;
        } else {
          _tmpCartao = _cursor.getString(_cursorIndexOfCartao);
        }
        _item.setCartao(_tmpCartao);
        final String _tmpNomeassociado;
        if (_cursor.isNull(_cursorIndexOfNomeassociado)) {
          _tmpNomeassociado = null;
        } else {
          _tmpNomeassociado = _cursor.getString(_cursorIndexOfNomeassociado);
        }
        _item.setNomeassociado(_tmpNomeassociado);
        final String _tmpMescorrente;
        if (_cursor.isNull(_cursorIndexOfMescorrente)) {
          _tmpMescorrente = null;
        } else {
          _tmpMescorrente = _cursor.getString(_cursorIndexOfMescorrente);
        }
        _item.setMescorrente(_tmpMescorrente);
        final String _tmpSenhaassoc;
        if (_cursor.isNull(_cursorIndexOfSenhaassoc)) {
          _tmpSenhaassoc = null;
        } else {
          _tmpSenhaassoc = _cursor.getString(_cursorIndexOfSenhaassoc);
        }
        _item.setSenhaassoc(_tmpSenhaassoc);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
