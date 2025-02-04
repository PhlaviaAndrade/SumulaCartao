/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subsidioCartao;

import controller.AbstractController;
import controller.RootLayoutController;
import controller.TelaPrincipalController;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author f3295813
 */
public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
   

    /**
     *
     */
    public MainApp() {
        // Add some sample data

    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Subsídio Proativo - CARTÃO");
        primaryStage.resizableProperty().setValue(Boolean.FALSE);

       

            initRootLayout();
            showMenuOpcoesAnchorPane();

     

    }

    /**
     * Inicializa o root layout (layout base).
     */
    public void initRootLayout() {

        Platform.runLater(() -> {
            try {

                //Carrega o root layout do arquivo fxml
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("/view/RootLayout.fxml"));
                rootLayout = (BorderPane) loader.load();

                //Mostra a scene (cena) contendo o root layout.
                Scene scene = new Scene(rootLayout);
                primaryStage.setScene(scene);

                // Dá ao controller o acesso ao main app.
                RootLayoutController controller = loader.getController();
                controller.setMainApp(this);

                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

    /**
     * Mostra o menu de opções de captura dentro do root layout.
     */
    public void showMenuOpcoesAnchorPane() {
        Platform.runLater(() -> {

        try {
            //Carrega o anchor pane do menu de opções.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/TelaPrincipal.fxml"));
            AnchorPane menuOpcoes = (AnchorPane) loader.load();

            //Define o menu de opções na esquerda
            rootLayout.setLeft(menuOpcoes);

            //Dá ao controlador acesso à main app.
            TelaPrincipalController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
        });

    }

    /**
     * Carrega o anchor pane no centro do RootLayout
     *
     * @param path
     * @param controller
     */
    public void showCenterAnchorPane(String path, AbstractController controller, AnchorPane a) {

        try {
            //Carrega o anchor pane.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(path));
            AnchorPane anchorPane = (AnchorPane) loader.load();

            //Define a tela no centro do root layout
            // rootLayout.set(anchorPane);
            a.getChildren().setAll(anchorPane);

            //Dá ao controlador acesso à main app.
            controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public AbstractController showCenterAnchorPaneWithReturn(String path, AbstractController controller, AnchorPane a) {

        try {
            //Carrega o anchor pane.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(path));
            AnchorPane anchorPane = (AnchorPane) loader.load();

            //Define a tela no centro do root layout
            // rootLayout.set(anchorPane);
            a.getChildren().setAll(anchorPane);

            //Dá ao controlador acesso à main app.
            controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return controller;
    }
    
    

    
     public AbstractController showCenterAnchorPaneWithReturnScrollPane(String path, AbstractController controller, AnchorPane a) {

        try {
            //Carrega o anchor pane.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(path));
            ScrollPane scrollPane = (ScrollPane) loader.load();

            //Define a tela no centro do root layout
            // rootLayout.set(anchorPane);
           // a.getChildren().setAll(anchorPane);
            a.getChildren().setAll(scrollPane);

            //Dá ao controlador acesso à main app.
            controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return controller;
    }
    /**
     * Retorna o palco principal
     *
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Retorna o arquivo de preferências da pessoa, o último arquivo que foi
     * aberto. As preferências são lidas do registro específico do SO (Sistema
     * Operacional). Se tais prefêrencias não puderem ser encontradas, ele
     * retorna null.
     *
     * @return
     */
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Define o caminho do arquivo do arquivo carregado atual. O caminho é
     * persistido no registro específico do SO (Sistema Operacional).
     *
     * @param file O arquivo ou null para remover o caminho
     */
    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("AddressApp");
        }
    }

}
