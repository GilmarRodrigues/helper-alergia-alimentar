package br.com.eventoseartigos.sefd.service;

import android.util.Log;

import java.io.IOException;

import br.com.eventoseartigos.sefd.converter.DefinirNomeCertificadoConverter;
import br.com.eventoseartigos.sefd.utils.HttpUtils;

/**
 * Created by gilmar on 02/01/17.
 */

public class DefinirNomeCertificadoService {
    private static final String URL = "http://api-sefd.ifpicos.com.br/participante/certificados/nome/{pk}/";

    public static String setNome(String token, String nome, String pk) {
        try {
            String url = URL.replace("{pk}", pk);
            String json = DefinirNomeCertificadoConverter.converteNomeCertificadoParaJson(nome);
            Log.i("Script", "JSON " + json);
            String response = HttpUtils.doPut(url, json, token);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
