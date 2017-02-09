package br.com.eventoseartigos.sefd.converter;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

import br.com.eventoseartigos.sefd.model.Atividade;
import br.com.eventoseartigos.sefd.model.AtividadeGeral;
import br.com.eventoseartigos.sefd.model.DocumentosEnviados;
import br.com.eventoseartigos.sefd.model.DocumentosNecessarios;
import br.com.eventoseartigos.sefd.model.Horario;
import br.com.eventoseartigos.sefd.model.Inscricao;
import br.com.eventoseartigos.sefd.model.Minicurso;
import br.com.eventoseartigos.sefd.model.Palestras;
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
            js.key("certificados_minicursos");
            js.array();
            for (Minicurso m: minicursos) {
                //js.value(m.getPk());
                int pk = Integer.parseInt(m.getPk());
                js.value(pk);
            }
            js.endArray();

            // array atividades
            js.key("certificados_atividades");
            js.array();
            for (Atividade a: atividades) {
                int pk = Integer.parseInt(a.getPk());
                js.value(pk);
            }
            js.endArray();

            js.key("certificados_palestras").value(palestras);
            js.key("certificado_evento").value(true);
            js.endObject();
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return js.toString();
    }

    public static List<Inscricao> converteInscricaoParaString(String json) {
        try {
            List<Inscricao> inscricaoList = new ArrayList<>();
            JSONArray array = new JSONArray(json);
            for (int i=0; i < array.length(); i++) {
                JSONObject root = array.getJSONObject(i);
                Inscricao in = new Inscricao();

                in.setPk(root.optString("pk"));
                in.setTipo(root.optString("tipo"));
                in.setPreco(root.optString("preco"));
                in.setStatus(root.optString("status"));

                //Minicursos e horario
                List<Minicurso> minicursoList = MinicursoConverter.converteMinicursoParaString(root);
                in.setMinicursos(minicursoList);

                //Atividade
                List<Atividade> atividadeList = AtividadeConverter.converteAtividadeParaString(root);
                in.setAtividades(atividadeList);

                //Palestras
                List<Palestras> palestrasList = PalestrasConverter.convertePalestrasParaString(root);
                in.setPalestras(palestrasList);

                // Atividades Gerais
                List<AtividadeGeral> atividadeGerals = AtividadesGeraisConverter.converteAtividadesGeraisParaString(root);
                in.setAtividades_gerais(atividadeGerals);

                //Documentos Enviados
                List<DocumentosEnviados> enviadosList = DocumentosEnviadosConverter.converteDocEnviadorsParaString(root);
                in.setDocumentos_enviados(enviadosList);

                //Documentos necessarios
                List<DocumentosNecessarios> necessariosList = DocumentosNecessariosConveter.converteDocNecessarioParaString(root);
                in.setDocumentos_necessarios(necessariosList);

                in.setEdicao(root.optString("edicao"));

                inscricaoList.add(in);
            }
            return inscricaoList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
