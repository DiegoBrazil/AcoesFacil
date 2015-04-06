package br.com.adfm.acoesfacil.service;

import android.util.Log;

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

    public ConsultaAcoesXML(String link) {
        this.link = link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public Ativo consultar() {
        HttpURLConnection conn = null;
        try {
            String strUrl = this.link;
            Log.d("Consulta Acoes", strUrl);

            String xml = null;
            URL url = new URL(strUrl);

            conn = (HttpURLConnection)url.openConnection();
            //conn.setReadTimeout(10000 /* milliseconds */);
            //conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            return converterParaAtivo(conn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Ativo converterParaAtivo(InputStream is) {
        Ativo ativo = null;
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();

            // Carrega com o stream
            parser.setInput(is, null);

            // Pega o tipo do evento
            int eventType = parser.getEventType();

            // Pecorre todas as tags do documento
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if(eventType == XmlPullParser.START_DOCUMENT) {
                    Log.d("XML -> LOG", "START DOC ");
                } else if(eventType == XmlPullParser.END_DOCUMENT) {
                    Log.d("XML -> LOG", "End DOC");
                } else if(eventType == XmlPullParser.START_TAG) {
                    String tagName = parser.getName();
                    Log.d("XML -> LOG", "Start tag " + tagName);

                    String codigo = parser.getAttributeValue(null,"Codigo");
                    Log.d("XML -> LOG", "Codigo" + codigo);
                    String nome = parser.getAttributeValue(null,"Nome");
                    String data = parser.getAttributeValue(null,"Data");
                    Log.d("XML -> LOG", "Data" + data);
                    String abertura = parser.getAttributeValue(null,"Abertura");
                    String minimo = parser.getAttributeValue(null,"Minimo");
                    String maximo = parser.getAttributeValue(null,"Maximo");
                    String medio = parser.getAttributeValue(null,"Medio");
                    String ultimo = parser.getAttributeValue(null,"Ultimo");
                    String oscilacao = parser.getAttributeValue(null,"Oscilacao");

                    ativo = new Ativo(codigo, 0d,0d);
                    if (abertura != null) {
                        ativo.setAbertura(Double.parseDouble(abertura.replace(",", ".")));
                    }
                    if (minimo != null){
                        ativo.setMinimo(Double.parseDouble(minimo.replace(",", ".")));
                    }
                }else if(eventType == XmlPullParser.END_TAG) {
                    System.out.println("End tag "+parser.getName());
                } else if(eventType == XmlPullParser.TEXT) {
                    System.out.println("Text " + parser.getText());
                }

                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ativo;
    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                if (!line.contains("?xml version")){
                    sb.append(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}
