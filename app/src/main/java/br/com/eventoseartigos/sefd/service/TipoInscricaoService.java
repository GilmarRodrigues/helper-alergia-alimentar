package br.com.eventoseartigos.sefd.service;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import br.com.eventoseartigos.sefd.converter.TipoInscricaoConverter;
import br.com.eventoseartigos.sefd.model.TipoInscricao;
import br.com.eventoseartigos.sefd.utils.HttpUtils;

/**
 * Created by gilmar on 27/12/16.
 */

public class TipoInscricaoService {
    private static final String URL_TIPOS_INSCRICAO = "http://api-sefd.ifpicos.com.br/participante/tipos/inscricao/?edicao={pk}";

    public static List<TipoInscricao> getIncricao(String token, String pk) {
        try {
            String url = URL_TIPOS_INSCRICAO.replace("{pk}", pk);
            String json = HttpUtils.doGet(url, token);
            List<TipoInscricao> tipoInscricaos = TipoInscricaoConverter.converteTipoIncricaoParaString(json);
            return tipoInscricaos;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}