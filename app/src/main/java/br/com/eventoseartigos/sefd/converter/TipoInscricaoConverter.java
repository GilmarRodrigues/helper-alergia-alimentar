package br.com.eventoseartigos.sefd.converter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.eventoseartigos.sefd.model.TipoInscricao;

/**
 * Created by gilmar on 28/12/16.
 */

public class TipoInscricaoConverter {
    public static List<TipoInscricao> converteTipoIncricaoParaString(String json) {
        try {
            List<TipoInscricao> tipoInscricaos = new ArrayList<>();
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject root = array.getJSONObject(i);
                TipoInscricao ti = new TipoInscricao();

                ti.setPk(root.optString("pk"));
                ti.setNome(root.optString("nome"));
                ti.setPreco_minicurso(root.getString("preco_minicurso"));
                ti.setPreco_palestra(root.getString("preco_palestra"));
                ti.setPreco_atividade(root.getString("preco_atividade"));

                tipoInscricaos.add(ti);
            }
            return tipoInscricaos;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
