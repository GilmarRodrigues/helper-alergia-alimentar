package br.com.eventoseartigos.sefd.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by gilmar on 20/09/16.
 */
public class ListEventos implements Parcelable{
    public static final String KEY= "eventos";
    public List<Evento> eventos;

    public ListEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    protected ListEventos(Parcel in) {
        eventos = in.createTypedArrayList(Evento.CREATOR);
    }

    public static final Creator<ListEventos> CREATOR = new Creator<ListEventos>() {
        @Override
        public ListEventos createFromParcel(Parcel in) {
            return new ListEventos(in);
        }

        @Override
        public ListEventos[] newArray(int size) {
            return new ListEventos[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(eventos);
    }
}
