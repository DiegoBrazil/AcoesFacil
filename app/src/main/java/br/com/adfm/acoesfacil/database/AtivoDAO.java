package br.com.adfm.acoesfacil.database;

import java.util.List;

import br.com.adfm.acoesfacil.model.Ativo;

/**
 * Created by alexandre-ms on 02/04/15.
 */
public interface AtivoDAO  {

    /**
     *<b>Descrição:</b> Método de adição de ativo
     * @param ativo
     */
    public void adicionar(Ativo ativo);

    /**
     *<b>Descrição:</b> Método de remoção de ativo
     * @param ativo
     */
    public void remover(Ativo ativo);

    /**
     * <b>Decrição:</b> Consultar Ativo pelo Código
     * @param codigo
     * @return {@link br.com.adfm.acoesfacil.model.Ativo}
     */
    public Ativo consultarPeloAtivo(String codigo);

    /**
     **<b>Descrição:</b> Método de adição de registros
     * @return
     * {@link java.util.List} <Ativo> - Retorna uma lista de registros
     */
    public List<Ativo> listarFavoritos();


}
