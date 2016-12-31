package br.com.eventoseartigos.sefd.converter;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.eventoseartigos.sefd.model.DocumentosEnviados;

/**
 * Created by gilmar on 30/12/16.
 */

public class DocumentosEnviadosConverter {

    public static DocumentosEnviados converteDocEnviadorsParaString(String json) {
        try {
            DocumentosEnviados e = new DocumentosEnviados();

            JSONObject root = new JSONObject(json);
            e.setPk(root.optString("pk"));
            e.setNome_tipo(root.optString("nome_tipo"));
            e.setValido(root.optString("valido"));

            return e != null ? e : null;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<DocumentosEnviados> converteDocEnviadorsParaString(JSONObject root) {
        try {
            JSONArray array = root.getJSONArray("documentos_enviados");
            List<DocumentosEnviados> enviadosList = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject rootDocEnviados = array.getJSONObject(i);
                DocumentosEnviados e = new DocumentosEnviados();

                e.setPk(rootDocEnviados.optString("pk"));
                e.setNome_tipo(rootDocEnviados.optString("nome_tipo"));
                e.setValido(rootDocEnviados.optString("valido"));

                enviadosList.add(e);

            }
            return enviadosList;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
