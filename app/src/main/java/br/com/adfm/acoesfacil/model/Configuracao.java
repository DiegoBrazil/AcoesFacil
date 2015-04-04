package br.com.adfm.acoesfacil.model;

/**
 * Created by Felipe on 03/04/2015.
 */
public class Configuracao {

    private Double corretagem;
    private Double emolumento;
    private Double custodia;
    private Double impostoRenda;

    public Configuracao(){}

    public Configuracao(Double corretagem,
            Double emolumento,
            Double custodia,
            Double impostoRenda){
        this.corretagem = corretagem;
        this.emolumento = emolumento;
        this.custodia = custodia;
        this.impostoRenda = impostoRenda;
    }

}
