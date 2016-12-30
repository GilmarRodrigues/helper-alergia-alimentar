package br.com.eventoseartigos.sefd.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gilmar on 28/12/16.
 */

public class TipoInscricao implements Parcelable{
    public static final String KEY = "tipoinscricao";
    private String pk;
    private String nome;
    private String preco_minicurso;
    private String preco_palestra;
    private String preco_atividade;

    public TipoInscricao() {
    }

    public TipoInscricao(String pk, String nome, String preco_minicurso, String preco_palestra, String preco_atividade) {
        this.pk = pk;
        this.nome = nome;
        this.preco_minicurso = preco_minicurso;
        this.preco_palestra = preco_palestra;
        this.preco_atividade = preco_atividade;
    }

    protected TipoInscricao(Parcel in) {
        pk = in.readString();
        nome = in.readString();
        preco_minicurso = in.readString();
        preco_palestra = in.readString();
        preco_atividade = in.readString();
    }

    public static final Creator<TipoInscricao> CREATOR = new Creator<TipoInscricao>() {
        @Override
        public TipoInscricao createFromParcel(Parcel in) {
            return new TipoInscricao(in);
        }

        @Override
        public TipoInscricao[] newArray(int size) {
            return new TipoInscricao[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pk);
        dest.writeString(nome);
        dest.writeString(preco_minicurso);
        dest.writeString(preco_palestra);
        dest.writeString(preco_atividade);
    }

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

    public String getPreco_minicurso() {
        return preco_minicurso;
    }

    public void setPreco_minicurso(String preco_minicurso) {
        this.preco_minicurso = preco_minicurso;
    }

    public String getPreco_palestra() {
        return preco_palestra;
    }

    public void setPreco_palestra(String preco_palestra) {
        this.preco_palestra = preco_palestra;
    }

    public String getPreco_atividade() {
        return preco_atividade;
    }

    public void setPreco_atividade(String preco_atividade) {
        this.preco_atividade = preco_atividade;
    }
}
