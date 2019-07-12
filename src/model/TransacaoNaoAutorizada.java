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
public class TransacaoNaoAutorizada {
   
   private String plastico; 
   private String vencimento;
   private String resposta;
   private String razaoRecusa;
   private String valorLimite;
   private String valorCompra;
   private String dataOcorrencia;

    public String getPlastico() {
        return plastico;
    }

    public void setPlastico(String plastico) {
        this.plastico = plastico;
    }
   
   

    public String getVencimento() {
        return vencimento;
    }

    public void setVencimento(String vencimento) {
        this.vencimento = vencimento;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public String getRazaoRecusa() {
        return razaoRecusa;
    }

    public void setRazaoRecusa(String razaoRecusa) {
        this.razaoRecusa = razaoRecusa;
    }

    public String getValorLimite() {
        return valorLimite;
    }

    public void setValorLimite(String valorLimite) {
        this.valorLimite = valorLimite;
    }

    public String getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(String valorCompra) {
        this.valorCompra = valorCompra;
    }

    public String getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(String dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }
    
    
    
}
