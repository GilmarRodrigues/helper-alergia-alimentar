package br.com.eventoseartigos.sefd.converter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.eventoseartigos.sefd.model.Palestras;

/**
 * Created by gilmar on 28/12/16.
 */

public class PalestrasConverter {
    public static List<Palestras> convertePalestrasParaString(String json) {
        try {
            List<Palestras> palestrasList = new ArrayList<>();
            JSONArray array = new JSONArray(json);
            for (int i=0; i<array.length(); i++) {
                JSONObject root = array.getJSONObject(i);
                Palestras p = new Palestras();

                p.setNome(root.optString("nome"));
                p.setHora(root.optString("hora"));
                p.setData(root.optString("data"));
                p.setProfissional(root.getString("profissional"));
                p.setEdicao(root.optString("edicao"));

                palestrasList.add(p);
            }
            return palestrasList;
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
