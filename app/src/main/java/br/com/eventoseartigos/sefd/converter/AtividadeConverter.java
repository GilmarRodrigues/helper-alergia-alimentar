package br.com.eventoseartigos.sefd.converter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.eventoseartigos.sefd.model.Atividade;
import br.com.eventoseartigos.sefd.model.Horario;

/**
 * Created by gilmar on 30/12/16.
 */

public class AtividadeConverter {
    public static List<Atividade> converteAtividadeParaString(JSONObject root) {
        try {
            JSONArray atividadeArray = root.getJSONArray("atividades");
            List<Atividade> atividadeList = new ArrayList<>();
            for (int j=0; j < atividadeArray.length(); j++) {
                JSONObject jsonAtividade = atividadeArray.getJSONObject(j);
                Atividade a = new Atividade();

                a.setPk(jsonAtividade.optString("pk"));
                a.setAtividade(jsonAtividade.optString("atividade"));
                a.setDescricao(jsonAtividade.optString("descricao"));
                a.setVagas(jsonAtividade.optString("vagas"));

                // Horario
                List<Horario> horarios = HorarioConverter.converteHorarioParaString(jsonAtividade);
                a.setHorarios(horarios);

                atividadeList.add(a);
            }
            return atividadeList;
        }catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
