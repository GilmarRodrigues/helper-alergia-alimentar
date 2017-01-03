package br.com.eventoseartigos.sefd.converter;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

/**
 * Created by gilmar on 02/01/17.
 */

public class DefinirNomeCertificadoConverter {

    public static String converteNomeCertificadoParaJson(String nome) {
        JSONStringer js = new JSONStringer();
        try {
            js.object();
            js.key("nome_certificado").value(nome);
            js.endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js.toString();
    }
}
