package br.com.eventoseartigos.sefd.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gilmar on 01/01/17.
 */

public class Certificado implements Parcelable {
    public static final String KEY = "certificado";
    private String pk;
    private String nome_certificado;
    private String token;
    private String disponivel;
    private String evento;
    private String turma;
    private String palestra;
    private String tipo;//minicuros, atividade ou palestra

    public Certificado() {
    }

    public Certificado(String pk, String nome_certificado, String token, String disponivel, String evento, String turma, String palestra, String tipo) {
        this.pk = pk;
        this.nome_certificado = nome_certificado;
        this.token = token;
        this.disponivel = disponivel;
        this.evento = evento;
        this.turma = turma;
        this.palestra = palestra;
        this.tipo = tipo;
    }

    protected Certificado(Parcel in) {
        pk = in.readString();
        nome_certificado = in.readString();
        token = in.readString();
        disponivel = in.readString();
        evento = in.readString();
        turma = in.readString();
        palestra = in.readString();
        tipo = in.readString();
    }

    public static final Creator<Certificado> CREATOR = new Creator<Certificado>() {
        @Override
        public Certificado createFromParcel(Parcel in) {
            return new Certificado(in);
        }

        @Override
        public Certificado[] newArray(int size) {
            return new Certificado[size];
        }
    };

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getNome_certificado() {
        return nome_certificado;
    }

    public void setNome_certificado(String nome_certificado) {
        this.nome_certificado = nome_certificado;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(String disponivel) {
        this.disponivel = disponivel;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getPalestra() {
        return palestra;
    }

    public void setPalestra(String palestra) {
        this.palestra = palestra;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pk);
        dest.writeString(nome_certificado);
        dest.writeString(token);
        dest.writeString(disponivel);
        dest.writeString(evento);
        dest.writeString(turma);
        dest.writeString(palestra);
        dest.writeString(tipo);
    }
}