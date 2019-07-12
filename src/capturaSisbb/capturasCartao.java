/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capturaSisbb;

import br.com.bb.jibm3270.Janela3270;
import controller.JanelaSisbb;
import java.util.ArrayList;
import java.util.List;
import model.DadosIniciais;

/**
 *
 * @author f3295813
 */
public class capturasCartao {

    private List<DadosIniciais> listDadosIniciais = new ArrayList<>();
    String matricula = System.getProperty("user.name").toUpperCase();

    public List dadosIniciais(JanelaSisbb sisbb, String cpf) throws Throwable {

        int linhaConta = 15;
        int linhaCartao = 7;
              
        sisbb.Aplicativo(matricula, "CARTAO", true);
        sisbb.aguardarInd(1, 2, "VP");
        sisbb.colar(21, 19, "11");
        sisbb.teclarAguardarTroca("@E");
        sisbb.aguardarInd(1, 3, "VIP10300");
        sisbb.colar(21, 20, "01");
        sisbb.teclarAguardarTroca("@E");
        sisbb.aguardarInd(1, 3, "VIP23050");
        sisbb.colar(17, 20, "01");
        sisbb.colar(20, 20, cpf);
        sisbb.teclarAguardarTroca("@E");
                
        if (sisbb.copiar(15, 8, 4).equals("")|| sisbb.copiar(23, 23, 2).equals("inv")) {
            return listDadosIniciais;
            
        }
              
        sisbb.aguardarInd(1, 3, "VIP10321");        

        do {
            DadosIniciais di = new DadosIniciais();
            String conta = sisbb.copiar(linhaConta, 8, 8);
            String autor = sisbb.copiar(6, 19,60).trim();
            String tipoPlastico = sisbb.copiar(linhaConta, 17, 36).trim();
            
            sisbb.colar(linhaConta, 4, "X");
            sisbb.teclarAguardarTroca("@E");
            sisbb.aguardarInd(1, 3, "VIP10331");
            sisbb.teclarAguardarTroca("@9");
            sisbb.aguardarInd(1, 3, "VIP60371");
            linhaCartao = 7;

            do {
                DadosIniciais dd = new DadosIniciais();
                dd.setNrCartao(sisbb.copiar(linhaCartao, 5, 18).trim());
                dd.setNomePlastico(sisbb.copiar(linhaCartao, 24, 19).trim());
                dd.setRestricao(sisbb.copiar(linhaCartao, 50, 23).trim());
                dd.setVencido(sisbb.copiar(linhaCartao, 74, 7).trim());
                dd.setConta(conta);
                dd.setTipoPlastico(tipoPlastico);
                dd.setAutor(autor);
                listDadosIniciais.add(dd);
                linhaCartao++;
                if (linhaCartao == 21) {
                    sisbb.teclarAguardarTroca("@8");
                    linhaCartao = 7;
                    
                }
            } while (!sisbb.copiar(linhaCartao, 5, 5).equals(""));

            linhaConta++;
                   
            sisbb.teclarAguardarTroca("@3");
            sisbb.aguardarInd(1, 3, "VIP10331");
            sisbb.teclarAguardarTroca("@3");
            sisbb.aguardarInd(1, 3, "VIP10321");
            
            if(linhaConta == 21){
                sisbb.teclarAguardarTroca("@8");
                linhaConta = 15;             
                if(sisbb.copiar(23, 4, 5).equals("ltima")){
                    break;
                }}
        } while (!sisbb.copiar(linhaConta, 8, 4).equals(""));

       // sisbb.rotinaEncerramento();
        return listDadosIniciais;
    }
    
    
    
    
    

}
