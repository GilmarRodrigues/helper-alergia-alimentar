package br.com.eventoseartigos.sefd.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gilmar on 30/12/16.
 */

public class DocumentosNecessarios implements Parcelable{
    public static final String KEY ="documentos_necessarios";
    private String pk;
    private String nome;
    private String tipo_inscricao;

    public DocumentosNecessarios() {
    }

    public DocumentosNecessarios(String pk, String nome, String tipo_inscricao) {
        this.pk = pk;
        this.nome = nome;
        this.tipo_inscricao = tipo_inscricao;
    }

    protected DocumentosNecessarios(Parcel in) {
        pk = in.readString();
        nome = in.readString();
        tipo_inscricao = in.readString();
    }

    public static final Creator<DocumentosNecessarios> CREATOR = new Creator<DocumentosNecessarios>() {
        @Override
        public DocumentosNecessarios createFromParcel(Parcel in) {
            return new DocumentosNecessarios(in);
        }

        @Override
        public DocumentosNecessarios[] newArray(int size) {
            return new DocumentosNecessarios[size];
        }
    };

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo_inscricao() {
        return tipo_inscricao;
    }

    public void setTipo_inscricao(String tipo_inscricao) {
        this.tipo_inscricao = tipo_inscricao;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pk);
        dest.writeString(nome);
        dest.writeString(tipo_inscricao);
    }
}
