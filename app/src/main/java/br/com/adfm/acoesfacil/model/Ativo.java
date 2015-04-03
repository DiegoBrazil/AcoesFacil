package br.com.adfm.acoesfacil.model;

import java.util.Date;

/**
 * Created by alexandre-ms on 01/04/15.
 * <b>Descrição:</b> Entidade de Ativos/Ações do Sistema
 */
public class Ativo {

    private String codigo;
    private Double precoCompra;
    private Double quantidadeCompra;
    private Date data;
    private Double minimo;
    private Double maximo;
    private Double medio;
    private Double abertura;
    private Double ultimo;
    private Double oscilacao;

    public Ativo() {
        super();
    }

    public Ativo(String codigo, Double precoCompra, Double quantidadeCompra) {
        this();
        this.codigo = codigo;
        this.precoCompra = precoCompra;
        this.quantidadeCompra = quantidadeCompra;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Double getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(Double precoCompra) {
        this.precoCompra = precoCompra;
    }

    public Double getQuantidadeCompra() {
        return quantidadeCompra;
    }

    public void setQuantidadeCompra(Double quantidadeCompra) {
        this.quantidadeCompra = quantidadeCompra;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Double getMinimo() {
        return minimo;
    }

    public void setMinimo(Double minimo) {
        this.minimo = minimo;
    }

    public Double getMaximo() {
        return maximo;
    }

    public void setMaximo(Double maximo) {
        this.maximo = maximo;
    }

    public Double getMedio() {
        return medio;
    }

    public void setMedio(Double medio) {
        this.medio = medio;
    }

    public Double getAbertura() {
        return abertura;
    }

    public void setAbertura(Double abertura) {
        this.abertura = abertura;
    }

    public Double getUltimo() {
        return ultimo;
    }

    public void setUltimo(Double ultimo) {
        this.ultimo = ultimo;
    }

    public Double getOscilacao() {
        return oscilacao;
    }

    public void setOscilacao(Double oscilacao) {
        this.oscilacao = oscilacao;
    }
}
