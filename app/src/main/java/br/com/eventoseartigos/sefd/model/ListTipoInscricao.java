package br.com.eventoseartigos.sefd.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by gilmar on 28/12/16.
 */

public class ListTipoInscricao implements Parcelable{
    public static final String KEY = "tipoinscricao";
    public List<TipoInscricao> mTipoInscricaos;

    public ListTipoInscricao(List<TipoInscricao> mTipoInscricaos) {
        this.mTipoInscricaos = mTipoInscricaos;
    }

    protected ListTipoInscricao(Parcel in) {
        mTipoInscricaos = in.createTypedArrayList(TipoInscricao.CREATOR);
    }

    public static final Creator<ListTipoInscricao> CREATOR = new Creator<ListTipoInscricao>() {
        @Override
        public ListTipoInscricao createFromParcel(Parcel in) {
            return new ListTipoInscricao(in);
        }

        @Override
        public ListTipoInscricao[] newArray(int size) {
            return new ListTipoInscricao[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mTipoInscricaos);
    }
}
