package br.com.eventoseartigos.sefd.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by gilmar on 28/12/16.
 */

public class Minicurso implements Parcelable {
    public static final String KEY = "minicurso";
    private String pk;
    private String minicurso;
    private String vagas;
    private String profissional;
    private List<Horario> horarios;

    public Minicurso() {
    }

    public Minicurso(String minicurso, String vagas, String profissional, List<Horario> horarios) {
        this.minicurso = minicurso;
        this.vagas = vagas;
        this.profissional = profissional;
        this.horarios = horarios;
    }

    protected Minicurso(Parcel in) {
        pk = in.readString();
        minicurso = in.readString();
        vagas = in.readString();
        profissional = in.readString();
        horarios = in.createTypedArrayList(Horario.CREATOR);
    }

    public static final Creator<Minicurso> CREATOR = new Creator<Minicurso>() {
        @Override
        public Minicurso createFromParcel(Parcel in) {
            return new Minicurso(in);
        }

        @Override
        public Minicurso[] newArray(int size) {
            return new Minicurso[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pk);
        dest.writeString(minicurso);
        dest.writeString(vagas);
        dest.writeString(profissional);
        dest.writeTypedList(horarios);
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getMinicurso() {
        return minicurso;
    }

    public void setMinicurso(String minicurso) {
        this.minicurso = minicurso;
    }

    public String getVagas() {
        return vagas;
    }

    public void setVagas(String vagas) {
        this.vagas = vagas;
    }

    public String getProfissional() {
        return profissional;
    }

    public void setProfissional(String profissional) {
        this.profissional = profissional;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }
}