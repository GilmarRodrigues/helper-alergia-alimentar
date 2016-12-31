package br.com.eventoseartigos.sefd.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by gilmar on 30/12/16.
 */

public class ListHorario implements Parcelable{
    public static final String KEY = "horarios";
    public List<Horario> horarios;

    public ListHorario(List<Horario> horarios) {
        this.horarios = horarios;
    }

    protected ListHorario(Parcel in) {
        horarios = in.createTypedArrayList(Horario.CREATOR);
    }

    public static final Creator<ListHorario> CREATOR = new Creator<ListHorario>() {
        @Override
        public ListHorario createFromParcel(Parcel in) {
            return new ListHorario(in);
        }

        @Override
        public ListHorario[] newArray(int size) {
            return new ListHorario[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(horarios);
    }
}
