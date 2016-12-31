package br.com.eventoseartigos.sefd.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by gilmar on 30/12/16.
 */

public class Inscricao implements Parcelable{
    public static final String KEY = "inscricao";
    private String pk;
    private String tipo;
    private String preco;
    private String status;
    private List<Minicurso> minicursos;
    private List<Atividade> atividades;
    private List<Palestras> palestras;
    private List<DocumentosEnviados> documentos_enviados;
    private List<DocumentosNecessarios> documentos_necessarios;
    private String edicao;

    public Inscricao() {
    }

    public Inscricao(String pk, String tipo, String preco, String status, List<Minicurso> minicursos, List<Atividade> atividades, List<Palestras> palestras, List<DocumentosEnviados> documentos_enviados, List<DocumentosNecessarios> documentos_necessarios, String edicao) {
        this.pk = pk;
        this.tipo = tipo;
        this.preco = preco;
        this.status = status;
        this.minicursos = minicursos;
        this.atividades = atividades;
        this.palestras = palestras;
        this.documentos_enviados = documentos_enviados;
        this.documentos_necessarios = documentos_necessarios;
        this.edicao = edicao;
    }

    protected Inscricao(Parcel in) {
        pk = in.readString();
        tipo = in.readString();
        preco = in.readString();
        status = in.readString();
        minicursos = in.createTypedArrayList(Minicurso.CREATOR);
        atividades = in.createTypedArrayList(Atividade.CREATOR);
        palestras = in.createTypedArrayList(Palestras.CREATOR);
        documentos_enviados = in.createTypedArrayList(DocumentosEnviados.CREATOR);
        documentos_necessarios = in.createTypedArrayList(DocumentosNecessarios.CREATOR);
        edicao = in.readString();
    }

    public static final Creator<Inscricao> CREATOR = new Creator<Inscricao>() {
        @Override
        public Inscricao createFromParcel(Parcel in) {
            return new Inscricao(in);
        }

        @Override
        public Inscricao[] newArray(int size) {
            return new Inscricao[size];
        }
    };

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<Palestras> getPalestras() {
        return palestras;
    }

    public void setPalestras(List<Palestras> palestras) {
        this.palestras = palestras;
    }

    public List<DocumentosEnviados> getDocumentos_enviados() {
        return documentos_enviados;
    }

    public void setDocumentos_enviados(List<DocumentosEnviados> documentos_enviados) {
        this.documentos_enviados = documentos_enviados;
    }

    public List<DocumentosNecessarios> getDocumentos_necessarios() {
        return documentos_necessarios;
    }

    public void setDocumentos_necessarios(List<DocumentosNecessarios> documentos_necessarios) {
        this.documentos_necessarios = documentos_necessarios;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pk);
        dest.writeString(tipo);
        dest.writeString(preco);
        dest.writeString(status);
        dest.writeTypedList(minicursos);
        dest.writeTypedList(atividades);
        dest.writeTypedList(palestras);
        dest.writeTypedList(documentos_enviados);
        dest.writeTypedList(documentos_necessarios);
        dest.writeString(edicao);
    }
}

