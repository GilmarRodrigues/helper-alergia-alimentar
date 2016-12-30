package br.com.eventoseartigos.sefd.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by gilmar on 28/12/16.
 */

public class ListGrupos implements Parcelable{
    public static final String KEY = "grupos";
    public List<Grupos> gruposList;

    public ListGrupos(List<Grupos> gruposList) {
        this.gruposList = gruposList;
    }

    protected ListGrupos(Parcel in) {
        gruposList = in.createTypedArrayList(Grupos.CREATOR);
    }

    public static final Creator<ListGrupos> CREATOR = new Creator<ListGrupos>() {
        @Override
        public ListGrupos createFromParcel(Parcel in) {
            return new ListGrupos(in);
        }

        @Override
        public ListGrupos[] newArray(int size) {
            return new ListGrupos[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(gruposList);
    }
}
