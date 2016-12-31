package br.com.eventoseartigos.sefd.service;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import br.com.eventoseartigos.sefd.converter.DocumentosEnviadosConverter;
import br.com.eventoseartigos.sefd.converter.DocumentosNecessariosConveter;
import br.com.eventoseartigos.sefd.model.DocumentosEnviados;
import br.com.eventoseartigos.sefd.utils.HttpUtils;
import br.com.eventoseartigos.sefd.utils.IOUtils;

/**
 * Created by gilmar on 30/12/16.
 */

public class DocumentosNecessarioService {
    private static final String URL = "http://api-sefd.ifpicos.com.br/participante/documentos/";

    public static DocumentosEnviados setDocumento(String inscricao, String tipo, File file, String token) {
        try {
            byte[] bytes = IOUtils.toBytes(new FileInputStream(file));
            String base64 = Base64.encodeToString(bytes, Base64.NO_WRAP);

            String json = DocumentosNecessariosConveter.converteDococumentosParaJson(inscricao, tipo, base64);
            String responseJson = HttpUtils.doPost(URL, json, token);
            DocumentosEnviados docEnvJson = DocumentosEnviadosConverter.converteDocEnviadorsParaString(responseJson);
            return docEnvJson;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
