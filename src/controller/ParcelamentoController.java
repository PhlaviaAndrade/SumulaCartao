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
import java.util.ArrayList;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableListBase;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.CheckBoxTableCell;
import model.DadosIniciais;
import model.RestricoesBB;
import model.RestricoesTerceiros;
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
    @FXML
    public TableView<ParcelamentoFatura> tableParcelamento;
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
     @FXML
    private CheckBox cbDoc8;
    @FXML
    private CheckBox cbDoc9;
    @FXML
    private CheckBox cbDoc10;    
    @FXML
    private Text txt_Doc211;
    @FXML
    private TableView<RestricoesBB> tableRestricoes;
    @FXML
    private TableColumn<RestricoesBB, String> restCol_Tipo;
    @FXML
    private TableColumn<RestricoesBB, String> restCol_Valor;
    @FXML
    private TableColumn<RestricoesBB, String> restCol_Data;
    @FXML
    private TableColumn<RestricoesBB, String> restCol_Modalidade;
    @FXML
    private TableColumn<RestricoesBB, String> restCol_Baixa;
    @FXML
    private Text txt_Doc2111;
    @FXML
    private TableView<RestricoesTerceiros> table_RestricoesTerceiros;
    @FXML
    private TableColumn<RestricoesTerceiros, Boolean> colTerceiros_selecao;
    @FXML
    private TableColumn<RestricoesTerceiros, String> colTerceiros_Tipo;
    @FXML
    private TableColumn<RestricoesTerceiros, String> colTerceiros_DtaRegistro;
    @FXML
    private TableColumn<RestricoesTerceiros, String> colTerceiros_DtaBaixa;

    utils util = new utils();
    String cpf;
    String npj;
    String autor;
    String matricula;
    String dataRestFim;
    String dataRestInicio;
    boolean restTudo;
    
    Thread t1;
    Thread t2;

    public TelaPrincipalController tp;
    ConsultaSQL consultaSQL = new ConsultaSQL();
    CapturaParcelamentoFatura pc = new CapturaParcelamentoFatura();
   ObservableList<ParcelamentoFatura> observableListParcelamento = FXCollections.observableArrayList();
   ObservableList<RestricoesBB> observableListRestricao = FXCollections.observableArrayList();
   ObservableList<RestricoesTerceiros> observableListTerceiros = FXCollections.observableArrayList();
   

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

        restCol_Tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        restCol_Valor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        restCol_Data.setCellValueFactory(new PropertyValueFactory<>("dtaRegistro"));
        restCol_Modalidade.setCellValueFactory(new PropertyValueFactory<>("modalidade"));
        restCol_Baixa.setCellValueFactory(new PropertyValueFactory<>("dtaBaixa"));

    }

    public void capturaParcelamento(String matricula, List listaOperacoesCaptura, String cpf, String npj, String autor, JanelaSisbb sisbb, TelaPrincipalController tp, String dataRestFim, String dataRestInicio, boolean restTudo) throws PropertyVetoException, Throwable {

        this.cpf = cpf;
        this.npj = npj;
        this.autor = autor;
        this.tp = tp;
        this.matricula = matricula;
        this.dataRestFim = dataRestFim;
        this.dataRestInicio = dataRestInicio;
        this.restTudo = restTudo;

        if (sisbb == null) {

            sisbb = new JanelaSisbb();;
            sisbb.setTamanho(600, 500);
            sisbb.Aplicativo(matricula, "CARTAO", true);
            sisbb.aguardarInd(1, 2, "VP");
            sisbb.colar(21, 19, "11");
            sisbb.teclarAguardarTroca("@E");

        }

        try {

            if (!observableListParcelamento.isEmpty()) {
                observableListParcelamento.clear();
            }

            observableListParcelamento = FXCollections.observableList(pc.capturaParcelamento(sisbb, matricula, listaOperacoesCaptura, cpf));

            observableListRestricao = FXCollections.observableList(pc.restricoes(sisbb, cpf, listaOperacoesCaptura));

            if (!observableListRestricao.isEmpty()) {
                tableRestricoes.setItems(observableListRestricao);

            }

            observableListTerceiros = FXCollections.observableList(pc.restricoesTerceiros(sisbb, cpf, dataRestFim, dataRestInicio, restTudo));

            if (!observableListTerceiros.isEmpty()) {

                colTerceiros_selecao.setCellValueFactory(new PropertyValueFactory<>("selected"));
                colTerceiros_Tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
                colTerceiros_DtaRegistro.setCellValueFactory(new PropertyValueFactory<>("dtaRegistro"));
                colTerceiros_DtaBaixa.setCellValueFactory(new PropertyValueFactory<>("dtaBaixa"));
                colTerceiros_selecao.setCellFactory(CheckBoxTableCell.forTableColumn(colTerceiros_selecao));

                colTerceiros_selecao.setCellValueFactory((CellDataFeatures<RestricoesTerceiros, Boolean> param) -> {
                    RestricoesTerceiros dados = param.getValue();
                    SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(dados.isSelected());
                    booleanProp.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                        dados.setSelected(newValue);
                    });
                    return booleanProp;
                });
                colTerceiros_selecao.setCellFactory((TableColumn<RestricoesTerceiros, Boolean> p) -> {
                    CheckBoxTableCell<RestricoesTerceiros, Boolean> cell = new CheckBoxTableCell<>();
                    return cell;
                });

                table_RestricoesTerceiros.setItems(observableListTerceiros);

            }

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
                
                sisbb.rotinaEncerramento();
                sisbb = null;
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @FXML
    private void geraSumulaParcelamento(ActionEvent event) throws FileNotFoundException, InvalidFormatException, IOException, PropertyVetoException, XmlException, Throwable {

         t1 = new Thread(() -> {
             
             try {
                 
                 List<RestricoesTerceiros> data = new ArrayList<>();
                 List<RestricoesTerceiros> listaRestricoesTerceiros = new ArrayList<>();
                 
                 if (!data.isEmpty()) {
                     data.clear();
                 }
                 
                 data = table_RestricoesTerceiros.getItems();
                 
                 for (RestricoesTerceiros t : data) {
                     if (t.isSelected()) {
                         listaRestricoesTerceiros.add(t);
                     }
                 }
                 
                 try {
                     consultaSQL.inserir(npj, matricula, "Pagamento Parcelado");
                 } catch (Throwable ex) {
                     Logger.getLogger(ParcelamentoController.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 
                 PagamentoParcelado wordParcelamento = new PagamentoParcelado();
                 try {
                     
                     wordParcelamento.wordPagamentoParcelado(listaRestricoesTerceiros, observableListRestricao, observableListParcelamento, cpf, npj, autor, cbDoc1, cbDoc2, cbDoc3, cbDoc4, cbDoc5, cbDoc6, cbDoc7, cbDoc8, cbDoc9, cbDoc10);
                     
                     
                 } catch (InvalidFormatException ex) {
                     Logger.getLogger(ParcelamentoController.class.getName()).log(Level.SEVERE, null, ex);
                 } catch (IOException ex) {
                     Logger.getLogger(ParcelamentoController.class.getName()).log(Level.SEVERE, null, ex);
                 } catch (XmlException ex) {
                     Logger.getLogger(ParcelamentoController.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 limparParcelamento();
                 
             } catch (PropertyVetoException ex) {
                 Logger.getLogger(ParcelamentoController.class.getName()).log(Level.SEVERE, null, ex);
             }
        
         });

        t1.start();

    }

    private void limparParcelamento() throws PropertyVetoException {

        cbDoc1.setSelected(false);
        cbDoc2.setSelected(false);
        cbDoc4.setSelected(false);
        cbDoc3.setSelected(false);
        cbDoc5.setSelected(false);
        cbDoc7.setSelected(false);
        cbDoc8.setSelected(false);
        cbDoc9.setSelected(false);
        cbDoc10.setSelected(false);
        

        table_RestricoesTerceiros.getItems().clear();
        tableRestricoes.getItems().clear();
        tableParcelamento.getItems().clear();
        tp.limparTelaPrincipalController();

    }

}
