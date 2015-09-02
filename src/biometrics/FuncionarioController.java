/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometrics;

import biometrics.model.Funcionario;
import biometrics.model.User;
import biometrics.util.URLService;
import biometrics.util.Utility;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author 205406813
 */
public class FuncionarioController extends AnchorPane implements Initializable {
    @FXML
    private AnchorPane conteudo;
    
    private Biometrics application;
    
    @FXML
    private TableView<Funcionario> funcionarioTable;
    @FXML
    private TableColumn<Funcionario, String> viaColumn;
    @FXML
    private TableColumn<Funcionario, String> matriculaColumn;
    @FXML
    private TableColumn<Funcionario, String> nomeColumn;

    @FXML
    private Label via;
    @FXML
    private Label matricula;
    @FXML
    private Label nome;
    @FXML
    private Label situacao;
    @FXML
    private Label postoGraduacao;
    @FXML
    private Label unidade;
    
    @FXML
    TextField txMatricula;
    
    @FXML
    private ComboBox<String> combobox;
    
    @FXML
    private GridPane gridPane;
    
    @FXML
    private Button adicionar;
    
    @FXML
    private Button remover;
    
    @FXML
    private Button imprimir;
    private ObservableList<String> list = FXCollections.observableArrayList("SIM","NÃO");
    
    private IntegerProperty index = new SimpleIntegerProperty();
    
    @FXML
    private Label errorMessage;
    
    @FXML
    private Label validateMessage;
    
    
    
    
    
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        viaColumn.setCellValueFactory(new PropertyValueFactory<Funcionario,String>("via"));
        matriculaColumn.setCellValueFactory(new PropertyValueFactory<Funcionario,String>("matricula"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<Funcionario,String>("nome"));
        combobox.setItems(list);
        combobox.setValue("SIM");
        desabilitaPanelGrid();
        funcionarioTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Funcionario>(){
            @Override
            public void changed(ObservableValue<? extends Funcionario> ov, Funcionario t, Funcionario t1) {
              index.set(application.getFuncionarioData().indexOf(t1));
            //  System.out.println("ok index "+ application.getFuncionarioData().indexOf(t1));
            }
        });
        
        verificaBtnRemocao();
        verificaBtnImprimir();
    }
    
    
    public void setApp(Biometrics application){
      this.application = application;
         
          
            funcionarioTable.setItems(application.getFuncionarioData());
            
        
     }
    
     public void onAddItem(ActionEvent event){
       Funcionario funcionario = new Funcionario();
                 funcionario.setMatricula(matricula.getText());
                 funcionario.setVia(combobox.getValue());
                 funcionario.setNome(nome.getText());
    
         if(application.getFuncionarioData().size()== 4){
            validateMessage.setText("Você já selecionou 4 funcionários.");
         }else if(application.getFuncionarioData().contains(funcionario)){
            validateMessage.setText("Funcionário já foi adicionado.");
         }else{
                 application.getFuncionarioData().add(funcionario);
                 validateMessage.setText(" ");
         }
         verificaBtnRemocao();
         verificaBtnImprimir();
     }
    
    public void onDeleteItem(ActionEvent event){
        validateMessage.setText(" ");
        int i = this.index.get();
        
        funcionarioTable.getSelectionModel().clearSelection();
        
        if(i >-1){
            this.application.getFuncionarioData().remove(i);
        }
    
        verificaBtnRemocao();
        verificaBtnImprimir();
    }
    
    private void verificaBtnRemocao(){
        if(application!=null) {
                if(application.getFuncionarioData().size()== 0){
                        remover.setVisible(false);
                }else{
                    remover.setVisible(true);
                }
        }else{
             remover.setVisible(true);
        }
    }
    
     private void verificaBtnImprimir(){
       if(application!=null) {     
            if(application.getFuncionarioData().size()== 4){//mudar para 4 depois
                    imprimir.setDisable(false);
            }else{
                imprimir.setDisable(true);
            }
            
       }else{
           imprimir.setDisable(false); 
       }
    }
     public void consultaFuncionario(ActionEvent event) {
           validateMessage.setText(" ");
           if(Utility.isNotNull(txMatricula.getText())){
         
      
       // imprimir.setDisable(false);
        
         Client client = Client.create();

                        WebResource webResource = client
                           .resource(URLService.LOCALHOST+"/biometricsws/service/funcionario/get");

                        ClientResponse response = webResource.accept("application/json")
                           .get(ClientResponse.class);

                        if (response.getStatus() != 200) {
                           throw new RuntimeException("Failed : HTTP error code : "
                                + response.getStatus());
                        }
                    
                      
                         MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
                         queryParams.add("matricula", txMatricula.getText());
                         String s = webResource.queryParams(queryParams).get(String.class);
                             
                        
               try {
                   //
                   //queryParams.add("password", password);
                   JSONObject obj = new JSONObject(s);
                   
                  if(obj.isNull("matricula")){
                      errorMessage.setText("Nenhum Funcionário encontrado.");
                      desabilitaPanelGrid();
                  }else{
                       habilitaPanelGrid();
                       via.setText((String) obj.get("via")+"ª via");
                       matricula.setText((String) obj.get("matricula"));
                       nome.setText((String) obj.get("nome"));
                       situacao.setText((String) obj.get("situacao"));
                       postoGraduacao.setText((String) obj.get("postoGraduacao"));
                       unidade.setText((String) obj.get("unidade"));
                       errorMessage.setText(" ");
                  }
                  
               } catch (JSONException ex) {
                   Logger.getLogger(FuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
               }
           }else{
                desabilitaPanelGrid();
                errorMessage.setText("Informe a matrícula do funcionário.");
           }
                      // 
     }
    
    private void desabilitaPanelGrid(){
        gridPane.setVisible(false);
        adicionar.setVisible(false);
        remover.setVisible(false);
        imprimir.setDisable(true);
        
         
    }
    
    private void habilitaPanelGrid(){
        gridPane.setVisible(true);
        adicionar.setVisible(true);
       
     }
     public void gotoImpressao(ActionEvent event) {
      try {
          
            ImpressaoController profile = (ImpressaoController) application.replaceSceneContent("impressora.fxml");
            profile.setApp(application);
        } catch (Exception ex) {
            Logger.getLogger(Biometrics.class.getName()).log(Level.SEVERE, null, ex);
        }

     }

    
    
    public AnchorPane getConteudo() {
        return conteudo;
    }

    public void setConteudo(AnchorPane conteudo) {
        this.conteudo = conteudo;
    }

    public Biometrics getApplication() {
        return application;
    }

    public void setApplication(Biometrics application) {
        this.application = application;
    }

    public TableView<Funcionario> getFuncionarioTable() {
        return funcionarioTable;
    }

    public void setFuncionarioTable(TableView<Funcionario> funcionarioTable) {
        this.funcionarioTable = funcionarioTable;
    }

    public TableColumn<Funcionario, String> getViaColumn() {
        return viaColumn;
    }

    public void setViaColumn(TableColumn<Funcionario, String> viaColumn) {
        this.viaColumn = viaColumn;
    }

    public TableColumn<Funcionario, String> getMatriculaColumn() {
        return matriculaColumn;
    }

    public void setMatriculaColumn(TableColumn<Funcionario, String> matriculaColumn) {
        this.matriculaColumn = matriculaColumn;
    }

    public TableColumn<Funcionario, String> getNomeColumn() {
        return nomeColumn;
    }

    public void setNomeColumn(TableColumn<Funcionario, String> nomeColumn) {
        this.nomeColumn = nomeColumn;
    }

   

    public Label getVia() {
        return via;
    }

    public void setVia(Label via) {
        this.via = via;
    }

    public Label getMatricula() {
        return matricula;
    }

    public void setMatricula(Label matricula) {
        this.matricula = matricula;
    }

    public Label getNome() {
        return nome;
    }

    public void setNome(Label nome) {
        this.nome = nome;
    }

    public Label getSituacao() {
        return situacao;
    }

    public void setSituacao(Label situacao) {
        this.situacao = situacao;
    }

    public Label getUnidade() {
        return unidade;
    }

    public void setUnidade(Label unidade) {
        this.unidade = unidade;
    }


}
