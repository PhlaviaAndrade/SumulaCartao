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
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import model.ParcelamentoFatura;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;

/**
 *
 * @author f3295813
 */
public class PagamentoParcelado {
    
    public void wordPagamentoParcelado(ObservableList<ParcelamentoFatura> observableListParcelamento, String cpf, String npj, String autor, CheckBox cbDoc1, CheckBox cbDoc2, CheckBox cbDoc3, CheckBox cbDoc4, CheckBox cbDoc5, CheckBox cbDoc6, CheckBox cbDoc7) throws FileNotFoundException, InvalidFormatException, IOException, XmlException {
        
        try {

            //String imagem = "/views/imagens/imgSumula.png";
            String imagem = "\\\\10.105.87.250\\dados\\Usuários\\Flávia\\SubsidioProativo\\src\\views\\imagens\\imgSumula.png";

            //String imagem = "C:\\Users\\f3295813\\Desktop\\imgSumula.png";
            //String imagem = "C:\\Users\\Flávia\\Desktop\\imgSumula.png";
            FileOutputStream saida = null;
            File file = new File(imagem);
            
            java.io.InputStream inputStream = new java.io.FileInputStream(file);
            
            XWPFDocument doc = new XWPFDocument();
            
            try {

//                XWPFStyles styles = doc.createStyles();
//
//                CTFonts fonts = CTFonts.Factory.newInstance();
//                fonts.setHAnsi("Arial");
//                styles.setDefaultFonts(fonts);

                String chave = System.getProperty("user.name");

                //saida = new FileOutputStream(new File("G:\\Publica\\SUBSIDIO PROATIVO CDC\\ " + tipoSumula + " - " + pegaAutor + ".docx"));
                saida = new FileOutputStream(new File("C:\\Users\\" + chave + "\\Desktop\\Pagamento Parcelado - " + autor + ".docx"));
                // saida = new FileOutputStream(new File("C:\\Users\\Flávia\\Desktop\\" + tipoSumula + " - " + pegaAutor + ".docx"));

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
                tipo_sumula.setSpacingAfter(0);
                XWPFRun run_tipo1 = tipo_sumula.createRun();
                tipo_sumula.setAlignment(ParagraphAlignment.CENTER);
                run_tipo1.setText("SÚMULA DE SUBSÍDIO PROATIVO");
                run_tipo1.setBold(true);
                run_tipo1.setFontSize(12);
                
                XWPFParagraph tipo_sumula2 = doc.createParagraph();
                XWPFRun run_tipo2 = tipo_sumula2.createRun();
                run_tipo2.setText("Cartão de Crédito¹");
                tipo_sumula2.setAlignment(ParagraphAlignment.CENTER);
                run_tipo2.setFontSize(12);
                                
                XWPFParagraph obs = doc.createParagraph();
                XWPFRun run_obs = obs.createRun();
                run_obs.setFontSize(8);
                run_obs.setText("¹ Este modelo de súmula lista as informações mínimas que devem ser encaminhadas ao advogado para a defesa do Banco. Assim, a depender dos fatos alegados e dos pedidos requeridos pelo autor, outras informações e documentos deverão ser providenciados.");
                obs.setAlignment(ParagraphAlignment.BOTH);
                tipo_sumula2.setSpacingAfter(6);
                
                  XWPFParagraph pagamento = doc.createParagraph();
                XWPFRun run_pagamento = pagamento.createRun();
                run_pagamento.setText("PAGAMENTO PARCELADO DA FATURA DO CARTÃO");
                pagamento.setAlignment(ParagraphAlignment.CENTER);
                run_pagamento.setBold(true);
                run_pagamento.setFontSize(12);
                
                
                
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
                
                XWPFParagraph questao3 = doc.createParagraph();
                questao3.setAlignment(ParagraphAlignment.BOTH);
                XWPFRun runQ3 = questao3.createRun();
                runQ3.setBold(true);
                runQ3.addBreak();
                runQ3.setText("3 - Informações do(s) contrato(s) de empréstimo objeto da ação (ATENÇÃO: especificar todos os empréstimos questionados na ação)");
                
                XWPFTable tabelaQ3 = doc.createTable();
                
                XWPFTableRow linha0 = tabelaQ3.getRow(0);
                
                XWPFTableCell celu1 = linha0.getCell(0);
                XWPFTableCell celu2 = linha0.createCell();
                XWPFTableCell celu3 = linha0.createCell();
                XWPFTableCell celu4 = linha0.createCell();
                XWPFTableCell celu5 = linha0.createCell();
                XWPFTableCell celu6 = linha0.createCell();
                XWPFTableCell celu7 = linha0.createCell();
                
                XWPFParagraph para1 = celu1.addParagraph();
                XWPFParagraph para2 = celu2.addParagraph();
                XWPFParagraph para3 = celu3.addParagraph();
                XWPFParagraph para4 = celu4.addParagraph();
                XWPFParagraph para5 = celu5.addParagraph();
                XWPFParagraph para6 = celu6.addParagraph();
                XWPFParagraph para7 = celu7.addParagraph();
                
                XWPFRun run10 = para1.createRun();
                XWPFRun run21 = para2.createRun();
                XWPFRun run31 = para3.createRun();
                XWPFRun run41 = para4.createRun();
                XWPFRun run51 = para5.createRun();
                XWPFRun run61 = para6.createRun();
                XWPFRun run71 = para7.createRun();
                
                run10.setText("Número da Operação");
                run10.setBold(true);
                para1.setAlignment(ParagraphAlignment.CENTER);
                run10.setFontSize(10);
                
                run21.setText("Tipo da Operação");
                run21.setBold(true);
                para2.setAlignment(ParagraphAlignment.CENTER);
                run21.setFontSize(10);
                
                run31.setText("Data da Contratação");
                run31.setBold(true);
                para3.setAlignment(ParagraphAlignment.CENTER);
                run31.setFontSize(10);
                
                run41.setText("Quantidade de parcelas");
                run41.setBold(true);
                para4.setAlignment(ParagraphAlignment.CENTER);
                run41.setFontSize(10);
                
                run51.setText("Valor parcela");
                run51.setBold(true);
                para5.setAlignment(ParagraphAlignment.CENTER);
                run51.setFontSize(10);
                
                run61.setText("Valor total");
                run61.setBold(true);
                para6.setAlignment(ParagraphAlignment.CENTER);
                run61.setFontSize(10);
                
                run71.setText("Forma de contratação");
                run71.setBold(true);
                para7.setAlignment(ParagraphAlignment.CENTER);
                run71.setFontSize(10);
                
                tabelaQ3.removeRow(1);
                
                List<ParcelamentoFatura> list_pagamentoParcelado;
                
                if (!observableListParcelamento.isEmpty()) {
                    
                    list_pagamentoParcelado = observableListParcelamento;
                    
                    for (ParcelamentoFatura pf : list_pagamentoParcelado) {
                        
                        String numOperacao = pf.getNumOperacao();
                        String tipoOperacao = pf.getTipoOperacao();
                        String dataContratacao = pf.getDataContratacao();
                        String qtdParcela = pf.getQtdeParcela();
                        String valorParcela = pf.getValorParcela();
                        String valorTotal = pf.getValorTotal();
                        String canalContratacao = pf.getCanalContratacao();
                        
                        XWPFTableRow linha1 = tabelaQ3.createRow();
                        
                        XWPFTableCell cel1 = linha1.getCell(0);
                        XWPFTableCell cel2 = linha1.getCell(1);
                        XWPFTableCell cel3 = linha1.getCell(2);
                        XWPFTableCell cel4 = linha1.getCell(3);
                        XWPFTableCell cel5 = linha1.getCell(4);
                        XWPFTableCell cel6 = linha1.getCell(5);
                        XWPFTableCell cel7 = linha1.getCell(6);
                        
                        XWPFParagraph parag1 = cel1.addParagraph();
                        XWPFParagraph parag2 = cel2.addParagraph();
                        XWPFParagraph parag3 = cel3.addParagraph();
                        XWPFParagraph parag4 = cel4.addParagraph();
                        XWPFParagraph parag5 = cel5.addParagraph();
                        XWPFParagraph parag6 = cel6.addParagraph();
                        XWPFParagraph parag7 = cel7.addParagraph();
                        
                        XWPFRun r1 = parag1.createRun();
                        XWPFRun r2 = parag2.createRun();
                        XWPFRun r3 = parag3.createRun();
                        XWPFRun r4 = parag4.createRun();
                        XWPFRun r5 = parag5.createRun();
                        XWPFRun r6 = parag6.createRun();
                        XWPFRun r7 = parag7.createRun();
                        
                        r1.setText(numOperacao);
                        r1.setBold(false);
                        parag1.setAlignment(ParagraphAlignment.CENTER);
                        r1.setFontSize(10);
                        
                        r2.setText(tipoOperacao);
                        r2.setBold(false);
                        parag2.setAlignment(ParagraphAlignment.CENTER);
                        r2.setFontSize(10);
                        
                        r3.setText(dataContratacao);
                        r3.setBold(false);
                        parag3.setAlignment(ParagraphAlignment.CENTER);
                        r3.setFontSize(10);
                        
                        r4.setText(qtdParcela);
                        r4.setBold(false);
                        parag4.setAlignment(ParagraphAlignment.CENTER);
                        r4.setFontSize(10);
                        
                        r5.setText(valorParcela);
                        r5.setBold(false);
                        parag5.setAlignment(ParagraphAlignment.CENTER);
                        r5.setFontSize(10);
                        
                        r6.setText(valorTotal);
                        r6.setBold(false);
                        parag6.setAlignment(ParagraphAlignment.CENTER);
                        r6.setFontSize(10);
                        
                        r7.setText(canalContratacao);
                        r7.setBold(false);
                        parag7.setAlignment(ParagraphAlignment.CENTER);
                        r7.setFontSize(10);
                        para7.setSpacingAfterLines(6);
                        
                    }
                } else {
                    XWPFParagraph resposta3 = doc.createParagraph();
                    XWPFRun run3 = resposta3.createRun();
                    run3.setText("Sem lançamentos");
                }
                
            } catch (Exception e) {
                System.out.println(e);
                
            }
//========================================================================QUESTAO 4========================================================

            XWPFParagraph questao4 = doc.createParagraph();
            questao4.setSpacingBeforeLines(200);
            questao4.setAlignment(ParagraphAlignment.BOTH);
            XWPFRun runQ4 = questao4.createRun();
            
            runQ4.setBold(true);
            
            runQ4.setText("4 – O cliente procurou a dependência para cancelar o parcelamento ou formalizou a ocorrência junto aos canais de atendimento do Banco?");

//=====================================================================QUESTÃO 5=====================================================
            XWPFParagraph questao5 = doc.createParagraph();
            questao5.setAlignment(ParagraphAlignment.BOTH);
            XWPFRun runQ5 = questao5.createRun();
            
            runQ5.setBold(true);
            questao5.setSpacingBefore(2);
            
            runQ5.setText("5 – Outras informações relevantes para a defesa do Banco:");

//=====================================================================QUESTÃO 6=====================================================
            XWPFParagraph questao6 = doc.createParagraph();
            
            questao6.setAlignment(ParagraphAlignment.BOTH);
            XWPFParagraph resposta6 = doc.createParagraph();
            
            XWPFRun runQ6 = questao6.createRun();
            XWPFRun runR6 = resposta6.createRun();
            
            runQ6.setBold(true);
            questao6.setSpacingBefore(2);
            
            runQ6.setText("6 - Documentos essenciais para a defesa do Banco, conforme o caso (ATENÇÃO: o rol abaixo listado não é taxativo, a depender dos fatos alegados pelo autor, outros documentos deverão ser apresentados):");
            
            String doc1 = "";
            XWPFParagraph doc1P = doc.createParagraph();
            XWPFRun runDoc1 = doc1P.createRun();
            
            if (cbDoc1.isSelected()) {
                
                runDoc1.setBold(true);
                
                doc1 = cbDoc1.getText() + " (APRESENTADO)";
                
            } else {
                
                doc1 = cbDoc1.getText() + " (AUSENTE)";
                
            }
            
            runDoc1.setText(doc1.toUpperCase());
            
            String doc2 = "";
            XWPFParagraph doc2P = doc.createParagraph();
            XWPFRun runDoc2 = doc2P.createRun();
            
            if (cbDoc2.isSelected()) {
                
                runDoc2.setBold(true);
                
                doc2 = cbDoc2.getText() + " (APRESENTADO)";
                
            } else {
                
                doc2 = cbDoc2.getText() + " (AUSENTE)";
                
            }
            
            runDoc2.setText(doc2.toUpperCase());
            
            String doc3 = "";
            XWPFParagraph doc3P = doc.createParagraph();
            XWPFRun runDoc3 = doc3P.createRun();
            
            if (cbDoc3.isSelected()) {
                
                runDoc3.setBold(true);
                
                doc3 = cbDoc3.getText() + " (APRESENTADO)";
                
            } else {
                
                doc3 = cbDoc3.getText() + " (AUSENTE)";
                
            }
            
            runDoc3.setText(doc3.toUpperCase());
            
            String doc4 = "";
            XWPFParagraph doc4P = doc.createParagraph();
            XWPFRun runDoc4 = doc4P.createRun();
            
            if (cbDoc4.isSelected()) {
                
                runDoc4.setBold(true);
                
                doc4 = cbDoc4.getText() + " (APRESENTADO)";
                
            } else {
                
                doc4 = cbDoc4.getText() + " (AUSENTE)";
                
            }
            
            runDoc4.setText(doc4.toUpperCase());
            
            String doc5 = "";
            XWPFParagraph doc5P = doc.createParagraph();
            XWPFRun runDoc5 = doc5P.createRun();
            
            if (cbDoc5.isSelected()) {                
                runDoc5.setBold(true);                
                doc5 = cbDoc5.getText() + " (APRESENTADO)";                
            } else {                
                doc5 = cbDoc5.getText() + " (AUSENTE)";                
            }
            runDoc5.setText(doc5.toUpperCase());            
            String doc6 = "";
            XWPFParagraph doc6P = doc.createParagraph();
            XWPFRun runDoc6 = doc6P.createRun();
            
            if (cbDoc6.isSelected()) {                
                runDoc6.setBold(true);                
                doc6 = cbDoc6.getText() + " (APRESENTADO)";                
            } else {                
                doc6 = cbDoc6.getText() + " (AUSENTE)";                
            }
            runDoc6.setText(doc6.toUpperCase());
            
            String doc7 = "";
            XWPFParagraph doc7P = doc.createParagraph();
            XWPFRun runDoc7 = doc7P.createRun();
            
            if (cbDoc7.isSelected()) {                
                runDoc7.setBold(true);                
                doc7 = cbDoc7.getText() + " (APRESENTADO)";                
            } else {                
                doc7 = cbDoc7.getText() + " (AUSENTE)";                
            }
            runDoc7.setText(doc7.toUpperCase());            
            
            XWPFParagraph nota = doc.createParagraph();
            
            nota.setAlignment(ParagraphAlignment.BOTH);
            
            XWPFRun runNota = nota.createRun();
            runNota.setColor("ff0000");
            runNota.setItalic(true);
            runNota.setFontSize(11);
            runNota.setText("Sr(a). Advogado(a), avaliar a necessidade de requerer que o processo tramite em segredo de justiça, considerando o tipo de documento a ser juntado, em atenção ao dever de sigilo.");
            
            runNota.setBold(true);
            
           

//=============================================== marca d'agua =============================================================            
            XWPFParagraph paragraph = doc.createParagraph();
            XWPFRun run = paragraph.createRun();
            XWPFHeaderFooterPolicy headerFooterPolicy = doc.getHeaderFooterPolicy();
            if (headerFooterPolicy == null) {
                headerFooterPolicy = doc.createHeaderFooterPolicy();
            }

            // create default Watermark - fill color black and not rotated
            headerFooterPolicy.createWatermark("Uso Interno");

            // get the default header
            // Note: createWatermark also sets FIRST and EVEN headers 
            // but this code does not updating those other headers
            XWPFHeader header = headerFooterPolicy.getHeader(XWPFHeaderFooterPolicy.DEFAULT);
            XWPFHeader footer = headerFooterPolicy.getHeader(XWPFHeaderFooterPolicy.FIRST);
            paragraph = header.getParagraphArray(0);

            // get com.microsoft.schemas.vml.CTShape where fill color and rotation is set
            org.apache.xmlbeans.XmlObject[] xmlobjects = paragraph.getCTP().getRArray(0).getPictArray(0).selectChildren(
                    new javax.xml.namespace.QName("urn:schemas-microsoft-com:vml", "shape"));
            
            if (xmlobjects.length > 0) {
                com.microsoft.schemas.vml.CTShape ctshape = (com.microsoft.schemas.vml.CTShape) xmlobjects[0];
                // set fill color
                ctshape.setFillcolor("#bfbdbd");

                // set rotation
                ctshape.setStyle(ctshape.getStyle() + ";rotation:315");
                //System.out.println(ctshape);
            }
            
            
            
            
            setFooter(doc, "CENOP SERVIÇOS CURITIBA - PR                                                                            Equipes Subsídios Proativos                                                                       Fones: (41) 3259-0215 / (41) 3259-0216");
            
            
            
            doc.write(saida);
            
            Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
            dialogoInfo.setTitle("Atenção");
            dialogoInfo.setHeaderText("Súmula gerada com sucesso");
            dialogoInfo.setContentText("Verifique a área de trabalho!");
            
            dialogoInfo.showAndWait();
            
        } catch (FileNotFoundException ex) {
            
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Atenção ERRO");
            dialogoInfo.setHeaderText("Súmula não foi gerada");
            dialogoInfo.setContentText("Verifique com a área de suporte!");
            dialogoInfo.showAndWait();
            
        }
    }
    
    
     public static void setFooter(XWPFDocument document, String footerText) throws IOException, XmlException {
        CTP ctpFooter = CTP.Factory.newInstance();
    ctpFooter.addNewR().addNewT();

    XWPFParagraph footerParagraph = new XWPFParagraph(ctpFooter, document);
    
   // XWPFRun footerRun = createFormattedRun(footerParagraph);
   XWPFRun footerRun = footerParagraph.createRun();
    footerRun.setFontSize(6);
    footerParagraph.setBorderTop(Borders.BIRDS);
    footerRun.setText(footerText);
    XWPFParagraph[] parsFooter = new XWPFParagraph[1];
    parsFooter[0] = footerParagraph;

    CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
    XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr);
    policy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, parsFooter);

    

    }
}
