package br.com.eventoseartigos.sefd.converter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.eventoseartigos.sefd.model.Horario;

/**
 * Created by gilmar on 30/12/16.
 */

public class HorarioConverter {

    public static List<Horario> converteHorarioParaString(JSONObject root) {

        try {
            JSONArray horarioArray = root.getJSONArray("horarios");
            List<Horario> horarios = new ArrayList<>();
            for (int i=0; i < horarioArray.length(); i++) {

                JSONObject jsonHorario = horarioArray.getJSONObject(i);
                Horario h = new Horario();

                h.setPk(jsonHorario.optString("pk"));
                h.setData(jsonHorario.optString("data"));
                h.setInicio(jsonHorario.optString("inicio"));
                h.setTermino(jsonHorario.optString("termino"));

                horarios.add(h);
            }
            return horarios;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
