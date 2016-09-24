package br.com.eventoseartigos.sefd.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by gilmar on 15/09/16.
 */
public class Evento implements Parcelable {
    private static final String KEY = "evento";
    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String complemento;
    private String nome;
    private String edicao;
    private String logomarca;
    private String inicio;
    private String termino;
    private String inicioInscricao;
    private String terminoInscricao;
    private String nomeEvento;
    private String sigleEvento;
    private List<String> nomeAreas;
    private String status;

    public Evento() {
    }

    public Evento(String cep, String logradouro, String numero, String bairro, String cidade, String estado, String complemento, String nome, String edicao, String logomarca, String inicio, String termino, String inicioInscricao, String terminoInscricao, String nomeEvento, String sigleEvento, List<String> nomeAreas, String status) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.complemento = complemento;
        this.nome = nome;
        this.edicao = edicao;
        this.logomarca = logomarca;
        this.inicio = inicio;
        this.termino = termino;
        this.inicioInscricao = inicioInscricao;
        this.terminoInscricao = terminoInscricao;
        this.nomeEvento = nomeEvento;
        this.sigleEvento = sigleEvento;
        this.nomeAreas = nomeAreas;
        this.status = status;
    }

    protected Evento(Parcel in) {
        cep = in.readString();
        logradouro = in.readString();
        numero = in.readString();
        bairro = in.readString();
        cidade = in.readString();
        estado = in.readString();
        complemento = in.readString();
        nome = in.readString();
        edicao = in.readString();
        logomarca = in.readString();
        inicio = in.readString();
        termino = in.readString();
        inicioInscricao = in.readString();
        terminoInscricao = in.readString();
        nomeEvento = in.readString();
        sigleEvento = in.readString();
        nomeAreas = in.createStringArrayList();
        status = in.readString();
    }

    public static final Creator<Evento> CREATOR = new Creator<Evento>() {
        @Override
        public Evento createFromParcel(Parcel in) {
            return new Evento(in);
        }

        @Override
        public Evento[] newArray(int size) {
            return new Evento[size];
        }
    };

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public String getLogomarca() {
        return logomarca;
    }

    public void setLogomarca(String logomarca) {
        this.logomarca = logomarca;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public String getInicioInscricao() {
        return inicioInscricao;
    }

    public void setInicioInscricao(String inicioInscricao) {
        this.inicioInscricao = inicioInscricao;
    }

    public String getTerminoInscricao() {
        return terminoInscricao;
    }

    public void setTerminoInscricao(String terminoInscricao) {
        this.terminoInscricao = terminoInscricao;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public String getSigleEvento() {
        return sigleEvento;
    }

    public void setSigleEvento(String sigleEvento) {
        this.sigleEvento = sigleEvento;
    }

    public List<String> getNomeAreas() {
        return nomeAreas;
    }

    public void setNomeAreas(List<String> nomeAreas) {
        this.nomeAreas = nomeAreas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cep);
        parcel.writeString(logradouro);
        parcel.writeString(numero);
        parcel.writeString(bairro);
        parcel.writeString(cidade);
        parcel.writeString(estado);
        parcel.writeString(complemento);
        parcel.writeString(nome);
        parcel.writeString(edicao);
        parcel.writeString(logomarca);
        parcel.writeString(inicio);
        parcel.writeString(termino);
        parcel.writeString(inicioInscricao);
        parcel.writeString(terminoInscricao);
        parcel.writeString(nomeEvento);
        parcel.writeString(sigleEvento);
        parcel.writeStringList(nomeAreas);
        parcel.writeString(status);
    }
}
