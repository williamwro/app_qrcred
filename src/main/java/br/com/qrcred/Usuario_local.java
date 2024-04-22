package br.com.qrcred;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_usuario")
public class Usuario_local {

    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "cartao")
    private String cartao;

    @ColumnInfo(name = "nomeassociado")
    private String nomeassociado;

    @ColumnInfo(name = "mescorrente")
    private String mescorrente;

    @ColumnInfo(name = "senha_assoc")
    private String senhaassoc;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCartao() {
        return cartao;
    }

    public void setCartao(String text) {
        this.cartao = text;
    }
    public String getNomeassociado() {
        return nomeassociado;
    }

    public void setNomeassociado(String text) {
        this.nomeassociado = text;
    }

    public String getSenhaassoc() {
        return senhaassoc;
    }

    public void setSenhaassoc(String text) {
        this.senhaassoc = text;
    }

    public String getMescorrente() {
        return mescorrente;
    }

    public void setMescorrente(String text) {
        this.mescorrente = text;
    }


}
