package br.com.eventoseartigos.sefd.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gilmar on 30/12/16.
 */

public class DocumentosEnviados implements Parcelable{
    public static final String KEY = "documentos_enviados";
    private String pk;
    private String nome_tipo;
    private String valido;

    public DocumentosEnviados() {
    }

    public DocumentosEnviados(String pk, String nome_tipo, String valido) {
        this.pk = pk;
        this.nome_tipo = nome_tipo;
        this.valido = valido;
    }

    protected DocumentosEnviados(Parcel in) {
        pk = in.readString();
        nome_tipo = in.readString();
        valido = in.readString();
    }

    public static final Creator<DocumentosEnviados> CREATOR = new Creator<DocumentosEnviados>() {
        @Override
        public DocumentosEnviados createFromParcel(Parcel in) {
            return new DocumentosEnviados(in);
        }

        @Override
        public DocumentosEnviados[] newArray(int size) {
            return new DocumentosEnviados[size];
        }
    };

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getNome_tipo() {
        return nome_tipo;
    }

    public void setNome_tipo(String nome_tipo) {
        this.nome_tipo = nome_tipo;
    }

    public String getValido() {
        return valido;
    }

    public void setValido(String valido) {
        this.valido = valido;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pk);
        dest.writeString(nome_tipo);
        dest.writeString(valido);
    }
}
