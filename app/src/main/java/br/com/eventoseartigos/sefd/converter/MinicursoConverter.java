package br.com.eventoseartigos.sefd.converter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.eventoseartigos.sefd.model.Horario;
import br.com.eventoseartigos.sefd.model.Minicurso;

/**
 * Created by gilmar on 30/12/16.
 */

public class MinicursoConverter {

    public static List<Minicurso> converteMinicursoParaString(JSONObject root) {
        try {
            JSONArray minicArray = root.getJSONArray("minicursos");
            List<Minicurso> minicursoList = new ArrayList<>();
            for (int j=0; j < minicArray.length(); j++) {
                JSONObject jsonMinucursos = minicArray.getJSONObject(j);
                Minicurso m = new Minicurso();

                m.setPk(jsonMinucursos.optString("pk"));
                m.setMinicurso(jsonMinucursos.optString("minicurso"));
                m.setVagas(jsonMinucursos.optString("vagas"));
                m.setProfissional(jsonMinucursos.optString("profissional"));

                // Horario
                List<Horario> horarios = HorarioConverter.converteHorarioParaString(jsonMinucursos);
                m.setHorarios(horarios);


                minicursoList.add(m);
            }
            return minicursoList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
