package br.com.eventoseartigos.sefd.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by gilmar on 28/12/16.
 */

public class Grupos implements Parcelable{
    public static final String KEY = "grupos";
    private String pk;
    private String nome;
    private List<Minicurso> minicursos;
    private List<Atividade> atividades;

    public Grupos() {
    }

    public Grupos(String pk, String nome, List<Minicurso> minicursos, List<Atividade> atividades) {
        this.pk = pk;
        this.nome = nome;
        this.minicursos = minicursos;
        this.atividades = atividades;
    }

    protected Grupos(Parcel in) {
        pk = in.readString();
        nome = in.readString();
        minicursos = in.createTypedArrayList(Minicurso.CREATOR);
        atividades = in.createTypedArrayList(Atividade.CREATOR);
    }

    public static final Creator<Grupos> CREATOR = new Creator<Grupos>() {
        @Override
        public Grupos createFromParcel(Parcel in) {
            return new Grupos(in);
        }

        @Override
        public Grupos[] newArray(int size) {
            return new Grupos[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pk);
        dest.writeString(nome);
        dest.writeTypedList(minicursos);
        dest.writeTypedList(atividades);
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Minicurso> getMinicursos() {
        return minicursos;
    }

    public void setMinicursos(List<Minicurso> minicursos) {
        this.minicursos = minicursos;
    }

    public List<Atividade> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }

    public static Creator<Grupos> getCREATOR() {
        return CREATOR;
    }
}
