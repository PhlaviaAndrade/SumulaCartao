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
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 *
 * @author f3295813
 */
public class TransacoesNaoAutorizadas {

    public void wordTransacoesNaoAutorizadas(List<String> telaTransacoes, String autor, String matricula) throws FileNotFoundException, InvalidFormatException, IOException {

        try {
            String imagem = "\\\\10.105.87.250\\dados\\Usuários\\Flávia\\SubsidioProativo\\src\\views\\imagens\\imgSumula.png";
            FileOutputStream saida = null;
            //saida = new FileOutputStream(new File("C:\\Users\\" + chave + "\\Desktop\\ " + tipoSumula + " - " + pegaAutor + ".docx"));

            saida = new FileOutputStream(new File("C:\\Users\\" + matricula + "\\Desktop\\Transações não aprovadas - " + autor + ".docx"));

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
            
            XWPFParagraph tela = doc.createParagraph();
            XWPFRun runTela = tela.createRun();
            
            String teste[] = null;

            for (String t : telaTransacoes) {
                
                 teste = t.split("\n");
                     }
            
            for(String p : teste){
            
             runTela.setText(p + "\n");
             runTela.addBreak();
                
            }

            runTela.setFontFamily("Courier New");
            runTela.setFontSize(9);
            runTela.setBold(false);


            doc.write(saida);

            Platform.runLater(() -> {
                Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
                dialogoInfo.setTitle("Atenção");
                dialogoInfo.setHeaderText("Documento gerado com sucesso");
                dialogoInfo.setContentText("Verifique a área de trabalho!");

                dialogoInfo.showAndWait();
            });

        } catch (FileNotFoundException ex) {

            Platform.runLater(() -> {

                Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
                dialogoInfo.setTitle("Atenção ERRO");
                dialogoInfo.setHeaderText("Documento não foi gerado");
                dialogoInfo.setContentText("Gentileza tentar novamente, se o erro persistir entrar em contato com a área de Desenvolvimento");
                dialogoInfo.showAndWait();

            });

        }

    }
}
