package br.com.eventoseartigos.sefd.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gilmar on 07/02/17.
 */

public class AtividadeGeral implements Parcelable{
    public static final String KEY = "atividadegeral";
    private String pk;
    private String descricao;
    private String data;
    private String inicio;
    private String termino;
    private String edicao;

    public AtividadeGeral() {
    }

    public AtividadeGeral(String pk, String descricao, String data, String inicio, String termino, String edicao) {
        this.pk = pk;
        this.descricao = descricao;
        this.data = data;
        this.inicio = inicio;
        this.termino = termino;
        this.edicao = edicao;
    }

    protected AtividadeGeral(Parcel in) {
        pk = in.readString();
        descricao = in.readString();
        data = in.readString();
        inicio = in.readString();
        termino = in.readString();
        edicao = in.readString();
    }

    public static final Creator<AtividadeGeral> CREATOR = new Creator<AtividadeGeral>() {
        @Override
        public AtividadeGeral createFromParcel(Parcel in) {
            return new AtividadeGeral(in);
        }

        @Override
        public AtividadeGeral[] newArray(int size) {
            return new AtividadeGeral[size];
        }
    };

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pk);
        dest.writeString(descricao);
        dest.writeString(data);
        dest.writeString(inicio);
        dest.writeString(termino);
        dest.writeString(edicao);
    }
}
