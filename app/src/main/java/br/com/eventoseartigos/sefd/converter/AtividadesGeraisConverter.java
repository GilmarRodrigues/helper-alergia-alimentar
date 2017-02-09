package br.com.eventoseartigos.sefd.converter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.eventoseartigos.sefd.model.Atividade;
import br.com.eventoseartigos.sefd.model.AtividadeGeral;
import br.com.eventoseartigos.sefd.model.AtividadeGeral;
import br.com.eventoseartigos.sefd.model.Horario;
import br.com.eventoseartigos.sefd.model.AtividadeGeral;

/**
 * Created by gilmar on 07/02/17.
 */
public class AtividadesGeraisConverter {
    public static List<AtividadeGeral> converteAtividadesGeraisParaString(String json) {
        try {
            List<AtividadeGeral> atividadeGerals = new ArrayList<>();
            JSONArray array = new JSONArray(json);
            for (int j = 0; j < array.length(); j++) {
                JSONObject root = array.getJSONObject(j);
                AtividadeGeral a = new AtividadeGeral();

                a.setPk(root.optString("pk"));
                a.setDescricao(root.optString("descricao"));
                a.setData(root.optString("data"));
                a.setInicio(root.optString("inicio"));
                a.setTermino(root.optString("termino"));
                a.setEdicao(root.optString("edicao"));

                atividadeGerals.add(a);
            }
            return atividadeGerals;

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }


    public static List<AtividadeGeral> converteAtividadesGeraisParaString(JSONObject root) {
        try {
            List<AtividadeGeral> atividadeGerals = new ArrayList<>();
            JSONArray array = root.getJSONArray("atividades_gerais");
            for (int i=0; i<array.length(); i++) {
                JSONObject rootAtividadeGeral = array.getJSONObject(i);
                AtividadeGeral a = new AtividadeGeral();

                a.setPk(rootAtividadeGeral.optString("pk"));
                a.setDescricao(rootAtividadeGeral.optString("descricao"));
                a.setData(rootAtividadeGeral.optString("data"));
                a.setInicio(rootAtividadeGeral.optString("inicio"));
                a.setTermino(rootAtividadeGeral.optString("termino"));
                a.setEdicao(rootAtividadeGeral.optString("edicao"));

                atividadeGerals.add(a);
            }
            return atividadeGerals;
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
