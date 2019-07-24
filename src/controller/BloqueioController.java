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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.BloqueioCorrespondencia;
import model.BloqueioDataValor;
import model.BloqueioDatasCaptura;
import model.DadosIniciais;
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
    private TableColumn<?, ?> colBloqueio_Instituicao;

    @FXML
    private TableColumn<?, ?> colBloqueio_DtaRegistro;
    @FXML
    private TableColumn<?, ?> colBloqueio_Valor;
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
    private TableView<?> tableBloqueio_restricoes;
    @FXML
    private TableColumn<?, ?> colBloqueio_Tipo;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public void capturaBloqueio(String matricula, List<BloqueioDataValor> listaBloqueioDataValor, List<DadosIniciais> listaOperacoesCaptura, List<BloqueioDatasCaptura> listaBloqueioDatas, String cpf, String npj, String autor, JanelaSisbb sisbb, TelaPrincipalController tp) throws PropertyVetoException, Throwable {
        ObservableList<TransacaoNaoAutorizada> observableListAutorizacao = null;
        ObservableList<TransacaoNaoAutorizada> observableListSemLimite;
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

            if(!listCorrespondencia.isEmpty()){
            
            
            for (BloqueioCorrespondencia b : listCorrespondencia) {

                String parte1 = b.getDtaPostagem();
                String parte2 = b.getServicoECT();
                String parte3 = b.getEndereco();
                
                concatCorrespondencia += parte1 + "\n" + parte2 + "\n" + parte3 + "\n" + "\n";
                
                
            }
            txtCorrespondencia.setText(concatCorrespondencia);
            
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
