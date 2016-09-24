package br.com.eventoseartigos.sefd.service;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import br.com.eventoseartigos.sefd.utils.IOUtils;

/**
 * Created by gilmar on 14/09/16.
 */
public class WebClient {
    private static final boolean LOG_ON = false;

    public String post(String json) {
        try {
            URL url = new URL("http://api-sefd.ifpicos.com.br/api-token-auth/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            connection.setDoOutput(true);

            PrintStream output = new PrintStream(connection.getOutputStream());
            output.println(json);

            connection.connect();

            Scanner scanner = new Scanner(connection.getInputStream());
            String resposta = scanner.next();

            return resposta;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String get(String token) {
        try {
            URL url = new URL("http://api-sefd.ifpicos.com.br/participante/edicoes/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("Content-type", "application/x-www-form-urlendcoded");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Token " + token);
            connection.setRequestMethod("GET");
            //connection.addRequestProperty("Authorization", "Token " + token);

            connection.setDoInput(true);
            connection.connect();

            Scanner scanner = new Scanner(connection.getInputStream());
            String resposta = scanner.next();

            return resposta;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static String doGet(String url, String token) throws IOException {
        return doGet(url, (Map) null, "UTF-8", token);
    }

    public static String doGet(String url, Map<String, String> params, String charset, String token) throws IOException {
        String queryString = getQueryString(params, (String) null);
        if (queryString != null && queryString.trim().length() > 0) {
            url = url + "?" + queryString;
        }

        if (LOG_ON) {
            Log.d("Http", ">> Http.doGet: " + url);
        }

        java.net.URL u = new URL(url);
        HttpURLConnection conn = null;
        String s = null;

        try {
            conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");

            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty ("Authorization", "Token " + token);

            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoOutput(false);
            conn.setDoInput(true);
            conn.connect();


            InputStream e = conn.getInputStream();
            s = IOUtils.toString(e, charset);
            if (LOG_ON) {
                Log.d("Http", "<< Http.doGet: " + s);
            }

            e.close();
        } catch (IOException var11) {
            throw var11;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }

        }

        return s;
    }

    public static String getQueryString(Map<String, String> params, String charsetToEncode) throws IOException {
        if (params != null && params.size() != 0) {
            String urlParams = null;
            Iterator var3 = params.keySet().iterator();

            while (var3.hasNext()) {
                String chave = (String) var3.next();
                Object objValor = params.get(chave);
                if (objValor != null) {
                    String valor = objValor.toString();
                    if (charsetToEncode != null) {
                        valor = URLEncoder.encode(valor, charsetToEncode);
                    }

                    urlParams = urlParams == null ? "" : urlParams + "&";
                    urlParams = urlParams + chave + "=" + valor;
                }
            }

            return urlParams;
        } else {
            return null;
        }
    }
}
