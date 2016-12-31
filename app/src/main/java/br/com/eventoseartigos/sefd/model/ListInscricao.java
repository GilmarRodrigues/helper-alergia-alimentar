package br.com.eventoseartigos.sefd.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by gilmar on 30/12/16.
 */

public class ListInscricao implements Parcelable{
    public static final String KEY = "inscricoes";
    public List<Inscricao> inscricaos;

    public ListInscricao(List<Inscricao> inscricaos) {
        this.inscricaos = inscricaos;
    }

    protected ListInscricao(Parcel in) {
        inscricaos = in.createTypedArrayList(Inscricao.CREATOR);
    }

    public static final Creator<ListInscricao> CREATOR = new Creator<ListInscricao>() {
        @Override
        public ListInscricao createFromParcel(Parcel in) {
            return new ListInscricao(in);
        }

        @Override
        public ListInscricao[] newArray(int size) {
            return new ListInscricao[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(inscricaos);
    }
}
