package br.com.eventoseartigos.sefd.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by gilmar on 01/01/17.
 */

public class ListCertificado implements Parcelable{
    public static final String KEY = "certificados";
    public List<Certificado> certificados;

    public ListCertificado(List<Certificado> certificados) {
        this.certificados = certificados;
    }


    protected ListCertificado(Parcel in) {
        certificados = in.createTypedArrayList(Certificado.CREATOR);
    }

    public static final Creator<ListCertificado> CREATOR = new Creator<ListCertificado>() {
        @Override
        public ListCertificado createFromParcel(Parcel in) {
            return new ListCertificado(in);
        }

        @Override
        public ListCertificado[] newArray(int size) {
            return new ListCertificado[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(certificados);
    }
}
