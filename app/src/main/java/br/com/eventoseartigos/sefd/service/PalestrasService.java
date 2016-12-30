package br.com.eventoseartigos.sefd.service;

import java.io.IOException;
import java.util.List;

import br.com.eventoseartigos.sefd.converter.PalestrasConverter;
import br.com.eventoseartigos.sefd.model.Palestras;
import br.com.eventoseartigos.sefd.utils.HttpUtils;

/**
 * Created by gilmar on 28/12/16.
 */

public class PalestrasService {
    private static final String URL_PALESTRAS = "http://api-sefd.ifpicos.com.br/participante/palestras/?edicao={pk}";

    public static List<Palestras> getPalestras(String token, String pk) {
        try {
            String url = URL_PALESTRAS.replace("{pk}", pk);
            String json = HttpUtils.doGet(url, token);
            List<Palestras> palestrasList = PalestrasConverter.convertePalestrasParaString(json);
            return palestrasList;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
