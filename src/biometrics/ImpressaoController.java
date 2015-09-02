/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometrics;

import biometrics.model.User;
import biometrics.report.Print;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javax.print.PrintService;

/**
 *
 * @author 205406813
 */
public class ImpressaoController extends AnchorPane implements Initializable {
  
   @FXML
    private Button voltar;
    
    private Biometrics application;
    
    @FXML
    private AnchorPane conteudo;
    
  
    
    @FXML
    private ComboBox<PrintService> combobox;
     
    private ObservableList<PrintService> list;
    private PrintService[] printService;
    
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
         this.printService = PrinterJob.lookupPrintServices();
        
         list = FXCollections.observableArrayList(printService);
         combobox.setItems(list);
    }
    
    
  
    public void setApp(Biometrics application){
      this.application = application;
            
        
     }
    
    private String verificaVia(String via){
        if(via.equals("SIM")){
            return "S";
        }else{
            return "N";
        }
    }
    
     public void imprimir(ActionEvent event) {
         if(application!=null && application.getFuncionarioData().size()== 4){
        HashMap<String, Object> parameters = new HashMap<String, Object>();
           User loggedUser = application.getLoggedUser();
         parameters.put("matricula_session", loggedUser.getMatricula());
         parameters.put("matricula1", application.getFuncionarioData().get(0).getMatricula());
         parameters.put("matricula2", application.getFuncionarioData().get(1).getMatricula());
         parameters.put("matricula3", application.getFuncionarioData().get(2).getMatricula());
         parameters.put("matricula4", application.getFuncionarioData().get(3).getMatricula());
         parameters.put("codigo1", "g43kzjrj");
         parameters.put("codigo2", "g43kzjrj");
         parameters.put("codigo3", "g43kzjrj");
         parameters.put("codigo4", "g43kzjrj");
         parameters.put("tag1",verificaVia(application.getFuncionarioData().get(0).getVia()));
         parameters.put("tag2",verificaVia(application.getFuncionarioData().get(1).getVia()));
         parameters.put("tag3",verificaVia(application.getFuncionarioData().get(2).getVia()));
         parameters.put("tag4",verificaVia(application.getFuncionarioData().get(3).getVia()));
         
  
         
             try {
                 Print.gerarVia(parameters,combobox.getValue().getName());
             } catch (MalformedURLException ex) {
                 Logger.getLogger(ImpressaoController.class.getName()).log(Level.SEVERE, null, ex);
             } catch (URISyntaxException ex) {
                 Logger.getLogger(ImpressaoController.class.getName()).log(Level.SEVERE, null, ex);
             } catch (ClassNotFoundException ex) {
                 Logger.getLogger(ImpressaoController.class.getName()).log(Level.SEVERE, null, ex);
             }
         
         }
       
     }
    
     public void gotoFuncionario(ActionEvent event) {
       try {
          
            FuncionarioController profile = (FuncionarioController) application.replaceSceneContent("funcionario.fxml");
            profile.setApp(application);
        } catch (Exception ex) {
            Logger.getLogger(Biometrics.class.getName()).log(Level.SEVERE, null, ex);
        }


     }
}
