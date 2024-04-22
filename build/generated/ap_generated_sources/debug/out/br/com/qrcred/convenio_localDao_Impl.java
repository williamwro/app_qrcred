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
public final class convenio_localDao_Impl implements convenio_localDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Convenio_local> __insertionAdapterOfConvenio_local;

  private final EntityDeletionOrUpdateAdapter<Convenio_local> __deletionAdapterOfConvenio_local;

  private final SharedSQLiteStatement __preparedStmtOfUpdate;

  public convenio_localDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfConvenio_local = new EntityInsertionAdapter<Convenio_local>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `table_convenio` (`ID`,`logconv`,`nomeconvenio`,`senha_convenio`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Convenio_local entity) {
        statement.bindLong(1, entity.getID());
        if (entity.getLogconv() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getLogconv());
        }
        if (entity.getNomeconvenio() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getNomeconvenio());
        }
        if (entity.getSenha_convenio() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getSenha_convenio());
        }
      }
    };
    this.__deletionAdapterOfConvenio_local = new EntityDeletionOrUpdateAdapter<Convenio_local>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `table_convenio` WHERE `ID` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Convenio_local entity) {
        statement.bindLong(1, entity.getID());
      }
    };
    this.__preparedStmtOfUpdate = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE table_convenio SET logconv = ?, nomeconvenio = ? WHERE ID = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Convenio_local convenio_local) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfConvenio_local.insert(convenio_local);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Convenio_local convenio_local) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfConvenio_local.handle(convenio_local);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void reset(final List<Convenio_local> convenio_local) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfConvenio_local.handleMultiple(convenio_local);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final int sIDC, final String slogconv, final String sNomeconvenio) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdate.acquire();
    int _argIndex = 1;
    if (slogconv == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, slogconv);
    }
    _argIndex = 2;
    if (sNomeconvenio == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, sNomeconvenio);
    }
    _argIndex = 3;
    _stmt.bindLong(_argIndex, sIDC);
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
  public List<Convenio_local> getAll() {
    final String _sql = "SELECT * FROM table_convenio";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfID = CursorUtil.getColumnIndexOrThrow(_cursor, "ID");
      final int _cursorIndexOfLogconv = CursorUtil.getColumnIndexOrThrow(_cursor, "logconv");
      final int _cursorIndexOfNomeconvenio = CursorUtil.getColumnIndexOrThrow(_cursor, "nomeconvenio");
      final int _cursorIndexOfSenhaConvenio = CursorUtil.getColumnIndexOrThrow(_cursor, "senha_convenio");
      final List<Convenio_local> _result = new ArrayList<Convenio_local>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Convenio_local _item;
        _item = new Convenio_local();
        final int _tmpID;
        _tmpID = _cursor.getInt(_cursorIndexOfID);
        _item.setID(_tmpID);
        final String _tmpLogconv;
        if (_cursor.isNull(_cursorIndexOfLogconv)) {
          _tmpLogconv = null;
        } else {
          _tmpLogconv = _cursor.getString(_cursorIndexOfLogconv);
        }
        _item.setLogconv(_tmpLogconv);
        final String _tmpNomeconvenio;
        if (_cursor.isNull(_cursorIndexOfNomeconvenio)) {
          _tmpNomeconvenio = null;
        } else {
          _tmpNomeconvenio = _cursor.getString(_cursorIndexOfNomeconvenio);
        }
        _item.setNomeconvenio(_tmpNomeconvenio);
        final String _tmpSenha_convenio;
        if (_cursor.isNull(_cursorIndexOfSenhaConvenio)) {
          _tmpSenha_convenio = null;
        } else {
          _tmpSenha_convenio = _cursor.getString(_cursorIndexOfSenhaConvenio);
        }
        _item.setSenha_convenio(_tmpSenha_convenio);
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
