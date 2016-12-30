package br.com.eventoseartigos.sefd.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by gilmar on 28/12/16.
 */

public class Atividade implements Parcelable{
    public static final String KEY = "atividade";
    private String pk;
    private String atividade;
    private String descricao;
    private String vagas;
    private List<Horario> horarios;

    public Atividade() {
    }

    public Atividade(String atividade, String descricao, String vagas, List<Horario> horarios) {
        this.atividade = atividade;
        this.descricao = descricao;
        this.vagas = vagas;
        this.horarios = horarios;
    }

    protected Atividade(Parcel in) {
        pk = in.readString();
        atividade = in.readString();
        descricao = in.readString();
        vagas = in.readString();
        horarios = in.createTypedArrayList(Horario.CREATOR);
    }

    public static final Creator<Atividade> CREATOR = new Creator<Atividade>() {
        @Override
        public Atividade createFromParcel(Parcel in) {
            return new Atividade(in);
        }

        @Override
        public Atividade[] newArray(int size) {
            return new Atividade[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pk);
        dest.writeString(atividade);
        dest.writeString(descricao);
        dest.writeString(vagas);
        dest.writeTypedList(horarios);
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getVagas() {
        return vagas;
    }

    public void setVagas(String vagas) {
        this.vagas = vagas;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }
}
