/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.jfoenix.controls.JFXCheckBox;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 *
 * @author f3295813
 */
public class utils {
    
    public boolean validarEntradaDados(String cpf, String npj) {

        

            String errorMessage = "";

            if (cpf == null || cpf.length() == 0 || cpf.length() < 11 || npj == null || npj.length() == 0) {
                errorMessage += "Digite o NPJ e o CPF com valores númericos e quantidade suficiente de dígitos";

            }

            if (errorMessage.length() == 0) {
                return true;

            } else {
                //mostrando a mensagem de erro

                alertaValidacao(errorMessage);
            }
       
        
        

        return false;

    }

      public void alertaValidacao(String p) {

       Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro no preenchimento");
                alert.setHeaderText("NPJ ou CPF Inválido");
                alert.setContentText(p);
                alert.show();

    }
     
     
    public void alertaCheckBoxSemMarcacao() {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Atenção");
        alert.setHeaderText("Não foi selecionado nenhum cartão");
        alert.setContentText("Gentileza selecionar as informações para a captura");
        alert.show();

    }
     
     
   public boolean validarSumula(JFXCheckBox a, JFXCheckBox b, JFXCheckBox c, JFXCheckBox d) {

        String errorMessage = "";

        if (!a.isSelected() && !b.isSelected() && !c.isSelected() && !d.isSelected()) {

            errorMessage += "Selecione um tipo de súmula";
        }

        if (errorMessage.length() == 0) {
            return true;

        } else {
            //mostrando a mensagem de erro

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ");
            alert.setHeaderText("Tipo de súmula não foi selecionada");
            alert.setContentText(errorMessage);
            alert.show();

            return false;

        }

    }  
   public class CapturaExtratoContaCorrente {
    
    DateTimeFormatter fd = DateTimeFormatter.ofPattern("dd.MM.yyyy");        
    SimpleDateFormat fd2 = new SimpleDateFormat("yyyy-MM-dd");
    
   
    
  //  public void capturaExtratos(JFXTextField inputAgencia, JFXTextField inputConta, JFXTextField inputDataInicial, JFXTextField inputDataFinal) throws Throwable  {
        
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle("Erro");
//        if (!inputAgencia.getText().matches("[0-9]+") || inputAgencia.getText().length() > 4 || inputAgencia.getText().length() == 0) {
//            alert.setHeaderText("Problema no número da agência...");
//            alert.setContentText("Algum erro no número da agência, verifique!");
//            alert.showAndWait();
//        } else if (!inputConta.getText().matches("[0-9]+") || inputConta.getText().length() > 12 || inputConta.getText().length() == 0) {
//            alert.setHeaderText("Problema no número da conta...");
//            alert.setContentText("Algum erro no número da conta, verifique!");
//            alert.showAndWait();
//         } else {
//            try {
//                String retornoMsg = geraExtratos(inputAgencia, inputConta, inputDataInicial, inputDataFinal);
//                if(retornoMsg.equals("")) {
//                    alertSucesso();
//                }
//                else {
//                    alert.setHeaderText("Atenção!");
//                    alert.setContentText(retornoMsg);
//                    alert.showAndWait();
//                }
//               
//            } catch (Exception e) {
//                alert.setHeaderText("Problema de execução...");
//                alert.setContentText("Execução interrompida!");
//                alert.showAndWait();
//            }
//        }  
//    }
      private void alertSucesso() {
        Alert alertSucesso = new Alert(Alert.AlertType.INFORMATION);
        alertSucesso.setTitle("Sucesso");
        alertSucesso.setHeaderText("Captura efetuada com sucesso!");
        alertSucesso.setContentText("Extrato disponível para tratamento!");
        alertSucesso.showAndWait();
    }  
    
}
   
    public static void maxField(TextField textField, Integer length) {
        textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue == null || newValue.length() > length) {
                textField.setText(oldValue);
            }
        }
        );
    }
     private static void positionCaret(TextField textField) {
        Platform.runLater(() -> {
            if (textField.getText().length() != 0) {
                textField.positionCaret(textField.getText().length());
            }
        }
        );
    }

    public void mascaraCPF(TextField textField) {
        maxField(textField, 18);
        textField.lengthProperty().addListener((observableValue, number, number2) -> {
            String value = textField.getText();
            if (number2.intValue() <= 14) {
                value = value.replaceAll("[^0-9]", "");
                value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
                value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
                value = value.replaceFirst("(\\d{3})(\\d)", "$1-$2");
            } else {
                value = value.replaceAll("[^0-9]", "");
                value = value.replaceFirst("(\\d{2})(\\d)", "$1.$2");
                value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
                value = value.replaceFirst("(\\d{3})(\\d)", "$1/$2");
                value = value.replaceFirst("(\\d{4})(\\d)", "$1-$2");
            }
            textField.setText(value);
            positionCaret(textField);
        }
        );
        
    }
    
     public void alertaGeral(String a, String b, String c) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(a);
        alert.setHeaderText(b);
        alert.setContentText(c);
        alert.show();

    }
     
      public void alertaGeralInformacao(String a, String b, String c) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(a);
        alert.setHeaderText(b);
        alert.setContentText(c);
        alert.show();

    }

}