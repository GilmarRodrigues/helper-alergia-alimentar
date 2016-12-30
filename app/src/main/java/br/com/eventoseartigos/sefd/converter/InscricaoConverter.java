package br.com.eventoseartigos.sefd.converter;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.eventoseartigos.sefd.model.Atividade;
import br.com.eventoseartigos.sefd.model.Minicurso;
import br.com.eventoseartigos.sefd.model.TipoInscricao;

/**
 * Created by gilmar on 29/12/16.
 */

public class InscricaoConverter {
    public static String converteInscricaoParaJson(TipoInscricao tipoInscricao, List<Minicurso> minicursos,
                                                   List<Atividade> atividades, boolean palestras) {
        JSONStringer js = new JSONStringer();
        try {
            js.object();
            int tipo_pk = Integer.parseInt(tipoInscricao.getPk());
            js.key("tipo").value(tipo_pk);

            // array minicursos
            js.key("minicursos");
            js.array();
            for (Minicurso m: minicursos) {
                //js.value(m.getPk());
                int pk = Integer.parseInt(m.getPk());
                js.value(pk);
            }
            js.endArray();

            // array atividades
            js.key("atividades");
            js.array();
            for (Atividade a: atividades) {
                int pk = Integer.parseInt(a.getPk());
                js.value(pk);
            }
            js.endArray();

            js.key("palestras").value(palestras);
            js.endObject();
            Log.i("Script", js.toString());
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return js.toString();
    }
}
