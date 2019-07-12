/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capturaSisbb;

import br.com.bb.jibm3270.RoboException;
import controller.JanelaSisbb;
import controller.TelaPrincipalController;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.BloqueioDataValor;
import model.BloqueioDatasCaptura;
import model.ContaCartao;
import model.DadosIniciais;
import model.TransacaoNaoAutorizada;

/**
 *
 * @author f3295813
 */
public class CapturaBloqueioRestricao {

    List teste2;

    public boolean cadastroRestritivo(String matricula, List<BloqueioDataValor> listaBloqueioDataValor, List<DadosIniciais> listaOperacoesCaptura, String cpf, String npj, String autor, JanelaSisbb sisbb, TelaPrincipalController tp) throws RoboException, InterruptedException {

       

        for (DadosIniciais t : listaOperacoesCaptura) {
             int linhaAnotacao = 15;
            String cartao = t.getNrCartao().substring(0, 16);

            while (!sisbb.copiar(1, 3, 8).equals("VIP10300")) {
                sisbb.teclarAguardarTroca("@3");
                Thread.sleep(300);

            }

            sisbb.aguardarInd(1, 3, "VIP10300");
            sisbb.colar(21, 20, "1");
            sisbb.teclarAguardarTroca("@E");
            sisbb.aguardarInd(1, 3, "VIP23050");

            sisbb.colar(17, 20, "2");
            sisbb.colar(18, 20, cartao);
            sisbb.teclarAguardarTroca("@E");
            
            
            if(sisbb.copiar(1, 3, 8).equals("VIP10311")){
                
            sisbb.aguardarInd(1, 3, "VIP10311");
            sisbb.teclarAguardarTroca("@6");

            if (sisbb.copiar(1, 3, 8).equals("VIP0112R")) {
                return false;
            }

            sisbb.aguardarInd(5, 3, "Nome");

            do {

                if (sisbb.copiar(linhaAnotacao, 6, 3).equals("007")) {
                    return true;

                }

                linhaAnotacao++;

                if (linhaAnotacao == 21) {
                    sisbb.teclarAguardarTroca("@8");
                    linhaAnotacao = 15;
                    if (sisbb.copiar(22, 4, 5).equals("ltima")) {
                        break;
                    }
                }
            } while (!sisbb.copiar(linhaAnotacao, 6, 3).equals(""));
            
        }else{
             sisbb.teclar("@E");
                
            }

        }
        return false;
    }

    public boolean creditoInsuficiente(String matricula, List<BloqueioDataValor> listaBloqueioDataValor, List<BloqueioDatasCaptura> listaBloqueioDatas, List<DadosIniciais> listaOperacoesCaptura, String cpf, JanelaSisbb sisbb, TelaPrincipalController tp, List transNaoAutorizada, String vencido, String suspeitaFraude) throws RoboException, ParseException, InterruptedException {

//        SimpleDateFormat out = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.ENGLISH);
//        SimpleDateFormat in = new SimpleDateFormat("ddMMyyyy");
        Set<ContaCartao> listaCC = new HashSet<>();
        String contaAntiga = "";
        listaOperacoesCaptura.sort(Comparator.comparing(DadosIniciais::getConta));
        for (DadosIniciais t : listaOperacoesCaptura) {
            Set<String> ListSet = new HashSet<>();
            ContaCartao cc = new ContaCartao();
            String conta;
            conta = t.getConta();
            for (DadosIniciais f : listaOperacoesCaptura) {

                String cartao = f.getNrCartao().substring(0, 16);
                String contaVer = f.getConta();

                if (contaVer.equals(conta)) {
                    ListSet.add(cartao);
                }
            }
            if (!contaAntiga.equals(conta)) {
                cc.setConta(conta);
                cc.setListaCartao(ListSet);
                listaCC.add(cc);
            }
            contaAntiga = conta;
        }
        for (ContaCartao tConta : listaCC) {
            int linhaVer = 12;
            String conta = tConta.getConta();
            Set<String> listaCartao = new HashSet<>();
            listaCartao = tConta.getListaCartao();
            while (!sisbb.copiar(1, 3, 8).equals("VIP10300")) {
                sisbb.teclarAguardarTroca("@3");
            }
            sisbb.aguardarInd(1, 3, "VIP10300");
            sisbb.colar(21, 20, "41");
            sisbb.teclarAguardarTroca("@E");
            sisbb.aguardarInd(1, 3, "VIP23961");
            sisbb.colar(17, 26, "1");
            sisbb.colar(20, 26, cpf);
            sisbb.teclarAguardarTroca("@E");
            sisbb.aguardarInd(1, 18, "SISBB");
            if (sisbb.copiar(1, 3, 8).equals("VIP10080")) {
                while (!sisbb.copiar(linhaVer, 8, 8).equals(conta)) {
                    linhaVer++;
                    if (linhaVer == 22) {
                        sisbb.teclarAguardarTroca("@8");
                        linhaVer = 12;
                    }
                }
                sisbb.colar(linhaVer, 4, "X");
                sisbb.teclarAguardarTroca("@E");
            }
            sisbb.aguardarInd(1, 3, "VIP65291");
            sisbb.colar(21, 10, "23");
            Thread.sleep(200); 
            sisbb.teclarAguardarTroca("@E");
            sisbb.aguardarInd(1, 3, "VIP60371");
            for (String cartao : listaCartao) {
                cartao = cartao.substring(0, 16);
                int linhaCartao = 7;
                while (!sisbb.copiar(linhaCartao, 5, 16).equals(cartao)) {
                    linhaCartao++;

                    if (linhaCartao == 20) {
                        sisbb.teclarAguardarTroca("@8");
                        linhaCartao = 7;
                    }
                }
                sisbb.colar(linhaCartao, 3, "X");
                sisbb.teclarAguardarTroca("@E");
                sisbb.aguardarInd(1, 3, "VIP20711");
                Thread.sleep(400);
                if (!sisbb.copiar(11, 8, 3).equals("")) {
                    for (BloqueioDatasCaptura f : listaBloqueioDatas) { // lista das datas da tabela
                        String dataInicial = f.getDataInicio().replace(".", "");
                        String dataFim = f.getDataFim().replace(".", "");
//                Date dataF = in.parse(dataFim);

                        while (!sisbb.copiar(23, 4, 7).equals("rimeira")) {
                            sisbb.teclarAguardarTroca("@7");
                        }

                        String data_parte1 = dataInicial.substring(0, 2); //pega o dia
                        String data_parte2 = dataInicial.substring(2, 4); //pega o mês
                        String data_parte3 = dataInicial.substring(4, 8); //pega o ano
                        String dataFim1 = dataFim.substring(0, 2); //pega o dia
                        String dataFim2 = dataFim.substring(2, 4); //pega o mês
                        String dataFim3 = dataFim.substring(4, 8); //pega o ano
                        int dataInicio_manipulada = Integer.parseInt(data_parte3 + data_parte2 + data_parte1);
                        int dataFim_manipulada = Integer.parseInt(dataFim3 + dataFim2 + dataFim1);
//                LocalDate date;
//                date = dataF.toInstant().atZone( ZoneId.systemDefault() ).toLocalDate();
                        int linhaData = 11;
                        do {
                            try {
                                Thread.sleep(100);
                                String data_sisbb = sisbb.copiar(linhaData, 8, 10).replace(".", "");
                                String data_Sis_parte1 = data_sisbb.substring(0, 2); //pega o dia
                                String data_Sis_parte2 = data_sisbb.substring(2, 4); //pega o mês
                                String data_Sis_parte3 = data_sisbb.substring(4, 8); //pega o ano
                                int data_sisbb_manipulada = Integer.parseInt(data_Sis_parte3 + data_Sis_parte2 + data_Sis_parte1);

                                if (data_sisbb_manipulada < dataInicio_manipulada) {
                                    break;

                                }

                                if (data_sisbb_manipulada <= dataFim_manipulada && data_sisbb_manipulada >= dataInicio_manipulada && sisbb.copiar(linhaData, 59, 15).equals("CREDITO INSUFIC") || !sisbb.copiar(linhaData, 59, 8).equals("APROVADO") ) {
                                   
                                    
                                    
                                    if (sisbb.copiar(1, 3, 8).equals("VIP20711")) {
                                        TransacaoNaoAutorizada tn = new TransacaoNaoAutorizada();
                                        sisbb.colar(linhaData, 4, "X");
                                        tn.setPlastico(cartao);
                                        tn.setDataOcorrencia(sisbb.copiar(linhaData, 8, 10));
                                        tn.setValorCompra(sisbb.copiar(linhaData, 46, 12).trim());
                                        sisbb.teclarAguardarTroca("@E");
                                        tn.setRazaoRecusa(sisbb.copiar(10, 63, 18).trim());
                                        tn.setResposta(sisbb.copiar(11, 17, 18).trim());
                                        tn.setVencimento(sisbb.copiar(5, 17, 12).trim());
                                        sisbb.teclarAguardarTroca("@9");
                                        tn.setValorLimite(sisbb.copiar(12, 69, 8).trim());
                                        sisbb.teclarAguardarTroca("@3");
                                        transNaoAutorizada.add(tn);
                                        sisbb.teclarAguardarTroca("@3");
                                    } else {
                                        TransacaoNaoAutorizada tn = new TransacaoNaoAutorizada();
                                        sisbb.colar(linhaData, 4, "X");
                                        tn.setValorCompra(sisbb.copiar(linhaData, 46, 12).trim());
                                        sisbb.teclarAguardarTroca("@E");
                                        tn.setRazaoRecusa(sisbb.copiar(11, 63, 18).trim());
                                        tn.setResposta(sisbb.copiar(12, 17, 18).trim());
                                        tn.setVencimento(sisbb.copiar(6, 17, 12).trim());
                                        sisbb.teclarAguardarTroca("@9");
                                        tn.setValorLimite(sisbb.copiar(12, 63, 8).trim());
                                        sisbb.teclarAguardarTroca("@3");
                                        transNaoAutorizada.add(tn);
                                        sisbb.teclarAguardarTroca("@3");

                                    }
                                }
                                linhaData++;
                                if (linhaData == 21 || sisbb.copiar(linhaData, 8, 3).equals("")) {
                                    sisbb.teclarAguardarTroca("@8");
                                    linhaData = 11;
                                    if (sisbb.copiar(23, 4, 5).equals("ltima")) {
                                        if (!sisbb.copiar(24, 59, 7).equals("Autoriz")) {
                                            break;
                                        }

                                        sisbb.teclarAguardarTroca("@9");
                                    }
                                }
                            } catch (RoboException | InterruptedException | NumberFormatException e) {
                                System.out.println(e);
                            }
                        } while (!sisbb.copiar(linhaData, 8, 3).equals(""));
                    }
                }

                while (!sisbb.copiar(1, 3, 8).equals("VIP60371")) {
                    sisbb.teclarAguardarTroca("@3");
                    Thread.sleep(300);
                    
                }
            }
            while (!sisbb.copiar(1, 3, 8).equals("VIP60371")) {
                sisbb.teclarAguardarTroca("@3");
                Thread.sleep(300);
            }
        }
        return !transNaoAutorizada.isEmpty(); // testar

    }
    
    
    public void restricoes (String matricula, JanelaSisbb sisbb, String cpf) throws RoboException, Throwable{
        
        sisbb.teclar("@5");

        sisbb.colar(15, 14, "Clientes");
        sisbb.aguardarInd(1, 2, "SBBP6130");
        
          if (sisbb.copiar(5, 38, 7).equals("ATENÇÃO")) {
            sisbb.teclarAguardarTroca("@3");
            Thread.sleep(300);
        }

        if (sisbb.copiar(3, 36, 10).equals("Comunicado")) {
            sisbb.teclarAguardarTroca("@E");
            Thread.sleep(300);
        }
        
        
        sisbb.colar(21, 20, "01");
        sisbb.teclar("@E");
        sisbb.aguardarInd(1, 8, "MCIM0000");
        sisbb.colar(19, 18, "01");
        sisbb.teclar("@E");
        sisbb.aguardarInd(1, 8, "MCIM001A");
        sisbb.colar(12, 13, cpf);
        sisbb.teclar("@E");

        sisbb.colar(21, 41, "14");
        sisbb.teclarAguardarTroca("@E");
        sisbb.aguardarInd(1, 3, "ACPM140B");
        
        int linhaImpedimento = 10;
        
        while(!sisbb.copiar(linhaImpedimento, 6, 2).equals("")){
            
            if(sisbb.copiar(linhaImpedimento, 39, 20).trim().equals("Relativa") || sisbb.copiar(linhaImpedimento, 39, 20).trim().equals("Absoluta") || sisbb.copiar(linhaImpedimento, 39, 20).trim().equals("Consta") ){
                
                
                
                
                
            }
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            linhaImpedimento++;
            
            
            
            
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        


    }
    
    

}
