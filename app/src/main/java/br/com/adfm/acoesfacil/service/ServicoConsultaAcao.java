package br.com.adfm.acoesfacil.service;

import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.adfm.acoesfacil.model.Ativo;

/**
 * Created by Felipe on 06/04/2015.
 */
public class ServicoConsultaAcao extends AsyncTask< String,Void, List<Ativo> > {


    private ConsultaAcoes consultaAcoes;
    private ServicoConsultInterface servicoConsultInterface;
    private String url ="http://www.bmfbovespa.com.br/Pregao-Online/ExecutaAcaoAjax.asp?CodigoPapel=";

    public ServicoConsultaAcao(ServicoConsultInterface servicoConsultInterface){
        this.servicoConsultInterface = servicoConsultInterface;
    }
    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected List<Ativo> doInBackground(String... params) {
        List<Ativo> retorno = new ArrayList<Ativo>();
        for (int i = 0; i < params.length; i++) {
            consultaAcoes = new ConsultaAcoesXML(url+params[i]);
            Ativo ativo = consultaAcoes.consultar();
            retorno.add(ativo);
        }
        return retorno;
    }

    @Override
    protected void onPostExecute(List<Ativo> response) {
        if(response != null){
            servicoConsultInterface.atualizarConsulta(response);
        }
        //ativo =response;
    }
}
