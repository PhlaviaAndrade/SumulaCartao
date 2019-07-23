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
public class DadosIniciais {
    
 private  boolean selected; 
 private  String conta; 
 private  String tipoPlastico;
 private  String nrCartao;
 private  String nomePlastico;
 private  String restricao;
 private  String vencido;
 private String autor;
 

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
 

    public DadosIniciais() {
    }

     public boolean isSelected() {
        return selected;
    }
     

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getTipoPlastico() {
        return tipoPlastico;
    }

    public void setTipoPlastico(String tipoPlastico) {
        this.tipoPlastico = tipoPlastico;
    }

    public String getNrCartao() {
        return nrCartao;
    }

    public void setNrCartao(String nrCartao) {
        this.nrCartao = nrCartao;
    }

    public String getNomePlastico() {
        return nomePlastico;
    }

    public void setNomePlastico(String nomePlastico) {
        this.nomePlastico = nomePlastico;
    }

    public String getRestricao() {
        return restricao;
    }

    public void setRestricao(String restricao) {
        this.restricao = restricao;
    }

    public String getVencido() {
        return vencido;
    }

    public void setVencido(String vencido) {
        this.vencido = vencido;
    }

    
   
  
}
