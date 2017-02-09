package br.com.eventoseartigos.sefd.service;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import br.com.eventoseartigos.sefd.converter.InscricaoConverter;
import br.com.eventoseartigos.sefd.model.Atividade;
import br.com.eventoseartigos.sefd.model.Inscricao;
import br.com.eventoseartigos.sefd.model.Minicurso;
import br.com.eventoseartigos.sefd.model.TipoInscricao;
import br.com.eventoseartigos.sefd.utils.HttpUtils;

/**
 * Created by gilmar on 29/12/16.
 */

public class InscricaoService {

    private static final String URL="http://api-sefd.ifpicos.com.br/participante/inscricoes/";

    public static String setInscricao(TipoInscricao tipo, List<Minicurso> minicursos, List<Atividade> atividades,
                                      boolean palestras, String token) {
        try {
            String json = InscricaoConverter.converteInscricaoParaJson(tipo, minicursos, atividades, palestras);
            Log.i("Script", json);
            String str = HttpUtils.doPost(URL, json, token);
            return str;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Inscricao> getInscricao(String token) {
        try {
            String json = HttpUtils.doGet(URL, token);
            List<Inscricao> inscricoes = InscricaoConverter.converteInscricaoParaString(json);
            Log.i("Script", json);
            return inscricoes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
