/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import capturaSisbb.capturasCartao;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import java.beans.PropertyVetoException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.DadosIniciais;
import model.utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.BloqueioDataValor;
import CurrencyField.CurrencyField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.BloqueioDatasCaptura;
import model.TextFieldFormatter;
import subsidioCartao.MainApp;

/**
 * FXML Controller class
 *
 * @author f3295813
 */
public class TelaPrincipalController implements Initializable {

    //========================= Listas e observable Lists===================================
    List<DadosIniciais> listaPlastico = new ArrayList<>();

    private MainApp mainApp;
    private ObservableList<DadosIniciais> observableListPlastico;
    private ObservableList<BloqueioDataValor> observableDataValor;
    private ObservableList<BloqueioDatasCaptura> observableDatasCaptura;
    List<BloqueioDataValor> listaDataValor = new ArrayList<>();
    List<BloqueioDatasCaptura> listaDatasCaptura = new ArrayList<>();

    String cpf = "";
    String npj = "";
    String matricula = "";
    String autor = "";
    int sumula = 0;
    public static JanelaSisbb sisbb;
    Thread t1;
    Thread t2;
    Thread t3;

    utils util = new utils();
    capturasCartao capturas = new capturasCartao();
    ParcelamentoController parcelamentoCaptura = new ParcelamentoController();
    BloqueioController bloqueioCaptura = new BloqueioController();

    //====================================Tabelas e colunas================================================= 
    @FXML
    private TableColumn<DadosIniciais, String> colDadosGerais_Conta;
    @FXML
    private TableColumn<DadosIniciais, String> colDadosGerais_TipoPlastico;
    @FXML
    private TableColumn<DadosIniciais, String> colDadosGerais_NrCartao;
    @FXML
    private TableColumn<DadosIniciais, String> colDadosGerais_NomePlastico;
    @FXML
    private TableColumn<DadosIniciais, String> colDadosGerais_Restricao;
    @FXML
    private TableColumn<DadosIniciais, String> colDadosGerais_Vencido;
    @FXML
    private TableColumn<DadosIniciais, Boolean> colDadosGerais_Selecao;
    @FXML
    private TableView<DadosIniciais> tableDadosGerais;
    @FXML
    private TableView<BloqueioDataValor> table_DataValor;
    @FXML
    private TableColumn<BloqueioDataValor, String> colData_Bloqueio;
    @FXML
    private TableColumn<BloqueioDataValor, String> colValor_Bloqueio;

    //=====================================================================================================
    @FXML
    private AnchorPane AnchorPanePrincipal;
    @FXML
    private ScrollPane ScrollPaneEsquerdo;
    @FXML
    private JFXTextField txt_NPJ;
    @FXML
    private JFXTextField txt_CPF;
    @FXML
    private JFXButton btn_BusNpj;
    @FXML
    private JFXTextField txtChave;
    @FXML
    private ImageView imageView;
    @FXML
    private JFXButton btn_buscarTable;
    @FXML
    private AnchorPane AP_ParcelamentoFatura;
    @FXML
    private JFXCheckBox cb_ParcelamentoFatura;
    @FXML
    private Tab tab_ParcelamentoFatura;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tabInformacoes;
    @FXML
    private JFXCheckBox cb_Bloqueio;
    @FXML
    private JFXCheckBox cb_Generico;
    @FXML
    private JFXCheckBox cb_NaoReconhecida;
    @FXML
    private JFXTextField txt_Autor;
    @FXML
    private TextField txtData;
    @FXML
    private CurrencyField txtValor;
    @FXML
    private JFXButton btn_InserirDataValor;
    @FXML
    private Tab tab_BloqueioRestricao;
    @FXML
    private AnchorPane AP_BloqueioRestricao;
    @FXML
    private JFXButton btn_ExcluirDataValor;
    @FXML
    private TableView<BloqueioDatasCaptura> table_DatasBloqueio;
    @FXML
    private TableColumn<String, BloqueioDatasCaptura> colDataInicio_Bloqueio;
    @FXML
    private TableColumn<String, BloqueioDatasCaptura> colDataFim_Bloqueio;
    @FXML
    private TextField txtDataInicio;
    @FXML
    private TextField txtDataFim;
    @FXML
    private JFXButton btn_InserirDatas;
    @FXML
    private JFXButton btn_ExcluirDatas;
    @FXML
    private TextField txt_DataFimRestricao;
    @FXML
    private TextField txt_DataInicioRestricao;
    @FXML
    private JFXCheckBox cb_CapturarTudoRestricao;

    public TelaPrincipalController() throws PropertyVetoException {

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        matricula = System.getProperty("user.name").toUpperCase();
        txtChave.setText(matricula);
        Image imagem = new Image("/views/imagens/logo.png");
        imageView.setImage(imagem);

        txtDataInicio.setText("01/2018");
        txtDataFim.setText("07/2019");

        CurrencyField cur = new CurrencyField();

        ScrollPane sp = new ScrollPane();
        sp.setContent(sp);


// Usando esta property você pode ver as mudanças no valor do textfield
        cur.amountProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(newValue.doubleValue());
            }
        });
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void pegaInformacoesIniciais(ActionEvent event) throws Throwable {
        
        limparTelaPrincipalControllerSemCpf();
        
        t1 = new Thread(() -> {
            
           
            
            listaPlastico.clear();

            if (sisbb != null && sisbb.getJanela() != null) {
                sisbb.rotinaEncerramento();
            }

            try {

                cpf = txt_CPF.getText().trim().replace(".", "").replace("-", "");
                npj = txt_NPJ.getText().trim().replace(".", "").replace("-", "");

              

                    if (cpf != null && cpf.length() != 0 && cpf.length() == 11 && npj != null && npj.length() != 0) {

                        try {
                            //                    JanelaSisbb sisbb = new JanelaSisbb();
                            this.sisbb = new JanelaSisbb();
                            sisbb.setTamanho(600, 500);
                            listaPlastico = capturas.dadosIniciais(sisbb, cpf);
                        } catch (PropertyVetoException ex) {
                            Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (Throwable ex) {
                            Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        

                       
                        if (!listaPlastico.isEmpty()) {

                            autor = listaPlastico.get(0).getAutor();
                            txt_Autor.setText(autor);

                            colDadosGerais_Selecao.setCellValueFactory(new PropertyValueFactory<>("selected"));
                            colDadosGerais_Conta.setCellValueFactory(new PropertyValueFactory<>("conta"));
                            colDadosGerais_TipoPlastico.setCellValueFactory(new PropertyValueFactory<>("tipoPlastico"));
                            colDadosGerais_NrCartao.setCellValueFactory(new PropertyValueFactory<>("nrCartao"));
                            colDadosGerais_NomePlastico.setCellValueFactory(new PropertyValueFactory<>("nomePlastico"));
                            colDadosGerais_Restricao.setCellValueFactory(new PropertyValueFactory<>("restricao"));
                            colDadosGerais_Vencido.setCellValueFactory(new PropertyValueFactory<>("vencido"));
                            colDadosGerais_Selecao.setCellFactory(CheckBoxTableCell.forTableColumn(colDadosGerais_Selecao));
                           

                            // coluna editável
//                        colDadosGerais_Conta.setCellFactory(TextFieldTableCell.<DadosIniciais>forTableColumn());
//                        colDadosGerais_Conta.setOnEditCommit(
//                                (CellEditEvent<DadosIniciais, String> t) -> {
//                                    ((DadosIniciais) t.getTableView().getItems().get(
//                                            t.getTablePosition().getRow())).setConta(t.getNewValue());
//                                });
//
//                        
                            colDadosGerais_Selecao.setCellValueFactory((CellDataFeatures<DadosIniciais, Boolean> param) -> {
                                DadosIniciais dados = param.getValue();
                                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(dados.isSelected());
                                // Note: singleCol.setOnEditCommit(): Not work for
                                // CheckBoxTableCell.
                                // When "Single?" column change.
                                booleanProp.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                                    dados.setSelected(newValue);
                                });
                                return booleanProp;
                            });
                            colDadosGerais_Selecao.setCellFactory((TableColumn<DadosIniciais, Boolean> p) -> {
                                CheckBoxTableCell<DadosIniciais, Boolean> cell = new CheckBoxTableCell<DadosIniciais, Boolean>();
                                return cell;
                            });

                            observableListPlastico = FXCollections.observableList(listaPlastico);

                            tableDadosGerais.setItems(observableListPlastico);

                            util.alertaGeralInformacao("Atenção", "Captura finalizada!", null);

                        } else {
                            
                              Platform.runLater(() -> {

                            util.alertaGeral("Atenção", "CPF incorreto ou o cliente não possui lançamentos.", "Gentileza verificar os dados");
                
                 });

                        }
                    }else{
                        
                        Platform.runLater(() -> { 
                      util.alertaValidacao("Digite o NPJ e o CPF com valores númericos e quantidade suficiente de dígitos");
                       }); 
                        
                        
                    }
               

            } catch (Exception e) {
                System.out.println(e);
            } catch (Throwable ex) {
                Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        t1.start();

    }

    @FXML
    private void pegaDadosTabela(ActionEvent event) throws Throwable {
        
        
       
        t2 = new Thread(() -> {
            
            
        if(cb_ParcelamentoFatura.isSelected()){
            sumula = 1;
            
            
        }else if(cb_Bloqueio.isSelected()){
            sumula = 2;
            
        }else if(cb_NaoReconhecida.isSelected()){
            sumula = 3;
            
        } else if(cb_Generico.isSelected()){
            sumula = 4;
            
        }         
            
            
            
        String dataRestInicio ="";
        String dataRestFim = "";
            
            
          if ((txt_DataInicioRestricao.getText().length() != 0 & txt_DataFimRestricao.getText().length() != 0 & txt_DataInicioRestricao.getText().trim().length() == 7 & txt_DataFimRestricao.getText().trim().length() == 7) || cb_CapturarTudoRestricao.isSelected() ) {
             
              dataRestInicio = txt_DataInicioRestricao.getText().replace("/", "");
              dataRestFim = txt_DataFimRestricao.getText().replace("/", "");
              boolean restTudo = cb_CapturarTudoRestricao.isSelected();
              
        
            
            List<DadosIniciais> listaOperacoesCaptura = new ArrayList<>();
            List<DadosIniciais> data = new ArrayList<>();
            
            if (!data.isEmpty()) {
                data.clear();
            }

            data = tableDadosGerais.getItems();

            for (DadosIniciais t : data) {
                if (t.isSelected()) {
                    listaOperacoesCaptura.add(t);
                }
            }
            if (!listaOperacoesCaptura.isEmpty()) {

                switch (sumula) {
                    
 //=======================================================Súmula Parcelamento=========================================================================                   
                    case 1: {
                        try {
                            parcelamentoCaptura.capturaParcelamento(matricula, listaOperacoesCaptura, cpf, npj, autor, sisbb, this, dataRestFim, dataRestInicio, restTudo);
                        } catch (Throwable ex) {
                            Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    break;
                    
 //=======================================================Súmula Bloqueio=========================================================================                         
                    case 2:

                        List<BloqueioDataValor> listaBloqueioDataValor = new ArrayList<>();
                        listaBloqueioDataValor = table_DataValor.getItems();

                        List<BloqueioDatasCaptura> listaBloqueioDatas = new ArrayList<>();
                        listaBloqueioDatas = table_DatasBloqueio.getItems();

                        try {

                            bloqueioCaptura.capturaBloqueio(matricula, listaBloqueioDataValor, listaOperacoesCaptura, listaBloqueioDatas, cpf, npj, autor, sisbb, this, dataRestFim, dataRestInicio, restTudo);

                        } catch (Throwable ex) {
                            Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        break;
                        
 //=======================================================Súmula Transação não reconhecida =========================================================================                             
                    case 3:
                        
                        
                        
                        break;
                        
 //=======================================================Súmula Genérica======================================================================================================     
                        
                    case 4:
                        
                        
                        
                        
                        
                        break;
                    default:

                        Platform.runLater(() -> {
                            
                            util.validarSumula(cb_Bloqueio, cb_Generico, cb_NaoReconhecida, cb_ParcelamentoFatura);
                        });
                }
                
                
                
                

            } else {
                Platform.runLater(() -> {

                    util.alertaCheckBoxSemMarcacao();
                });

            }
                        
            
           }else{
                Platform.runLater(() -> {
                util.alertaGeral("Atenção", "Verifique se a data possui todos os campos: mm.aaaa", "Verifique se todos os campos foram preenchidos!");
                });
          }    
            
            
            
            

        });

        t2.start();

    }

    @FXML
    private void ParcelamentoFatura(ActionEvent event) throws Exception {

        if (cb_ParcelamentoFatura.isSelected()) {

            tabPane.getSelectionModel().select(tab_ParcelamentoFatura);
            sumula = 1;

        } else {
            tabPane.getSelectionModel().select(tabInformacoes);
            sumula = 0;

        }
        if (cb_ParcelamentoFatura.isSelected()) {
            cb_Bloqueio.setSelected(false);
            cb_Generico.setSelected(false);
            cb_NaoReconhecida.setSelected(false);

        }

        String path = "/view/ParcelamentoFatura.fxml";
        parcelamentoCaptura = (ParcelamentoController) mainApp.showCenterAnchorPaneWithReturn(path, parcelamentoCaptura, AP_ParcelamentoFatura);

    }

    @FXML
    private void Bloqueio(ActionEvent event) {

        if (cb_Bloqueio.isSelected()) {

            tabPane.getSelectionModel().select(tab_BloqueioRestricao);
            sumula = 2;

        } else {
            tabPane.getSelectionModel().select(tabInformacoes);
            sumula = 0;

        }
        if (cb_Bloqueio.isSelected()) {
            cb_ParcelamentoFatura.setSelected(false);
            cb_Generico.setSelected(false);
            cb_NaoReconhecida.setSelected(false);

        }

        String path = "/view/BloqueioRestricao.fxml";
        bloqueioCaptura = (BloqueioController) mainApp.showCenterAnchorPaneWithReturn(path, bloqueioCaptura, AP_BloqueioRestricao);
    }

    @FXML
    private void Generico(ActionEvent event) {

        if (cb_Generico.isSelected()) {
            sumula = 3;
            tabPane.getSelectionModel().select(tab_ParcelamentoFatura);

        } else {
            tabPane.getSelectionModel().select(tabInformacoes);
            sumula = 0;

        }
        if (cb_Generico.isSelected()) {
            cb_ParcelamentoFatura.setSelected(false);
            cb_Bloqueio.setSelected(false);
            cb_NaoReconhecida.setSelected(false);

        }
    }

    @FXML
    private void NaoReconhecido(ActionEvent event) {

        if (cb_NaoReconhecida.isSelected()) {
            sumula = 4;
            tabPane.getSelectionModel().select(tab_ParcelamentoFatura);

        } else {
            tabPane.getSelectionModel().select(tabInformacoes);
            sumula = 0;

        }
        if (cb_NaoReconhecida.isSelected()) {
            cb_ParcelamentoFatura.setSelected(false);
            cb_Bloqueio.setSelected(false);
            cb_Generico.setSelected(false);

        }

    }

    public void limparTelaPrincipalController() {

        txt_Autor.clear();
        txt_CPF.clear();
        txt_NPJ.clear();
        cb_ParcelamentoFatura.setSelected(false);
        cb_Bloqueio.setSelected(false);
        cb_Generico.setSelected(false);
        cb_NaoReconhecida.setSelected(false);
        tableDadosGerais.getItems().clear();
        cb_CapturarTudoRestricao.setSelected(false);
        txt_DataFimRestricao.clear();
        txt_DataInicioRestricao.clear();
    }
    
     public void limparTelaPrincipalControllerSemCpf() {

        txt_Autor.clear();
       
        cb_ParcelamentoFatura.setSelected(false);
        cb_Bloqueio.setSelected(false);
        cb_Generico.setSelected(false);
        cb_NaoReconhecida.setSelected(false);
        tableDadosGerais.getItems().clear();
        cb_CapturarTudoRestricao.setSelected(false);
        txt_DataFimRestricao.clear();
        txt_DataInicioRestricao.clear();
    }


    @FXML
    private void montaTabelaDataValor(ActionEvent event) {

        BloqueioDataValor bdv = new BloqueioDataValor();

        String data = txtData.getText();
        String valor = txtValor.getText();

        if (txtData.getText().length() != 0 & txtValor.getText().length() != 0 & txtData.getText().trim().length() == 10) {

            bdv.setData(data);
            bdv.setValor(valor);

            listaDataValor.add(bdv);

            colData_Bloqueio.setCellValueFactory(new PropertyValueFactory<>("data"));
            colValor_Bloqueio.setCellValueFactory(new PropertyValueFactory<>("valor"));

            observableDataValor = FXCollections.observableList(listaDataValor);

            table_DataValor.setItems(observableDataValor);

            txtData.clear();
            txtValor.clear();

        } else {

            util.alertaGeral("Atenção", "Verifique se a data possui todos os campos: dd.mm.aaaa", "Verifique se todos os campos foram preenchidos!");

        }

    }

    @FXML
    private void excluiTabelaDataValor(ActionEvent event) {

        int linhaSelecionada = table_DataValor.getSelectionModel().getSelectedIndex();
        table_DataValor.getItems().remove(linhaSelecionada);

    }

    @FXML
    private void inputDataKeyTyped() throws ParseException {

        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("##.##.####");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtData);
        tff.formatter();

    }

    @FXML
    private void inputDataKeyTypedDataInicial() throws ParseException {

        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("##/####");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtDataInicio);
        tff.formatter();

    }

    @FXML
    private void inputDataKeyTypedDataFinal() throws ParseException {

        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("##/####");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtDataFim);
        tff.formatter();

    }

    @FXML
    private void montaTabelaDatas(ActionEvent event) {

        BloqueioDatasCaptura bdv = new BloqueioDatasCaptura();

        String dataInicio = txtDataInicio.getText();
        String dataFim = txtDataFim.getText();

        if (txtDataInicio.getText().length() != 0 & txtDataFim.getText().length() != 0 & txtDataInicio.getText().trim().length() == 7 & txtDataFim.getText().trim().length() == 7) {

            bdv.setDataInicio(dataInicio);
            bdv.setDataFim(dataFim);

            listaDatasCaptura.add(bdv);

            colDataInicio_Bloqueio.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
            colDataFim_Bloqueio.setCellValueFactory(new PropertyValueFactory<>("dataFim"));

            observableDatasCaptura = FXCollections.observableList(listaDatasCaptura);

            table_DatasBloqueio.setItems(observableDatasCaptura);

            txtDataInicio.clear();
            txtDataFim.clear();

        } else {

            util.alertaGeral("Atenção", "Verifique se a data possui todos os campos: dd.mm.aaaa", "Verifique se todos os campos foram preenchidos!");

        }

    }

    @FXML
    private void excluiTabelaDatas(ActionEvent event) {

        int linhaSelecionada = table_DatasBloqueio.getSelectionModel().getSelectedIndex();
        table_DatasBloqueio.getItems().remove(linhaSelecionada);
    }

    @FXML
    private void inputDataKeyTypedDataFinalRestricao(KeyEvent event) {
        
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("##/####");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txt_DataFimRestricao);
        tff.formatter();
        
    }

    @FXML
    private void inputDataKeyTypedDataInicialRestricao(KeyEvent event) {
        
        
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("##/####");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txt_DataInicioRestricao);
        tff.formatter();
    }

    @FXML
    private void desabilitarDatasRest(MouseEvent event) {

        if (cb_CapturarTudoRestricao.isSelected()) {

            txt_DataInicioRestricao.setDisable(true);
            txt_DataFimRestricao.setDisable(true);
            txt_DataFimRestricao.clear();
            txt_DataInicioRestricao.clear();

        } else {

            txt_DataInicioRestricao.setDisable(false);
            txt_DataFimRestricao.setDisable(false);
        }

    }

}
