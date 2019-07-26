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
import model.BloqueioCorrespondencia;
import model.BloqueioDataValor;
import model.BloqueioDatasCaptura;
import model.ContaCartao;
import model.DadosIniciais;
import model.RestricoesBB;
import model.RestricoesReplicadas;
import model.RestricoesTerceiros;
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

            if (sisbb.copiar(1, 3, 8).equals("VIP10311")) {

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

            } else {
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

                                if (data_sisbb_manipulada <= dataFim_manipulada && data_sisbb_manipulada >= dataInicio_manipulada && sisbb.copiar(linhaData, 59, 15).equals("CREDITO INSUFIC") || !sisbb.copiar(linhaData, 59, 8).equals("APROVADO")) {

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
                                        tn.setPlastico(cartao);
                                        tn.setDataOcorrencia(sisbb.copiar(linhaData, 8, 10));
                                        tn.setValorCompra(sisbb.copiar(linhaData, 46, 12).trim());
                                        sisbb.teclarAguardarTroca("@E");
                                        tn.setRazaoRecusa(sisbb.copiar(11, 63, 18).trim());
                                        tn.setResposta(sisbb.copiar(12, 17, 18).trim());
                                        tn.setVencimento(sisbb.copiar(6, 17, 12).trim());
                                        sisbb.teclarAguardarTroca("@9");
                                        tn.setValorLimite(sisbb.copiar(12, 65, 12).trim());
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
                                        Thread.sleep(300);
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

    public List restricoesBB(JanelaSisbb sisbb, String cpf, String dataRestFim, String dataRestInicio, boolean restTudo) throws RoboException, InterruptedException {

        List<RestricoesBB> listRestricoesBB = new ArrayList<>();
        int linhaOpcao = 10;
        String opcaoTipo = "01";
        String tipoRestricao = "";

        sisbb.teclarAguardarTroca("@5");
        sisbb.colar(15, 14, "CLIENTES");
        sisbb.aguardarInd(1, 2, "SBBP6130");
        sisbb.colar(21, 20, "01");
        sisbb.teclarAguardarTroca("@E");
        sisbb.colar(19, 18, "01");
        sisbb.teclarAguardarTroca("@E");
        sisbb.colar(12, 13, cpf);
        sisbb.teclarAguardarTroca("@E");
        sisbb.colar(21, 41, "14");
        sisbb.teclarAguardarTroca("@E");

        while (!tipoRestricao.equals("Replicaçoes")) {

            sisbb.colar(21, 24, "  ");
            sisbb.colar(21, 24, opcaoTipo);
            sisbb.teclarAguardarTroca("@E");

            if (!sisbb.copiar(23, 4, 11).equals("etalhamento")) {

                sisbb.aguardarInd(1, 3, "ACPM14AB");

                if (!sisbb.copiar(linhaOpcao, 39, 4).equals("Nada")) {

                    int linha_rest = 10;

                    do {

                        RestricoesBB res = new RestricoesBB();

                        if ((sisbb.copiar(linha_rest, 42, 5).contains("RELAT")) || sisbb.copiar(linha_rest, 42, 5).contains("ABSOL")) {

                            String opcao = sisbb.copiar(linha_rest, 3, 2).trim();
                            sisbb.colar(21, 24, opcao);

                            sisbb.teclar("@E");
                            sisbb.aguardarInd(1, 3, "ACPM14BB");

                            String valor = sisbb.copiar(9, 68, 13).trim();

                            if (valor.equals("")) {
                                res.setValor("-");

                            } else {
                                res.setValor(valor);
                            }

                            res.setDtaRegistro(sisbb.copiar(19, 61, 10));

                            String modalidade = sisbb.copiar(12, 21, 25).trim();

                            if (modalidade.equals("")) {
                                res.setModalidade("-");

                            } else {
                                res.setModalidade(modalidade);
                            }

                            res.setTipo(sisbb.copiar(8, 21, 25).trim());

                            sisbb.teclarAguardarTroca("@3");
                            Thread.sleep(100);
                            sisbb.aguardarInd(1, 3, "ACPM14AB");
                            res.setDtaBaixa(sisbb.copiar(linha_rest, 71, 10));

                            sisbb.colar(21, 24, "  ");

                            listRestricoesBB.add(res);

                        }

                        if (linha_rest == 19) {

                            if (sisbb.copiar(1, 3, 8).equals("ACPM14BB")) {
                                sisbb.teclarAguardarTroca("@3");
                                Thread.sleep(200);
                                sisbb.colar(21, 24, "  ");

                            }

                            Thread.sleep(200);
                            sisbb.teclarAguardarTroca("@8");
                            Thread.sleep(200);

                            if (sisbb.copiar(22, 3, 4).equals("Mais")) {
                                Thread.sleep(200);

                                sisbb.colar(22, 38, "s");
                                Thread.sleep(200);
                                sisbb.teclar("@E");
                                Thread.sleep(200);

                            }

                            linha_rest = 9;

                        }

                        linha_rest++;

                        if (sisbb.copiar(1, 3, 8).equals("ACPM14BB")) {
                            sisbb.teclarAguardarTroca("@3");
                            Thread.sleep(100);
                            sisbb.colar(21, 24, "  ");

                        }

                    } while (!sisbb.copiar(linha_rest, 17, 5).equals("") & !sisbb.copiar(23, 3, 6).equals("Última"));

                    sisbb.teclarAguardarTroca("@3");
                    sisbb.aguardarInd(1, 3, "ACPM140B");

                }

            }
            linhaOpcao++;
            tipoRestricao = sisbb.copiar(linhaOpcao, 6, 24).trim();
            opcaoTipo = sisbb.copiar(linhaOpcao, 4, 1);

        }

        String data_parte1 = dataRestInicio.substring(0, 2); //pega o mês
        String data_parte2 = dataRestInicio.substring(2, 6); //pega o ano
        String dataFim1 = dataRestFim.substring(0, 2); //pega o mês
        String dataFim2 = dataRestFim.substring(2, 6); //pega o ano

        int dataInicio_manipulada = Integer.parseInt(data_parte2 + data_parte1);
        int dataFim_manipulada = Integer.parseInt(dataFim2 + dataFim1);

        if (!restTudo) {

            List<RestricoesBB> listRestricoesBB2 = new ArrayList<>();

            for (RestricoesBB r : listRestricoesBB) {

                String data_sisbb = r.getDtaRegistro().replace("/", "");
                String data_Sis_parte1 = data_sisbb.substring(2, 4); //pega o dia
                String data_Sis_parte2 = data_sisbb.substring(4, 8); //pega o ano
                int data_sisbb_manipulada = Integer.parseInt(data_Sis_parte2 + data_Sis_parte1);

                if (data_sisbb_manipulada <= dataFim_manipulada && data_sisbb_manipulada >= dataInicio_manipulada) {
                    listRestricoesBB2.add(r);
                }
            }
            listRestricoesBB = listRestricoesBB2;
        }

        return listRestricoesBB;

    }

    public List comunicacaoCliente(String matricula, JanelaSisbb sisbb, String cpf) throws RoboException {

        List<BloqueioCorrespondencia> listaCorrespondencia = new ArrayList<>();

        // sisbb.teclarAguardarTroca("@5");
        sisbb.teclarAguardarTroca("@5");
        sisbb.colar(15, 14, "INI");
        sisbb.teclarAguardarTroca("@E");
        sisbb.aguardarInd(1, 3, "INIM0000");
        sisbb.colar(19, 25, "14");
        sisbb.teclarAguardarTroca("@E");
        sisbb.aguardarInd(1, 3, "MCIM001A");
        sisbb.colar(12, 13, cpf);
        sisbb.teclarAguardarTroca("@E");
        sisbb.aguardarInd(5, 43, "Código");

        int dataInicio = Integer.parseInt(sisbb.copiar(8, 75, 4));
        dataInicio -= 5;

        sisbb.colar(8, 75, String.valueOf(dataInicio));
        sisbb.teclarAguardarTroca("@E");

        if (!sisbb.copiar(10, 42, 8).equals("Registro")) {

            sisbb.aguardarInd(1, 2, "INIM145D");

            int linhaSistema = 7;

            do {
                linhaSistema++;
                BloqueioCorrespondencia bc = new BloqueioCorrespondencia();

                if (linhaSistema == 20) {
                    sisbb.teclarAguardarTroca("@8");
                    linhaSistema = 8;

                    if (sisbb.copiar(23, 4, 5).equals("ltima")) {
                        break;

                    }

                }

                if (sisbb.copiar(linhaSistema, 12, 6).equals("VIP576") || sisbb.copiar(linhaSistema, 12, 6).equals("ICE404")) {

                    sisbb.colar(linhaSistema, 2, "X");
                    sisbb.teclarAguardarTroca("@E");
                    sisbb.aguardarInd(1, 2, "INIM145E");

                    bc.setDtaPostagem(sisbb.copiar(8, 4, 77).trim());
                    bc.setServicoECT(sisbb.copiar(11, 4, 77).trim());
                  
                    String parte1 = sisbb.copiar(18, 36, 44).trim();
                    String parte2 = sisbb.copiar(19, 4, 76).trim();
                    String parte3 = sisbb.copiar(20, 4, 76).trim();
                    
                    
                    if (!parte1.equals("")) {
                        bc.setEndereco(parte1 + ", " + parte2 + ", " + parte3);
                    }else{
                        bc.setEndereco("");                         
                        
                    }
                    listaCorrespondencia.add(bc);

                    sisbb.teclarAguardarTroca("@3");
                    sisbb.aguardarInd(1, 2, "INIM145D");

                }

            } while (!sisbb.copiar(linhaSistema, 4, 3).equals(""));

        }

        return listaCorrespondencia;
    }

    public List restricoesReplicadas(JanelaSisbb sisbb, String cpf, String dataRestFim, String dataRestInicio, boolean restTudo) throws RoboException, InterruptedException {

        List<RestricoesReplicadas> listaReplicadas = new ArrayList<>();

        sisbb.aguardarInd(1, 3, "ACPM140B");

        if (!sisbb.copiar(12, 39, 4).equals("Nada")) {

            sisbb.colar(21, 24, "03");
            sisbb.teclarAguardarTroca("@E");

            if (!sisbb.copiar(23, 4, 11).equals("etalhamento")) {

                sisbb.aguardarInd(1, 3, "ACPM14AB");

                int linha_rest = 10;

                do {

                    RestricoesReplicadas res = new RestricoesReplicadas();

                    if ((sisbb.copiar(linha_rest, 42, 5).contains("RELAT")) || sisbb.copiar(linha_rest, 42, 5).contains("ABSOL")) {

                        res.setTipo(sisbb.copiar(linha_rest, 17, 24).trim());
                        res.setDtaOcorrencia(sisbb.copiar(linha_rest, 48, 10).trim());
                        res.setDtaBaixa(sisbb.copiar(linha_rest, 71, 10));

                        listaReplicadas.add(res);

                    }

                    if (linha_rest == 19) {

                        if (sisbb.copiar(1, 3, 8).equals("ACPM14BB")) {
                            sisbb.teclarAguardarTroca("@3");
                            Thread.sleep(200);
                            sisbb.colar(21, 24, "  ");

                        }

                        Thread.sleep(200);
                        sisbb.teclarAguardarTroca("@8");
                        Thread.sleep(200);

                        if (sisbb.copiar(22, 3, 4).equals("Mais")) {
                            Thread.sleep(200);

                            sisbb.colar(22, 38, "s");
                            Thread.sleep(200);
                            sisbb.teclar("@E");
                            Thread.sleep(200);

                        }

                        linha_rest = 9;

                    }

                    linha_rest++;

                    if (sisbb.copiar(1, 3, 8).equals("ACPM14BB")) {
                        sisbb.teclarAguardarTroca("@3");
                        Thread.sleep(100);
                        sisbb.colar(21, 24, "  ");

                    }

                } while (!sisbb.copiar(linha_rest, 17, 5).equals("") & !sisbb.copiar(23, 3, 6).equals("Última"));

            }

        }

        String data_parte1 = dataRestInicio.substring(0, 2); //pega o mês
        String data_parte2 = dataRestInicio.substring(2, 6); //pega o ano
        String dataFim1 = dataRestFim.substring(0, 2); //pega o mês
        String dataFim2 = dataRestFim.substring(2, 6); //pega o ano

        int dataInicio_manipulada = Integer.parseInt(data_parte2 + data_parte1);
        int dataFim_manipulada = Integer.parseInt(dataFim2 + dataFim1);

        if (!restTudo) {

            List<RestricoesReplicadas> listRestricoesRep = new ArrayList<>();

            for (RestricoesReplicadas r : listaReplicadas) {

                String data_sisbb = r.getDtaOcorrencia().replace("/", "");
                String data_Sis_parte1 = data_sisbb.substring(2, 4); //pega o dia
                String data_Sis_parte2 = data_sisbb.substring(4, 8); //pega o ano
                int data_sisbb_manipulada = Integer.parseInt(data_Sis_parte2 + data_Sis_parte1);

                if (data_sisbb_manipulada <= dataFim_manipulada && data_sisbb_manipulada >= dataInicio_manipulada) {
                    listRestricoesRep.add(r);
                }
            }
            listaReplicadas = listRestricoesRep;
        }

        return listaReplicadas;

    }
    
     public List restricoesTerceiros(JanelaSisbb sisbb, String cpf, String dataRestFim, String dataRestInicio, boolean restTudo) throws RoboException, InterruptedException {

        List<RestricoesTerceiros> listRestricoesTerceiros = new ArrayList<>();
        int linhaTipoRestricao = 14;
        String opcao;

        while (!sisbb.copiar(1, 3, 8).equals("ACPM140B")) {
            sisbb.teclarAguardarTroca("@3");
        }

        do {

            if (restTudo) {

                if (!sisbb.copiar(linhaTipoRestricao, 39, 18).trim().equals("Nada consta")) {

                    opcao = sisbb.copiar(linhaTipoRestricao, 4, 1);
                    sisbb.colar(21, 24, opcao);
                    sisbb.teclarAguardarTroca("@E");

                    int linha_rest = 10;

                    do {

                        RestricoesTerceiros res = new RestricoesTerceiros();

                        if (sisbb.copiar(linha_rest, 42, 5).contains("RELAT") || sisbb.copiar(linha_rest, 42, 5).contains("ABSOL")) {

                            if (!sisbb.copiar(10, 6, 3).equals("CCF")) {
                                res.setTipo(sisbb.copiar(linha_rest, 17, 24).trim());

                            } else {
                                String tipo = sisbb.copiar(linha_rest, 6, 3);
                                String cheques = sisbb.copiar(linha_rest, 17, 24).trim();
                                String ccf = tipo.concat(" " + cheques);
                                res.setTipo(ccf);

                            }

                            res.setDtaRegistro(sisbb.copiar(linha_rest, 48, 10));
                            res.setDtaBaixa(sisbb.copiar(linha_rest, 71, 10));

                            listRestricoesTerceiros.add(res);

                        }

                        if (linha_rest == 19) {

                            Thread.sleep(100);
                            sisbb.teclarAguardarTroca("@8");
                            Thread.sleep(200);

                            if (sisbb.copiar(22, 3, 4).equals("Mais")) {
                                Thread.sleep(200);

                                sisbb.colar(22, 38, "s");
                                Thread.sleep(300);
                                sisbb.teclar("@E");
                                Thread.sleep(200);

                            }

                            linha_rest = 9;

                        }

                        linha_rest++;

                    } while (!sisbb.copiar(linha_rest, 17, 5).equals("") & !sisbb.copiar(23, 3, 6).equals("Última"));

                }

                if (!sisbb.copiar(1, 3, 8).equals("ACPM140B")) {
                    sisbb.teclarAguardarTroca("@3");

                }

                if (sisbb.copiar(linhaTipoRestricao + 1, 6, 20).trim().equals("BACEN")) {

                    linhaTipoRestricao += 2;

                } else {

                    linhaTipoRestricao++;
                }

            } else {

                if (!sisbb.copiar(linhaTipoRestricao, 39, 18).trim().equals("Nada consta")) {

                    opcao = sisbb.copiar(linhaTipoRestricao, 4, 1);
                    sisbb.colar(21, 24, opcao);
                    sisbb.teclarAguardarTroca("@E");

                    int linha_rest = 10;

                    int linhaData = 10;

                    String data_parte1 = dataRestInicio.substring(0, 2); //pega o mês
                    String data_parte2 = dataRestInicio.substring(2, 6); //pega o ano
                    String dataFim1 = dataRestFim.substring(0, 2); //pega o mês
                    String dataFim2 = dataRestFim.substring(2, 6); //pega o ano

                    int dataInicio_manipulada = Integer.parseInt(data_parte2 + data_parte1);
                    int dataFim_manipulada = Integer.parseInt(dataFim2 + dataFim1);

                    do {

                        String data_sisbb = sisbb.copiar(linhaData, 48, 10).replace("/", "");
                        String data_Sis_parte1 = data_sisbb.substring(2, 4); //pega o dia
                        String data_Sis_parte2 = data_sisbb.substring(4, 8); //pega o ano
                        int data_sisbb_manipulada = Integer.parseInt(data_Sis_parte2 + data_Sis_parte1);

                        if (data_sisbb_manipulada < dataInicio_manipulada) {
                            break;

                        }

                        RestricoesTerceiros res = new RestricoesTerceiros();

                        if ((data_sisbb_manipulada <= dataFim_manipulada && data_sisbb_manipulada >= dataInicio_manipulada) && (sisbb.copiar(linha_rest, 42, 5).contains("RELAT") || sisbb.copiar(linha_rest, 42, 5).contains("ABSOL"))) {

                            if (!sisbb.copiar(10, 6, 3).equals("CCF")) {
                                res.setTipo(sisbb.copiar(linha_rest, 17, 24).trim());

                            } else {
                                String tipo = sisbb.copiar(linha_rest, 6, 3);
                                String cheques = sisbb.copiar(linha_rest, 17, 24).trim();
                                String ccf = tipo.concat(" " + cheques);
                                res.setTipo(ccf);

                            }

                            res.setDtaRegistro(sisbb.copiar(linha_rest, 48, 10));
                            res.setDtaBaixa(sisbb.copiar(linha_rest, 71, 10));

                            listRestricoesTerceiros.add(res);

                        }

                        if (linha_rest == 19) {

                            Thread.sleep(100);
                            sisbb.teclarAguardarTroca("@8");
                            Thread.sleep(100);
                            linhaData = 9;

                            if (sisbb.copiar(22, 3, 4).equals("Mais")) {
                                Thread.sleep(200);

                                sisbb.colar(22, 38, "s");
                                Thread.sleep(300);
                                sisbb.teclar("@E");
                                Thread.sleep(200);

                            }

                            linha_rest = 9;

                        }

                        linha_rest++;
                        linhaData++;

                    } while (!sisbb.copiar(linha_rest, 17, 5).equals("") & !sisbb.copiar(23, 3, 6).equals("Última"));

                }

                if (!sisbb.copiar(1, 3, 8).equals("ACPM140B")) {
                    sisbb.teclarAguardarTroca("@3");

                }

              
                    linhaTipoRestricao++;
                

            }

        } while (!sisbb.copiar(linhaTipoRestricao, 6, 4).equals(""));
        
        
         sisbb.teclarAguardarTroca("@3");
         sisbb.aguardarInd(1, 3, "MCIM100F");
         sisbb.teclarAguardarTroca("@3");
         sisbb.aguardarInd(1, 3, "MCIM001A");
         sisbb.teclarAguardarTroca("@3");
         sisbb.aguardarInd(1, 3, "MCIM0000");
         sisbb.teclarAguardarTroca("@3");
         sisbb.aguardarInd(1, 3, "SBBP6130");
         sisbb.colar(21, 20, "02");
         sisbb.teclarAguardarTroca("@E");
         sisbb.colar(18, 18, "15");
         sisbb.colar(19, 18, cpf);
         sisbb.teclarAguardarTroca("@E");
         

 if (!sisbb.copiar(23, 7, 5).equals("foram")) {

               // sisbb.aguardarInd(1, 3, "ACPM14AB");

                int linha_rest = 10;

                do {

                    RestricoesTerceiros res = new RestricoesTerceiros();

                    if ((sisbb.copiar(linha_rest, 37, 4).contains("RELA")) || sisbb.copiar(linha_rest, 37, 4).contains("ABSO")) {

                        res.setTipo(sisbb.copiar(linha_rest, 17, 19).trim());
                        res.setDtaRegistro(sisbb.copiar(linha_rest, 42, 10).trim());
                        res.setDtaBaixa(sisbb.copiar(linha_rest, 62, 10));

                        listRestricoesTerceiros.add(res);

                    }

                    if (linha_rest == 19) {

                        if (sisbb.copiar(1, 3, 8).equals("ACPM14BB")) {
                            sisbb.teclarAguardarTroca("@3");
                            Thread.sleep(200);
                            sisbb.colar(21, 24, "  ");

                        }

                        Thread.sleep(200);
                        sisbb.teclarAguardarTroca("@8");
                        Thread.sleep(200);

                        if (sisbb.copiar(22, 3, 4).equals("Mais")) {
                            Thread.sleep(200);

                            sisbb.colar(22, 38, "s");
                            Thread.sleep(200);
                            sisbb.teclar("@E");
                            Thread.sleep(200);

                        }

                        linha_rest = 9;

                    }

                    linha_rest++;

                    if (sisbb.copiar(1, 3, 8).equals("ACPM14BB")) {
                        sisbb.teclarAguardarTroca("@3");
                        Thread.sleep(100);
                        sisbb.colar(21, 24, "  ");

                    }

                } while (!sisbb.copiar(linha_rest, 17, 5).equals("") & !sisbb.copiar(23, 3, 6).equals("Última"));

            }


        
        
        
        
        
        
        
        

        return listRestricoesTerceiros;

    }

}
