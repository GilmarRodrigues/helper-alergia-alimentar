package br.com.eventoseartigos.sefd.service;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import br.com.eventoseartigos.sefd.converter.GruposConverter;
import br.com.eventoseartigos.sefd.model.Grupos;
import br.com.eventoseartigos.sefd.utils.HttpUtils;

/**
 * Created by gilmar on 28/12/16.
 */

public class GruposService {
    private static final String URL_GRUPOS = "http://api-sefd.ifpicos.com.br/participante/grupos/?edicao={pk}";

    public static List<Grupos> getGrupos(String token, String pk) {
        try {
            String url = URL_GRUPOS.replace("{pk}", pk);
            String json = HttpUtils.doGet(url, token);
            List<Grupos> grupos = GruposConverter.converteGruposParaString(json);
            return grupos;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
