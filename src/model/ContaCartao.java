/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author f3295813
 */
public class ContaCartao {
    
    private String conta;
    private Set <String> listaCartao = new HashSet<>();   

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public Set<String> getListaCartao() {
        return listaCartao;
    }

    public void setListaCartao(Set<String> listaCartao) {
        this.listaCartao = listaCartao;
    }

  
    
    
    
}
