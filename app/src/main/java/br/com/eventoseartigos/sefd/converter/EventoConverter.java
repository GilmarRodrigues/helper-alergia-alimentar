package br.com.eventoseartigos.sefd.converter;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.eventoseartigos.sefd.model.Evento;

/**
 * Created by gilmar on 19/09/16.
 */
public class EventoConverter {

    public static List<Evento> converteEventoParaString(String json) {
        try {
            //
            List<Evento> eventos = new ArrayList<>();
            JSONArray array = new JSONArray(json);
            for (int j = 0; j < array.length(); j++) {
                JSONObject root = array.getJSONObject(j);
                Evento e = new Evento();

                e.setCep(root.optString("cep"));
                e.setLogomarca(root.optString("logradouro"));
                e.setNumero(root.optString("numero"));
                e.setBairro(root.optString("bairro"));
                e.setCidade(root.optString("cidade"));
                e.setEstado(root.optString("estado"));
                e.setComplemento(root.optString("complemento"));
                e.setNome(root.optString("nome"));
                e.setEdicao(root.optString("edicao"));
                e.setLogomarca(root.optString("logomarca"));
                e.setInicio(root.optString("inicio"));
                e.setTermino(root.optString("termino"));
                e.setInicioInscricao(root.optString("inicio_inscricao"));
                e.setTerminoInscricao(root.optString("termino_inscricao"));

                JSONObject evento = root.getJSONObject("evento");
                e.setNomeEvento(evento.optString("nome"));
                e.setSigleEvento(evento.optString("sigla"));

                JSONArray areas = root.getJSONArray("areas");
                ArrayList<String> listNomeArea = new ArrayList<>();
                for (int i = 0; i < areas.length(); i++) {
                    JSONObject jsonArea = areas.getJSONObject(i);
                    listNomeArea.add(jsonArea.optString("nome"));
                }
                e.setNomeAreas(listNomeArea);

                e.setStatus(root.optString("status"));

                eventos.add(e);
            }
            return eventos;

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }
}
