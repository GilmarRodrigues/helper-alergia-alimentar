package br.com.eventoseartigos.sefd.service;

import java.io.IOException;
import java.util.List;

import br.com.eventoseartigos.sefd.converter.EventoConverter;
import br.com.eventoseartigos.sefd.model.Evento;
import br.com.eventoseartigos.sefd.utils.HttpUtils;

/**
 * Created by gilmar on 19/09/16.
 */
public class EventosService {
    private static final String URL = "http://api-sefd.ifpicos.com.br/participante/edicoes/";

    public static List<Evento> getEventos(String token) {
        try {
            String json = HttpUtils.doGet(URL, token);
            List<Evento> eventos = EventoConverter.converteEventoParaString(json);
            return eventos;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /* teste edukids
    public static String eduKids() {
        try {
            String json = HttpUtils.doGet("http://api.edukids.online/responsavel/turmas/", "fbe19443b9c811068e1313680b98a12374ddae23");
            return json;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/
}
