package br.com.qrcred;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_convenio")
public class Convenio_local {

    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "logconv")
    private String logconv;

    @ColumnInfo(name = "nomeconvenio")
    private String nomeconvenio;

    @ColumnInfo(name = "senha_convenio")
    private String senha_convenio;

    public int getID() { return ID; }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLogconv() { return logconv; }

    public void setLogconv(String text) {
        this.logconv = text;
    }

    public String getNomeconvenio() {
        return nomeconvenio;
    }

    public void setNomeconvenio(String text) {
        this.nomeconvenio = text;
    }

    public String getSenha_convenio() {
        return senha_convenio;
    }

    public void setSenha_convenio(String text) {
        this.senha_convenio = text;
    }

}
