package br.com.eventoseartigos.sefd.converter;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.eventoseartigos.sefd.model.Atividade;
import br.com.eventoseartigos.sefd.model.Grupos;
import br.com.eventoseartigos.sefd.model.Horario;
import br.com.eventoseartigos.sefd.model.Minicurso;

/**
 * Created by gilmar on 28/12/16.
 */

public class GruposConverter {
    public static List<Grupos> converteGruposParaString(String json) {
        try {
            JSONArray array = new JSONArray(json);
            List<Grupos> gruposList = new ArrayList<>();
            for (int i=0; i < array.length(); i++) {
                JSONObject root = array.getJSONObject(i);
                Grupos g = new Grupos();

                g.setPk(root.optString("pk"));
                g.setNome(root.optString("nome"));

                //Minicursos
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
                    JSONArray horarioArray = jsonMinucursos.getJSONArray("horarios");
                    List<Horario> horarioMinicursoList = new ArrayList<>();
                    for (int c=0; c < horarioArray.length(); c++) {
                        JSONObject jsonHorario = horarioArray.getJSONObject(c);
                        Horario h = new Horario();
                        h.setPk(jsonHorario.optString("pk"));
                        h.setData(jsonHorario.optString("data"));
                        h.setInicio(jsonHorario.optString("inicio"));
                        h.setTermino(jsonHorario.optString("termino"));
                        horarioMinicursoList.add(h);
                    }

                    m.setHorarios(horarioMinicursoList);

                    minicursoList.add(m);
                }

                //Atividade
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
                    JSONArray horarioArray = jsonAtividade.getJSONArray("horarios");
                    List<Horario> horarioList = new ArrayList<>();
                    for (int c=0; c < horarioArray.length(); c++) {
                        JSONObject jsonHorario = horarioArray.getJSONObject(c);
                        Horario h = new Horario();
                        h.setPk(jsonHorario.optString("pk"));
                        h.setData(jsonHorario.optString("data"));
                        h.setInicio(jsonHorario.optString("inicio"));
                        h.setTermino(jsonHorario.optString("termino"));
                        horarioList.add(h);
                    }

                    a.setHorarios(horarioList);

                    atividadeList.add(a);
                }

                g.setMinicursos(minicursoList);
                g.setAtividades(atividadeList);
                gruposList.add(g);
            }
            return gruposList;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }
}
