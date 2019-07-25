/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capturaSisbb;

import br.com.bb.jibm3270.RoboException;
import controller.JanelaSisbb;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.ParcelamentoFatura;
import model.DadosIniciais;
import model.RestricoesBB;
import model.RestricoesTerceiros;

/**
 *
 * @author f3295813
 */
public class CapturaParcelamentoFatura {

    List<ParcelamentoFatura> listaParcelamento = new ArrayList<>();

    DadosIniciais di = new DadosIniciais();
    String contaObjeto;

    public List capturaParcelamento(JanelaSisbb sisbb, String matricula, List<DadosIniciais> listaOperacoesCaptura, String cpf) throws Throwable {
        Set<String> ListSet = new HashSet<>();
        int linhaConta;
        int linhaCartao = 9;

        while (!sisbb.copiar(1, 3, 8).equals("VIP10300")) {
            sisbb.teclarAguardarTroca("@3");
        }

        sisbb.aguardarInd(1, 3, "VIP10300");
        sisbb.colar(21, 20, "41");
        sisbb.teclarAguardarTroca("@E");
        sisbb.aguardarInd(1, 3, "VIP23961");

        for (DadosIniciais r : listaOperacoesCaptura) {
            String conta = r.getConta();
            ListSet.add(conta);

        }

        for (String contaObjeto : ListSet) {

            while (!sisbb.copiar(1, 3, 8).equals("VIP23961")) {
                sisbb.teclarAguardarTroca("@3");
            }

            sisbb.colar(17, 26, "01");
            sisbb.colar(20, 26, cpf);
            sisbb.teclarAguardarTroca("@E");

            if (sisbb.copiar(1, 3, 8).equals("VIP10080")) {

                sisbb.aguardarInd(1, 3, "VIP10080");

                linhaConta = 12;
                linhaCartao = 9;

                while (!sisbb.copiar(linhaConta, 8, 8).trim().equals(contaObjeto)) {
                    linhaConta++;
                    if (linhaConta == 22) {
                        sisbb.teclarAguardarTroca("@8");
                        linhaConta = 12;

                    }
                }

                if (contaObjeto.equals(sisbb.copiar(linhaConta, 8, 8).trim())) {

                    sisbb.colar(linhaConta, 4, "X");
                    sisbb.teclarAguardarTroca("@E");

                }

            }
            sisbb.aguardarInd(1, 3, "VIP65291");

            sisbb.colar(21, 10, "29");
            sisbb.teclarAguardarTroca("@E");
            sisbb.aguardarInd(1, 3, "VIP");

            //for(int i = 11; i == 13; i++){
            sisbb.colar(12, 17, "X");
            sisbb.teclarAguardarTroca("@E");

            if (!sisbb.copiar(9, 7, 3).equals("")) {

                do {
                    ParcelamentoFatura pf = new ParcelamentoFatura();
                    sisbb.colar(linhaCartao, 4, "X");
                    sisbb.teclarAguardarTroca("@E");
                    sisbb.aguardarInd(1, 3, "VIP33161");

                    double valorParc = Double.parseDouble(sisbb.copiar(7, 21, 14).replaceAll("\\.", "").replaceAll(",", ".").trim());
                    double valorIofAdici = Double.parseDouble(sisbb.copiar(13, 21, 14).replaceAll("\\.", "").replaceAll(",", ".").trim());
                    double valorIofDiario = Double.parseDouble(sisbb.copiar(14, 21, 14).replaceAll("\\.", "").replaceAll(",", ".").trim());
                    double totalDouble = valorParc + valorIofAdici + valorIofDiario;
                    BigDecimal vlTotal = new BigDecimal(totalDouble);

                    BigDecimal vlTotalConv = vlTotal.setScale(2, RoundingMode.HALF_EVEN);
                    String total = String.valueOf(vlTotalConv);

                    pf.setValorTotal(total);
                    pf.setValorParcela(sisbb.copiar(9, 22, 13).trim());
                    pf.setDataContratacao(sisbb.copiar(7, 68, 10).trim());
                    pf.setQtdeParcela(sisbb.copiar(8, 72, 6).trim());
                    pf.setNumOperacao(sisbb.copiar(11, 68, 12).trim());
                    pf.setCanalContratacao(sisbb.copiar(11, 21, 20).trim());
                    pf.setTipoOperacao("Parcelamento");

                    sisbb.teclarAguardarTroca("@3");
                    sisbb.aguardarInd(1, 3, "VIP");
                    sisbb.colar(linhaCartao, 4, " ");

                    listaParcelamento.add(pf);
                    linhaCartao++;

                } while (!sisbb.copiar(linhaCartao, 7, 3).equals(""));

            }

            // }
        }

//        sisbb.rotinaEncerramento();
//        TelaPrincipalController.sisbb = null;
        return listaParcelamento;

    }

    public List restricoes(JanelaSisbb sisbb, String cpf, List<DadosIniciais> listaOperacoesCaptura) throws RoboException, InterruptedException {

        List<RestricoesBB> listRestricoes = new ArrayList<>();

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
        sisbb.colar(21, 24, "02");
        sisbb.teclarAguardarTroca("@E");

        if (!sisbb.copiar(23, 4, 11).equals("etalhamento")) {

            sisbb.aguardarInd(1, 3, "ACPM14AB");

            if (!sisbb.copiar(11, 39, 4).equals("Nada")) {

                String num_operacao = "";
                String concat = "";

                for (DadosIniciais op : listaOperacoesCaptura) {

                    num_operacao = op.getConta();
                    concat += num_operacao + ", ";
                }
                int linha_rest = 10;

                do {

                    RestricoesBB res = new RestricoesBB();

                    if ((sisbb.copiar(linha_rest, 17, 24).contains("SERASA") || sisbb.copiar(linha_rest, 17, 24).contains("SCPC")) && ((sisbb.copiar(linha_rest, 42, 5).contains("RELAT")) || sisbb.copiar(linha_rest, 42, 5).contains("ABSOL"))) {

                        String opcao = sisbb.copiar(linha_rest, 3, 2).trim();
                        sisbb.colar(21, 24, opcao);

                        sisbb.teclar("@E");
                        sisbb.aguardarInd(1, 3, "ACPM14BB");

                        String sisbbConta = sisbb.copiar(12, 58, 14).trim();

                        if (concat.contains(sisbbConta)) {
                            for (DadosIniciais t : listaOperacoesCaptura) {
                                String conta = t.getConta();

                                if (conta.equals(sisbbConta)) {
                                    String modalidade = t.getTipoPlastico();
                                    res.setModalidade(modalidade);
                                }

                            }
                            res.setValor(sisbb.copiar(9, 68, 13).trim());
                            res.setDtaRegistro(sisbb.copiar(19, 61, 10));

                            sisbb.teclarAguardarTroca("@3");
                            Thread.sleep(100);
                            sisbb.aguardarInd(1, 3, "ACPM14AB");
                            res.setDtaBaixa(sisbb.copiar(linha_rest, 71, 10));
                            res.setTipo(sisbb.copiar(linha_rest, 17, 24).trim());

                            sisbb.colar(21, 24, "  ");

                            listRestricoes.add(res);

                        }

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
                        Thread.sleep(00);
                        sisbb.colar(21, 24, "  ");

                    }

                } while (!sisbb.copiar(linha_rest, 17, 5).equals("") & !sisbb.copiar(23, 3, 6).equals("Última"));

            }

        }

        return listRestricoes;

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

                if (sisbb.copiar(linhaTipoRestricao + 1, 6, 20).trim().equals("BACEN")) {

                    linhaTipoRestricao += 2;

                } else {

                    linhaTipoRestricao++;
                }

            }

        } while (!sisbb.copiar(linhaTipoRestricao, 6, 4).equals(""));

        return listRestricoesTerceiros;

    }
}
