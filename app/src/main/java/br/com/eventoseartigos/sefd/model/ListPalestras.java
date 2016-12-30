package br.com.eventoseartigos.sefd.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by gilmar on 28/12/16.
 */

public class ListPalestras implements Parcelable{
    public static final String KEY = "palestras";
    public List<Palestras> palestras;

    public ListPalestras(List<Palestras> palestras) {
        this.palestras = palestras;
    }

    protected ListPalestras(Parcel in) {
        palestras = in.createTypedArrayList(Palestras.CREATOR);
    }

    public static final Creator<ListPalestras> CREATOR = new Creator<ListPalestras>() {
        @Override
        public ListPalestras createFromParcel(Parcel in) {
            return new ListPalestras(in);
        }

        @Override
        public ListPalestras[] newArray(int size) {
            return new ListPalestras[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(palestras);
    }
}
