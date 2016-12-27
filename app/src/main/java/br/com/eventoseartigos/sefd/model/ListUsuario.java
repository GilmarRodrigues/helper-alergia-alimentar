package br.com.eventoseartigos.sefd.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by gilmar on 27/12/16.
 */

public class ListUsuario implements Parcelable{
    public static final String KEY= "usuarios";
    public List<Usuario> eventos;

    public ListUsuario(List<Usuario> eventos) {
        this.eventos = eventos;
    }

    protected ListUsuario(Parcel in) {
        eventos = in.createTypedArrayList(Usuario.CREATOR);
    }

    public static final Creator<ListUsuario> CREATOR = new Creator<ListUsuario>() {
        @Override
        public ListUsuario createFromParcel(Parcel in) {
            return new ListUsuario(in);
        }

        @Override
        public ListUsuario[] newArray(int size) {
            return new ListUsuario[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(eventos);
    }
}
