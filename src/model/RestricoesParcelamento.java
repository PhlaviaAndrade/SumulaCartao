/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author f3295813
 */
public class RestricoesParcelamento {

    private String tipo;
    private String valor;
    private String dtaRegistro;
    private String modalidade;
    private String dtaBaixa;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDtaRegistro() {
        return dtaRegistro;
    }

    public void setDtaRegistro(String dtaRegistro) {
        this.dtaRegistro = dtaRegistro;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public String getDtaBaixa() {
        return dtaBaixa;
    }

    public void setDtaBaixa(String dtaBaixa) {
        this.dtaBaixa = dtaBaixa;
    }

}
