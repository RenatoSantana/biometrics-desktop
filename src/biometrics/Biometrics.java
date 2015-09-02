/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometrics;


import biometrics.LoginController;
import biometrics.model.Funcionario;
import java.io.InputStream;
import javafx.fxml.JavaFXBuilderFactory;
import biometrics.model.User;
import biometrics.security.Authenticator;


import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author 205406813
 */
public class Biometrics extends Application {
    private Stage stage;
    private User loggedUser;
    private BorderPane rootLayout;
    private final double MINIMUM_WINDOW_WIDTH = 1000.0;
    private final double MINIMUM_WINDOW_HEIGHT = 600.0;
    
        
    private ObservableList<Funcionario> funcionarioData = FXCollections.observableArrayList();
    
    
    
     public Biometrics() {
         
         
           //  funcionarioData.add(new Funcionario("1","SIM","305689741","Muster Vasckovisk", "Ativo","teste","teste"));
             //funcionarioData.add(new Funcionario("2","SIM","305685648","Victor Doom", "Ativo","teste","teste"));
            
     }

    /**
     * @param args the command line arguments
     */
    //define a classe como a principal do projeto
    public static void main(String[] args) {
        Application.launch(Biometrics.class, (java.lang.String[])null);
    }

    /**
     * Inicializa as configurações básicas do template
     *
    */
    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            stage.setTitle("Biometrics");
            stage.initStyle(StageStyle.UTILITY);
            initRootLayout();
            showLoginOverview();
        } catch (Exception ex) {
            Logger.getLogger(Biometrics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    //Defino o RootLayout como a base
     public void initRootLayout() {
        try {
            // Carrega o root layout do arquivo fxml.
            FXMLLoader loader = new FXMLLoader();
            

            loader.setLocation(Biometrics.class.getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Mostra a scene (cena) contendo oroot layout.
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
     //Renderiza a tela de login
      public void showLoginOverview() {
        try {
            // Carrega o person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Biometrics.class.getResource("login.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            LoginController loginController = loader.getController();
            loginController.setApp(this);
            // Define o person overview dentro do root layout.
            rootLayout.setCenter(personOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
      
    //Retorno o usuário logado  
    public User getLoggedUser() {
        return loggedUser;
    }
    
    // Efetua o login do usuario
    public int userLogging(String userId, String password){
        int tag = Authenticator.validate(userId, password);
        if (tag==1) {
            loggedUser = new User(userId);
            gotoFuncionario();
            return 1;
        } else {
          return  tag;
        }
    }
    //Logout
    void userLogout(){
        loggedUser = null;
        gotoLogin();
    }
    //Avanca para tela de funcionarios
    private void gotoFuncionario() {
        try {
            FuncionarioController profile = (FuncionarioController) replaceSceneContent("funcionario.fxml");
            profile.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Biometrics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//     //Avanca para tela de funcionarios
//    public void gotoImprimir() {
//        try {
//            ImpressaoController profile = (ImpressaoController) replaceSceneContent("impressao.fxml");
//            profile.setApp(this);
//        } catch (Exception ex) {
//            Logger.getLogger(Biometrics.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    //avanca para tela de login 
    private void gotoLogin() {
        try {
            LoginController login = (LoginController) replaceSceneContent("login.fxml");
            login.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Biometrics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Cria as views
    public Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Biometrics.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Biometrics.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        } 
        Scene scene = new Scene(page, 1000, 600);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }
    
     public ObservableList<Funcionario> getFuncionarioData() {
        return funcionarioData;
    }

    public void setFuncionarioData(ObservableList<Funcionario> funcionarioData) {
        this.funcionarioData = funcionarioData;
    }
}
