/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capturaSisbb;

import controller.JanelaSisbb;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.ParcelamentoFatura;
import model.DadosIniciais;

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

}
