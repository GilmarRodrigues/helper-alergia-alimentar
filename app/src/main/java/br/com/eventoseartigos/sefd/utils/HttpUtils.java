package br.com.eventoseartigos.sefd.utils;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by gilmar on 14/09/16.
 */
public class HttpUtils {
    private static final boolean LOG_ON = false;

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

    public static String doPost(String url, String json) throws IOException {
        if (LOG_ON) {
            Log.d("Http", ">> Http.doPost: " + url);
        }

        URL u = new URL(url);
        HttpURLConnection conn = null;

        String s = null;

        try {
            conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.connect();

            if (json != null) {
                OutputStream e = conn.getOutputStream();
                e.write(json.getBytes());
                e.flush();
                e.close();
            }

            InputStream el = null;
            if (conn.getResponseCode() != 400) {
                el = conn.getInputStream();
            } else {
                el = conn.getErrorStream();
            }
            s = IOUtils.toString(el, "UTF-8");

            return s;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String doPost(String url, String json, String token) throws IOException {
        if (LOG_ON) {
            Log.d("Http", ">> Http.doPost: " + url);
        }

        URL u = new URL(url);
        HttpURLConnection conn = null;

        String s = null;

        try {
            conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty ("Authorization", "Token " + token);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.connect();

            if (json != null) {
                OutputStream e = conn.getOutputStream();
                e.write(json.getBytes());
                e.flush();
                e.close();
            }

            InputStream el = null;
            if (conn.getResponseCode() != 400) {
                el = conn.getInputStream();
            } else {
                el = conn.getErrorStream();
            }
            s = IOUtils.toString(el, "UTF-8");

            return s;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String doPost(String url, Map<String, String> params, String charset) throws IOException {
        String queryString = getQueryString(params, charset);
        byte[] bytes = params != null ? queryString.getBytes(charset) : null;
        if (LOG_ON) {
            Log.d("Http", "Http.doPost: " + url + "?" + params);
        }

        return doPost(url, bytes, charset);
    }

    public static String doPost(String url, byte[] params, String charset) throws IOException {
        if (LOG_ON) {
            Log.d("Http", ">> Http.doPost: " + url);
        }

        URL u = new URL(url);
        HttpURLConnection conn = null;

        String s = null;

        try {
            conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.connect();
            if (params != null) {
                OutputStream e = conn.getOutputStream();
                e.write(params);
                e.flush();
                e.close();
            }

            InputStream e1 = conn.getInputStream();
            Log.i("LOG", "el " + e1.read());
            s = IOUtils.toString(e1, charset);
            Log.i("LOG", "s " + s);
            if (LOG_ON) {
                Log.d("Http", "<< Http.doPost: " + s);
            }
            e1.close();
        } catch (IOException var10) {
            throw var10;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }

        }

        return s;
    }

    public static String doPut(String url, String json, String token) throws IOException {
        if (LOG_ON) {
            Log.d("Http", ">> Http.doPost: " + url);
        }

        URL u = new URL(url);
        HttpURLConnection conn = null;

        String s = null;

        try {
            conn = (HttpURLConnection) u.openConnection();
                conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty ("Authorization", "Token " + token);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.connect();

            if (json != null) {
                OutputStream e = conn.getOutputStream();
                e.write(json.getBytes());
                e.flush();
                e.close();
            }

            InputStream el = null;
            if (conn.getResponseCode() != 400) {
                el = conn.getInputStream();
            } else {
                el = conn.getErrorStream();
            }
            s = IOUtils.toString(el, "UTF-8");

            return s;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
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
