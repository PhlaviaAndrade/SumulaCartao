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
public class RestricoesTerceiros {
    
    private String tipo;
    private String dtaRegistro;
    private String dtaBaixa;
    private  boolean selected; 

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDtaRegistro() {
        return dtaRegistro;
    }

    public void setDtaRegistro(String dtaRegistro) {
        this.dtaRegistro = dtaRegistro;
    }

    public String getDtaBaixa() {
        return dtaBaixa;
    }

    public void setDtaBaixa(String dtaBaixa) {
        this.dtaBaixa = dtaBaixa;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
    
    
    
    
    
    
    
}
