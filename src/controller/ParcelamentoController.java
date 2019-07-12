/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import br.com.bb.jibm3270.Janela3270;
import capturaSisbb.CapturaParcelamentoFatura;
import com.jfoenix.controls.JFXButton;
import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.ParcelamentoFatura;
import model.utils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import poi.PagamentoParcelado;
import controller.TelaPrincipalController;
import dao.ConsultaSQL;
import org.apache.xmlbeans.XmlException;

/**
 * FXML Controller class
 *
 * @author f3295813
 */
public class ParcelamentoController extends AbstractController implements Initializable {

    @FXML
    public AnchorPane AP_Parcelamento;
    @FXML
    public Text txtRendas;
    @FXML
    public Text txtRendas1;
    @FXML
    public TableColumn<ParcelamentoFatura, String> col_NumOperacao;
    @FXML
    public TableColumn<ParcelamentoFatura, String> col_TipoOperacao;
    @FXML
    public TableColumn<ParcelamentoFatura, String> col_DataContratacao;
    @FXML
    public TableColumn<ParcelamentoFatura, String> col_QuantidadeParcela;
    @FXML
    public TableColumn<ParcelamentoFatura, String> col_ValorParcela;
    @FXML
    public TableColumn<ParcelamentoFatura, String> col_ValorTotal;
    @FXML
    public TableColumn<ParcelamentoFatura, String> col_CanalContratacao;
    @FXML
    public Text txt_Doc21;

    public ObservableList<ParcelamentoFatura> observableListParcelamento;
    @FXML
    public TableView<ParcelamentoFatura> tableParcelamento;
    public TelaPrincipalController tp;
    ConsultaSQL consultaSQL = new ConsultaSQL();
    
    public CapturaParcelamentoFatura pc;
    @FXML
    private JFXButton btn_GerarWordParcelamento;
    @FXML
    private CheckBox cbDoc1;
    @FXML
    private CheckBox cbDoc2;
    @FXML
    private CheckBox cbDoc4;
    @FXML
    private CheckBox cbDoc3;
    @FXML
    private CheckBox cbDoc5;
    @FXML
    private CheckBox cbDoc7;
    @FXML
    private CheckBox cbDoc6;

    utils util = new utils();
    String cpf;
    String npj;
    String autor;
    String matricula;
    
     
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        col_NumOperacao.setCellValueFactory(new PropertyValueFactory<>("numOperacao"));
        col_TipoOperacao.setCellValueFactory(new PropertyValueFactory<>("tipoOperacao"));
        col_DataContratacao.setCellValueFactory(new PropertyValueFactory<>("dataContratacao"));
        col_QuantidadeParcela.setCellValueFactory(new PropertyValueFactory<>("qtdeParcela"));
        col_ValorParcela.setCellValueFactory(new PropertyValueFactory<>("valorParcela"));
        col_ValorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        col_CanalContratacao.setCellValueFactory(new PropertyValueFactory<>("canalContratacao"));
        pc = new CapturaParcelamentoFatura();
        
    }

    public void capturaParcelamento(String matricula, List listaOperacoesCaptura, String cpf, String npj, String autor, JanelaSisbb sisbb, TelaPrincipalController tp) throws PropertyVetoException, Throwable {

        this.cpf = cpf;
        this.npj = npj;
        this.autor = autor;
        this.tp = tp;
        this.matricula = matricula;
        
        if (sisbb == null) {

            sisbb = new JanelaSisbb();;
            sisbb.setTamanho(600, 500);
            sisbb.Aplicativo(matricula, "CARTAO", true);
            sisbb.aguardarInd(1, 2, "VP");
            sisbb.colar(21, 19, "11");
            sisbb.teclarAguardarTroca("@E");

        }

        try {

            observableListParcelamento = FXCollections.observableList(pc.capturaParcelamento(sisbb, matricula, listaOperacoesCaptura, cpf));

            if (!observableListParcelamento.isEmpty()) {
                tableParcelamento.setItems(observableListParcelamento);

                sisbb.rotinaEncerramento();
                TelaPrincipalController.sisbb = null;

                Platform.runLater(() -> {

                    util.alertaGeralInformacao("Atenção", "Captura finalizada com sucesso!", "Verifique os itens na tabela para gerar a súmula.");

                });

            } else {

                Platform.runLater(() -> {

                    util.alertaGeral("Atenção", "Cliente sem parcelamento nas operações selecionadas", "Gentileza verificar os dados!");

                });

            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @FXML
    private void geraSumulaParcelamento(ActionEvent event) throws FileNotFoundException, InvalidFormatException, IOException, PropertyVetoException, XmlException, Throwable {

        
        
        consultaSQL.inserir(npj, matricula, "Pagamento Parcelado");
        
        PagamentoParcelado wordParcelamento = new PagamentoParcelado();
        wordParcelamento.wordPagamentoParcelado(observableListParcelamento, cpf, npj, autor, cbDoc1, cbDoc2, cbDoc3, cbDoc4, cbDoc5, cbDoc6, cbDoc7);
        limparParcelamento();

    }
    
    private void limparParcelamento() throws PropertyVetoException {
       

        cbDoc1.setSelected(false);
        cbDoc2.setSelected(false);
        cbDoc4.setSelected(false);
        cbDoc3.setSelected(false);
        cbDoc5.setSelected(false);
        cbDoc7.setSelected(false);
        cbDoc6.setSelected(false);
        
        
        tableParcelamento.getItems().clear();
        tp.limparTelaPrincipalController();
     

    }



}
