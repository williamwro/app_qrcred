package br.com.qrcred;

import android.os.Parcel;
import android.os.Parcelable;

public class Convenios_itens implements Parcelable {
    public final String NOMEFANTASIA;
    public final String ENDERECO;

    public Convenios_itens(String nomefantasia, String endereco) {
        this.NOMEFANTASIA = nomefantasia;
        this.ENDERECO = endereco;
    }

    protected Convenios_itens(Parcel in) {
        NOMEFANTASIA = in.readString();
        ENDERECO = in.readString();
    }

    public static final Creator<Convenios_itens> CREATOR = new Creator<Convenios_itens>() {
        @Override
        public Convenios_itens createFromParcel(Parcel in) {
            return new Convenios_itens(in);
        }

        @Override
        public Convenios_itens[] newArray(int size) {
            return new Convenios_itens[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(NOMEFANTASIA);
        dest.writeString(ENDERECO);
    }
}
