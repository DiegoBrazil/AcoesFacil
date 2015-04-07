package br.com.adfm.acoesfacil.service;

import br.com.adfm.acoesfacil.model.Ativo;

/**
 * Created by alexandre-ms on 07/04/15.
 */
public interface AtivoHandler {

    public void notificarAtivo(Ativo ativo);

    public void notificarMensagem(String msg);
}
