package br.com.eventoseartigos.sefd.converter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.eventoseartigos.sefd.model.Certificado;

/**
 * Created by gilmar on 01/01/17.
 */

public class CertificadoConverter {

    public static List<Certificado> converteCertificadoParaString(String json, List<Certificado> certificados, String tipo) {
        try {
            JSONArray array = new JSONArray(json);
            for (int i=0; i < array.length(); i++) {
                JSONObject root = array.getJSONObject(i);
                Certificado c = new Certificado();

                c.setPk(root.optString("pk"));
                c.setNome_certificado(root.optString("nome_certificado"));
                c.setToken(root.optString("token"));
                c.setDisponivel(root.optString("disponivel"));
                c.setEvento(root.optString("evento"));
                c.setTurma(root.optString("turma"));
                c.setPalestra(root.optString("palestra"));
                c.setTipo(tipo);

                certificados.add(c);
            }
            return certificados;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
