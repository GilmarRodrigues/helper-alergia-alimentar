package br.com.eventoseartigos.sefd.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gilmar on 30/12/16.
 */

public class FormatUtils {
    public static String formtDate(String date) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date data = fmt.parse(date);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String str = format.format(data);
            return str;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formtHora(String hora) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
            Date time = fmt.parse(hora);
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            String str = format.format(time);
            return str;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
