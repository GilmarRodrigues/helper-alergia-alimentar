package br.com.eventoseartigos.sefd.converter;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

import br.com.eventoseartigos.sefd.model.DocumentosNecessarios;

/**
 * Created by gilmar on 30/12/16.
 */

public class DocumentosNecessariosConveter {
    public static List<DocumentosNecessarios> converteDocNecessarioParaString(JSONObject root) {
        try {
            JSONArray array = root.getJSONArray("documentos_necessarios");
            List<DocumentosNecessarios> necessariosList = new ArrayList<>();
            for (int i=0; i < array.length(); i++) {
                JSONObject rootDocNeces = array.getJSONObject(i);
                DocumentosNecessarios n = new DocumentosNecessarios();

                n.setPk(rootDocNeces.optString("pk"));
                n.setNome(rootDocNeces.optString("nome"));
                n.setTipo_inscricao(rootDocNeces.optString("tipo_inscricao"));

                necessariosList.add(n);
            }
            return necessariosList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String converteDococumentosParaJson(String inscricao, String tipo, String documentoBase64) {
        JSONStringer js = new JSONStringer();
        try {
            js.object();
            js.key("inscricao").value(Integer.parseInt(inscricao));
            js.key("tipo").value(Integer.parseInt(tipo));
            js.key("documento").value(documentoBase64);

            js.endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js.toString();
    }
}
