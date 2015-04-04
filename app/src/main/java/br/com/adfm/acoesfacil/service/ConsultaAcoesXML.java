package br.com.adfm.acoesfacil.service;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.adfm.acoesfacil.model.Ativo;

/**
 * Created by alexandre-ms on 03/04/15.
 */
public class ConsultaAcoesXML implements ConsultaAcoes {

    private String link;

    @Override
    public Ativo consultar(String codigo) {
        try {
            HttpURLConnection conn = getHttpURLConnection();
            if (conn != null) {
                InputStream is = conn.getInputStream();
                Ativo ativo = converterStreamParaAtivo(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Ativo converterStreamParaAtivo(InputStream is) {
        Ativo ativo = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            XmlPullParser parser = factory.newPullParser();
            parser.setInput(is, null);
            int evento = parser.getEventType();

            while(evento != XmlPullParser.END_DOCUMENT){
                String tagNome = parser.getName();

            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } finally {

        }

        return ativo;
    }

    private HttpURLConnection getHttpURLConnection() throws IOException {
        URL url = new URL(link);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();

        return conn;
    }

    private String converterStreamParaString(final InputStream is) {
        StringBuffer retorno = new StringBuffer();

        if (is == null){
            throw new IllegalArgumentException("Stream inválido");
        } else {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String linha = null;
                while((linha = br.readLine()) != null) {
                    retorno.append(linha);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return retorno.toString();
    }
}
