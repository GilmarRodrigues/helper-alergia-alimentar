package br.com.eventoseartigos.sefd.service;

import java.io.IOException;
import java.util.List;

import br.com.eventoseartigos.sefd.converter.AtividadesGeraisConverter;
import br.com.eventoseartigos.sefd.model.AtividadeGeral;
import br.com.eventoseartigos.sefd.utils.HttpUtils;

/**
 * Created by gilmar on 07/02/17.
 */

public class AtividadesGeraisService {

    private static final String URL_ATIVIDADES_GERRAIS = "http://api-sefd.ifpicos.com.br/participante/atividades/gerais/?edicao={pk}";

    public static List<AtividadeGeral> getAtividadesGerais(String token, String pk) {
        try {
            String url = URL_ATIVIDADES_GERRAIS.replace("{pk}", pk);
            String json = HttpUtils.doGet(url, token);
            List<AtividadeGeral> atividadeGerals = AtividadesGeraisConverter.converteAtividadesGeraisParaString(json);
            return atividadeGerals;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
