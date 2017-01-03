package br.com.eventoseartigos.sefd.service;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.eventoseartigos.sefd.R;
import br.com.eventoseartigos.sefd.converter.CertificadoConverter;
import br.com.eventoseartigos.sefd.model.Certificado;
import br.com.eventoseartigos.sefd.utils.HttpUtils;

import static br.com.eventoseartigos.sefd.converter.CertificadoConverter.converteCertificadoParaString;

/**
 * Created by gilmar on 31/12/16.
 */

public class CertificadoService {
    public static final String URL_MINICURSO = "http://api-sefd.ifpicos.com.br/participante/certificados/minicurso/";
    public static final String URL_ATIVIDADE = "http://api-sefd.ifpicos.com.br/participante/certificados/atividade/";
    public static final String URL_PALESTRAS = "http://api-sefd.ifpicos.com.br/participante/certificados/palestra/";
    public static final String URL_CODIGO_CERTIFICADO = "http://api-sefd.ifpicos.com.br/participante/certificados/{pk}/";

    public static List<Certificado> getCertificados(String token, Context context) {
        try {
            List<Certificado> certificados = new ArrayList<>();

            String minucirsosJdon = HttpUtils.doGet(URL_MINICURSO, token);
            certificados = CertificadoConverter.converteCertificadoParaString(minucirsosJdon, certificados, context.getString(R.string.text_minicurso));

            String atividadesJson = HttpUtils.doGet(URL_ATIVIDADE, token);
            certificados = CertificadoConverter.converteCertificadoParaString(atividadesJson, certificados, context.getString(R.string.text_atividade));

            String palestrasJson = HttpUtils.doGet(URL_PALESTRAS, token);
            certificados = CertificadoConverter.converteCertificadoParaString(palestrasJson, certificados, context.getString(R.string.text_palestras));

            return certificados;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String codigoBase64(String token, String pk) {
        try {
            String url = URL_CODIGO_CERTIFICADO.replace("{pk}", pk);
            String codigo = HttpUtils.doGet(url, token);
            return codigo;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
