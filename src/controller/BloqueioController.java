/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import br.com.bb.jibm3270.RoboException;
import capturaSisbb.CapturaBloqueioRestricao;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import dao.ConsultaSQL;
import java.beans.PropertyVetoException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.BloqueioCorrespondencia;
import model.BloqueioDataValor;
import model.BloqueioDatasCaptura;
import model.DadosIniciais;
import model.RestricoesBB;
import model.RestricoesReplicadas;
import model.RestricoesTerceiros;
import model.TransacaoNaoAutorizada;
import model.utils;
import poi.PagamentoParcelado;

/**
 * FXML Controller class
 *
 * @author f3295813
 */
public class BloqueioController extends AbstractController implements Initializable {

    public TelaPrincipalController tp;
    CapturaBloqueioRestricao capBloqueio = new CapturaBloqueioRestricao();

    public ObservableList<BloqueioDataValor> observableListBloqueio;

    @FXML
    private AnchorPane AP_Parcelamento;
    @FXML
    private Text txtRendas;
    @FXML
    private Text txtRendas1;
    @FXML
    private Text txt_Doc21;
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
    private JFXButton btn_GerarWordParcelamento;
    @FXML
    private Text txt_Restricao1;
    @FXML
    private Text txt_Restricao11;
    @FXML
    private Text txt_Restricao111;
    @FXML
    private Text txt_Restricao1111;

    utils util = new utils();
    String cpf;
    String npj;
    String autor;
    String vencido;
    String suspeitaFraude;
    String matricula;
    List listaOperacoesCaptura;
    List listaBloqueioDataValor;
    List listaBloqueioDatas;
    String dataRestFim;
    String dataRestInicio;
    boolean restTudo;
    
    @FXML
    private JFXCheckBox cbBloqueio_Anotacao;
    @FXML
    private JFXCheckBox cbBloqueio_LimiteInsuficiente;
    @FXML
    private JFXCheckBox cbBloqueio_LimiteVigente;
    @FXML
    private JFXCheckBox cbBloqueio_Fraude;
    @FXML
    private JFXCheckBox cbBloqueio_CartaoVencido;
    @FXML
    private JFXCheckBox cbBloqueio_Outros;
    @FXML
    private TableView<TransacaoNaoAutorizada> tableBloqueio_Transacoes;
    @FXML
    private TableColumn<TransacaoNaoAutorizada, String> colBloqueio_ValorCompra;
    @FXML
    private TableColumn<TransacaoNaoAutorizada, String> colBloqueio_ValorLimite;
    @FXML
    private TableColumn<TransacaoNaoAutorizada, String> colBloqueio_Motivo;
    @FXML
    private TableColumn<TransacaoNaoAutorizada, String> colBloqueio_DtaOcorrencia;
    @FXML
    private TableView<TransacaoNaoAutorizada> tableBloqueio_Motivos;
    @FXML
    private TableColumn<TransacaoNaoAutorizada, String> colMotivos_Data;
    @FXML
    private TableColumn<TransacaoNaoAutorizada, String> colMotivos_Valor;
    @FXML
    private TableColumn<TransacaoNaoAutorizada, String> colMotivos_Resposta;
    @FXML
    private TableColumn<TransacaoNaoAutorizada, String> colMotivos_RazaoRecusa;
    @FXML
    private TableColumn<TransacaoNaoAutorizada, String> colMotivos_Plastico;
    @FXML
    private TextArea txtCorrespondencia;
    @FXML
    private TableView<RestricoesBB> tb_RestricoesBB;
    @FXML
    private TableColumn<RestricoesBB, Boolean> colRestricoesBB_selecao;
    @FXML
    private TableColumn<RestricoesBB, String> colRestricoesBB_Tipo;
    @FXML
    private TableColumn<RestricoesBB, String> colRestricoesBB_Modalidade;
    @FXML
    private TableColumn<RestricoesBB, String> colRestricoesBB_Valor;
    @FXML
    private TableColumn<RestricoesBB, String> colRestricoesBB_dtaRegistro;
    @FXML
    private TableColumn<RestricoesBB, String> colRestricoesBB_dtaBaixa;
    @FXML
    private TableView<RestricoesTerceiros> tb_RestricoesTerceiros;
    @FXML
    private TableColumn<RestricoesTerceiros, Boolean> colTerceiros_selecao;
    @FXML
    private TableColumn<RestricoesTerceiros, String> colTerceiros_Tipo;
    @FXML
    private TableColumn<RestricoesTerceiros, String> colTerceiros_DtaRegistro;
    @FXML
    private TableColumn<RestricoesTerceiros, String> colTerceiros_DtaBaixa;
    @FXML
    private TableView<RestricoesReplicadas> tb_RestricoesReplicadas;
    @FXML
    private TableColumn<RestricoesReplicadas, Boolean> colReplicacoes_selecao;
    @FXML
    private TableColumn<RestricoesReplicadas, String> colReplicacoes_Tipo;
    @FXML
    private TableColumn<RestricoesReplicadas, String> colReplicacoes_DtaRegistro;
    @FXML
    private TableColumn<RestricoesReplicadas, String> colReplicacoes_DtaBaixa;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public void capturaBloqueio(String matricula, List<BloqueioDataValor> listaBloqueioDataValor, List<DadosIniciais> listaOperacoesCaptura, List<BloqueioDatasCaptura> listaBloqueioDatas, String cpf, String npj, String autor, JanelaSisbb sisbb, TelaPrincipalController tp, String dataRestFim, String dataRestInicio, boolean restTudo) throws PropertyVetoException, Throwable {
        ObservableList<TransacaoNaoAutorizada> observableListAutorizacao = null;
        ObservableList<TransacaoNaoAutorizada> observableListSemLimite;
        ObservableList<RestricoesBB> observableListRestricoesBB;
        ObservableList<RestricoesReplicadas> observableListRestricoesReplicadas;
         ObservableList<RestricoesTerceiros> observableListRestricoesTerceiros;

        List<TransacaoNaoAutorizada> transNaoAutorizada = new ArrayList<>();
        List<TransacaoNaoAutorizada> transSemLimite = new ArrayList<>();
        List<BloqueioCorrespondencia> listCorrespondencia = new ArrayList<>();

        String concatCorrespondencia = "";

        this.cpf = cpf;
        this.npj = npj;
        this.autor = autor;
        this.tp = tp;
        this.listaBloqueioDataValor = listaBloqueioDataValor;
        this.listaOperacoesCaptura = listaOperacoesCaptura;
        this.listaBloqueioDatas = listaBloqueioDatas;
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

            if (capBloqueio.cadastroRestritivo(matricula, listaBloqueioDataValor, listaOperacoesCaptura, cpf, npj, autor, sisbb, tp)) {
                cbBloqueio_Anotacao.setSelected(true);
            }

            if (capBloqueio.creditoInsuficiente(matricula, listaBloqueioDataValor, listaBloqueioDatas, listaOperacoesCaptura, cpf, sisbb, tp, transNaoAutorizada, vencido, suspeitaFraude)) {

                for (TransacaoNaoAutorizada t : transNaoAutorizada) {
                    String tipo = t.getResposta().substring(0, 2);
                    if (tipo.equals("51")) {
                        transSemLimite.add(t);
                        cbBloqueio_LimiteInsuficiente.setSelected(true);
                    }

                }

                colBloqueio_DtaOcorrencia.setCellValueFactory(new PropertyValueFactory<>("dataOcorrencia"));
                colBloqueio_ValorCompra.setCellValueFactory(new PropertyValueFactory<>("valorCompra"));
                colBloqueio_ValorLimite.setCellValueFactory(new PropertyValueFactory<>("valorLimite"));
                colBloqueio_Motivo.setCellValueFactory(new PropertyValueFactory<>("resposta"));

                observableListSemLimite = FXCollections.observableList(transSemLimite);
                tableBloqueio_Transacoes.setItems(observableListSemLimite);

                colMotivos_Data.setCellValueFactory(new PropertyValueFactory<>("dataOcorrencia"));
                colMotivos_RazaoRecusa.setCellValueFactory(new PropertyValueFactory<>("razaoRecusa"));
                colMotivos_Valor.setCellValueFactory(new PropertyValueFactory<>("valorCompra"));
                colMotivos_Resposta.setCellValueFactory(new PropertyValueFactory<>("resposta"));
                colMotivos_Plastico.setCellValueFactory(new PropertyValueFactory<>("plastico"));

                observableListAutorizacao = FXCollections.observableList(transNaoAutorizada);
                tableBloqueio_Motivos.setItems(observableListAutorizacao);

            }

            // capBloqueio.restricoes(matricula, sisbb, cpf);
            listCorrespondencia = capBloqueio.comunicacaoCliente(matricula, sisbb, cpf);

            if (!listCorrespondencia.isEmpty()) {

                for (BloqueioCorrespondencia b : listCorrespondencia) {

                    String parte1 = b.getDtaPostagem();
                    String parte2 = b.getServicoECT();
                    String parte3 = b.getEndereco();

                    concatCorrespondencia += parte1 + "\n" + parte2 + "\n" + parte3 + "\n" + "\n";

                }
                txtCorrespondencia.setText(concatCorrespondencia);

            }

            if (!(observableListRestricoesBB = FXCollections.observableList(capBloqueio.restricoesBB(sisbb, cpf, dataRestFim, dataRestInicio, restTudo))).isEmpty()) {

                colRestricoesBB_selecao.setCellValueFactory(new PropertyValueFactory<>("selected"));
                colRestricoesBB_Modalidade.setCellValueFactory(new PropertyValueFactory<>("modalidade"));
                colRestricoesBB_Valor.setCellValueFactory(new PropertyValueFactory<>("valor"));                
                colRestricoesBB_Tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
                colRestricoesBB_dtaRegistro.setCellValueFactory(new PropertyValueFactory<>("dtaRegistro"));
                colRestricoesBB_dtaBaixa.setCellValueFactory(new PropertyValueFactory<>("dtaBaixa"));
                colRestricoesBB_selecao.setCellFactory(CheckBoxTableCell.forTableColumn(colRestricoesBB_selecao));

                colRestricoesBB_selecao.setCellValueFactory((TableColumn.CellDataFeatures<RestricoesBB, Boolean> param) -> {
                    RestricoesBB dados = param.getValue();
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

                tb_RestricoesBB.setItems(observableListRestricoesBB);

            } 
            
            
           if (!(observableListRestricoesReplicadas = FXCollections.observableList(capBloqueio.restricoesReplicadas(sisbb, cpf, dataRestFim, dataRestInicio, restTudo))).isEmpty()) {
               
               colReplicacoes_selecao.setCellValueFactory(new PropertyValueFactory<>("selected"));
               colReplicacoes_DtaBaixa.setCellValueFactory(new PropertyValueFactory<>("dtaBaixa"));
               colReplicacoes_DtaRegistro.setCellValueFactory(new PropertyValueFactory<>("dtaOcorrencia"));
               colReplicacoes_Tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

               colReplicacoes_selecao.setCellFactory(CheckBoxTableCell.forTableColumn(colReplicacoes_selecao));

               colReplicacoes_selecao.setCellValueFactory((TableColumn.CellDataFeatures<RestricoesReplicadas, Boolean> param) -> {
                   RestricoesReplicadas dados = param.getValue();
                   SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(dados.isSelected());
                   booleanProp.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                       dados.setSelected(newValue);
                   });
                   return booleanProp;
               });
               colReplicacoes_selecao.setCellFactory((TableColumn<RestricoesReplicadas, Boolean> p) -> {
                   CheckBoxTableCell<RestricoesReplicadas, Boolean> cell = new CheckBoxTableCell<>();
                   return cell;
               });

               tb_RestricoesReplicadas.setItems(observableListRestricoesReplicadas);


               
               if (!(observableListRestricoesTerceiros = FXCollections.observableList(capBloqueio.restricoesTerceiros(sisbb, cpf, dataRestFim, dataRestInicio, restTudo))).isEmpty()) {

                   colTerceiros_selecao.setCellValueFactory(new PropertyValueFactory<>("selected"));
                   colTerceiros_DtaBaixa.setCellValueFactory(new PropertyValueFactory<>("dtaBaixa"));
                   colTerceiros_DtaRegistro.setCellValueFactory(new PropertyValueFactory<>("dtaRegistro"));
                   colTerceiros_Tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

                   colTerceiros_selecao.setCellFactory(CheckBoxTableCell.forTableColumn(colTerceiros_selecao));

                   colTerceiros_selecao.setCellValueFactory((TableColumn.CellDataFeatures<RestricoesTerceiros, Boolean> param) -> {
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

                   tb_RestricoesTerceiros.setItems(observableListRestricoesTerceiros);  
                 
                 
                 
                 
                 
                 
                 
                 
             }  
               
               
               
               
               
               
               
               
               
               
               
               
               
               
           } 
            
            
            
            
            Platform.runLater(() -> {

                util.alertaGeralInformacao("Atenção", "Captura finalizada com sucesso!", "Verifique os itens na tabela para gerar a súmula.");

            });

        } catch (RoboException e) {
        }

    }

    @FXML
    private void geraSumulaBloqueio(ActionEvent event) throws Throwable {
        ConsultaSQL consultaSQL = new ConsultaSQL();

        consultaSQL.inserir(npj, matricula, "Bloqueio ou Restrição ao uso");

        PagamentoParcelado wordParcelamento = new PagamentoParcelado();
        //  wordParcelamento.wordPagamentoParcelado(observableListParcelamento, cpf, npj, autor, cbDoc1, cbDoc2, cbDoc3, cbDoc4, cbDoc5, cbDoc6, cbDoc7);
        //limparParcelamento();

    }

}
