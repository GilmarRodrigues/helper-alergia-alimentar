package br.com.eventoseartigos.sefd.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gilmar on 28/12/16.
 */

public class Horario implements Parcelable{
    public static final String KEY = "horario";
    private String pk;
    private String data;
    private String inicio;
    private String termino;

    public Horario() {
    }

    public Horario(String pk, String data, String inicio, String termino) {
        this.pk = pk;
        this.data = data;
        this.inicio = inicio;
        this.termino = termino;
    }

    protected Horario(Parcel in) {
        pk = in.readString();
        data = in.readString();
        inicio = in.readString();
        termino = in.readString();
    }

    public static final Creator<Horario> CREATOR = new Creator<Horario>() {
        @Override
        public Horario createFromParcel(Parcel in) {
            return new Horario(in);
        }

        @Override
        public Horario[] newArray(int size) {
            return new Horario[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pk);
        dest.writeString(data);
        dest.writeString(inicio);
        dest.writeString(termino);
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
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

}
