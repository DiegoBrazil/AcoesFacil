package br.com.adfm.acoesfacil.service;

import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import br.com.adfm.acoesfacil.model.Ativo;

/**
 * Created by Felipe on 06/04/2015.
 */
public class ServicoConsultaAcao extends AsyncTask<String,Void, Ativo > {


    private ConsultaAcoes consultaAcoes;
    private ServicoConsultInterface servicoConsultInterface;

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
    protected Ativo doInBackground(String... params) {
        String url = params[0];
        String cdAtivo = params[1];
        consultaAcoes = new ConsultaAcoesXML(url+cdAtivo);
        Ativo ativo = consultaAcoes.consultar();
        return ativo;
    }

    @Override
    protected void onPostExecute(Ativo response) {
        if(response != null){
            servicoConsultInterface.atualizarConsulta(response);
        }
        //ativo =response;
    }
}
