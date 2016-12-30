package br.com.eventoseartigos.sefd.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gilmar on 28/12/16.
 */

public class Palestras implements Parcelable{
    public static final String KEY = "palestras";
    private String nome;
    private String hora;
    private String data;
    private String profissional;
    private String edicao;

    public Palestras() {
    }

    public Palestras(String nome, String hora, String data, String profissional, String edicao) {
        this.nome = nome;
        this.hora = hora;
        this.data = data;
        this.profissional = profissional;
        this.edicao = edicao;
    }

    protected Palestras(Parcel in) {
        nome = in.readString();
        hora = in.readString();
        data = in.readString();
        profissional = in.readString();
        edicao = in.readString();
    }

    public static final Creator<Palestras> CREATOR = new Creator<Palestras>() {
        @Override
        public Palestras createFromParcel(Parcel in) {
            return new Palestras(in);
        }

        @Override
        public Palestras[] newArray(int size) {
            return new Palestras[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeString(hora);
        dest.writeString(data);
        dest.writeString(profissional);
        dest.writeString(edicao);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getProfissional() {
        return profissional;
    }

    public void setProfissional(String profissional) {
        this.profissional = profissional;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }
}
