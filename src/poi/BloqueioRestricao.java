/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.scene.control.Alert;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 *
 * @author f3295813
 */
public class BloqueioRestricao {

    public void wordBloqueioRestricao(String cpf, String npj, String autor) throws FileNotFoundException, IOException, InvalidFormatException {//método que cria o documento word para a súmula de Bloqueio, utiliza a biblioteca Apache POI

        try {
            String imagem = "\\\\10.105.87.250\\dados\\Usuários\\Flávia\\SubsidioProativo\\src\\views\\imagens\\imgSumula.png";
            FileOutputStream saida = null;
            //saida = new FileOutputStream(new File("C:\\Users\\" + chave + "\\Desktop\\ " + tipoSumula + " - " + pegaAutor + ".docx"));

            saida = new FileOutputStream(new File(" C:\\Users\\f3295813\\Desktop\\Teste.docx"));

            File file = new File(imagem);
            java.io.InputStream inputStream = new java.io.FileInputStream(file);
            XWPFDocument doc = new XWPFDocument();

            XWPFParagraph paragrafo_imagem = doc.createParagraph();

            XWPFRun img = paragrafo_imagem.createRun();
            img.addPicture(inputStream, doc.PICTURE_TYPE_PNG, imagem, Units.toEMU(435), Units.toEMU(17));

            XWPFParagraph interna = doc.createParagraph();
            XWPFRun run_interna = interna.createRun();
            run_interna.setText("#usointerno");
            run_interna.setBold(true);
            run_interna.addBreak();

            paragrafo_imagem.setSpacingAfter(2);

            XWPFParagraph tipo_sumula = doc.createParagraph();
            XWPFRun run_tipo = tipo_sumula.createRun();
            tipo_sumula.setAlignment(ParagraphAlignment.CENTER);
            run_tipo.setText("SÚMULA DE SUBSÍDIO PROATIVO");
            run_tipo.setBold(true);

            XWPFParagraph tipo_2 = doc.createParagraph();
            XWPFRun run_tipo2 = tipo_2.createRun();
            run_tipo2.setText("CARTÃO¹");
            run_tipo2.setBold(true);
            tipo_2.setAlignment(ParagraphAlignment.CENTER);
            run_tipo2.setFontSize(12);

            XWPFParagraph obs = doc.createParagraph();
            XWPFRun run_obs = obs.createRun();
            run_obs.setFontSize(8);
            run_obs.setText("¹Este modelo de súmula lista as informações mínimas que devem ser encaminhadas ao advogado para a defesa do Banco. Assim, a depender dos fatos alegados e dos pedidos requeridos pelo autor, outras informações e documentos deverão ser providenciados.");
            obs.setAlignment(ParagraphAlignment.BOTH);
            tipo_2.setSpacingAfter(6);

            run_tipo.addBreak();
            tipo_sumula.setSpacingAfter(0);

            XWPFParagraph tipo_sumula2 = doc.createParagraph();

            tipo_sumula.setAlignment(ParagraphAlignment.CENTER);
            run_tipo.setText("COMPRA/SAQUE NÃO AUTORIZADO, BLOQUEIO DA FUNÇÃO CRÉDITO, e/ou INCLUSÃO EM CADASTROS RESTRITIVOS");
            run_tipo.setBold(true);

            tipo_sumula2.setSpacingAfter(6);

            XWPFParagraph texto = doc.createParagraph();
            XWPFRun run_texto = texto.createRun();
            texto.setAlignment(ParagraphAlignment.BOTH);
            run_texto.setText("(Atenção: a presente súmula contém informações relacionadas ao processo judicial mencionado abaixo e serve, exclusivamente, de subsídio para a elaboração da defesa do Banco.  Desta feita, os dados aqui evidenciados, bem como o próprio documento, não devem ser usados ou disponibilizados para qualquer outra destinação).");
            run_texto.setFontSize(9);

            run_texto.addBreak();

            XWPFParagraph para_npj = doc.createParagraph();
            XWPFRun run_npj = para_npj.createRun();
            para_npj.setAlignment(ParagraphAlignment.LEFT);
            run_npj.setText("NPJ: " + npj);
            run_npj.setBold(true);
            para_npj.setSpacingAfter(0);
            para_npj.setSpacingBefore(8);

            XWPFParagraph para_autor = doc.createParagraph();
            XWPFRun run_autor = para_autor.createRun();
            para_autor.setAlignment(ParagraphAlignment.LEFT);
            run_autor.setText("NOME DO(A) AUTOR(A): " + autor);
            run_autor.setBold(true);
            para_autor.setSpacingAfter(0);

            XWPFParagraph para_cpf = doc.createParagraph();
            XWPFRun run_cpf = para_cpf.createRun();
            para_cpf.setAlignment(ParagraphAlignment.LEFT);
            run_cpf.setText("CPF: " + cpf);
            run_cpf.setBold(true);
            para_cpf.setSpacingAfter(8);

            //======================================================primeira questao==========================================================
            XWPFParagraph questao1 = doc.createParagraph();
            questao1.setAlignment(ParagraphAlignment.BOTH);

            XWPFRun runQ1 = questao1.createRun();

            runQ1.setBold(true);
            runQ1.addBreak();

            runQ1.setText("1 - Resumo das alegações do(a) autor(a).");

            //=====================================================segunda questão============================================================
            XWPFParagraph questao2 = doc.createParagraph();
            XWPFRun runQ2 = questao2.createRun();
            questao2.setAlignment(ParagraphAlignment.BOTH);
            runQ2.setBold(true);
            runQ2.addBreak();
            runQ2.setText("2 - Versão do Banco a respeito dos fatos alegados pelo(a) cliente no processo.");

            doc.write(saida);

            Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
            dialogoInfo.setTitle("Atenção");
            dialogoInfo.setHeaderText("Súmula gerada com sucesso");
            dialogoInfo.setContentText("Verifique a área de trabalho!");
            dialogoInfo.showAndWait();

        } catch (Exception e) {

            Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
            dialogoInfo.setTitle("Atenção ERRO");
            dialogoInfo.setHeaderText("Súmula não foi gerada");
            dialogoInfo.setContentText("Verifique com a área de suporte!");
            dialogoInfo.showAndWait();

        }

    }

}
