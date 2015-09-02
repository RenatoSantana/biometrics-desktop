package biometrics.model;


import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 *
 * @author 205406813
 */
public class Funcionario {
    private final StringProperty id;
    private final StringProperty via;
    private final StringProperty matricula;
    private final StringProperty nome;
    private final StringProperty situacao;
    private final StringProperty grauHierarquico;
    private final StringProperty unidade;
    
    
     public Funcionario() {
        this(null,null, null,null, null,null, null);
    }
     
    public Funcionario(String id, String via, String matricula,String nome, String situacao, String grauHierarquico,String unidade) {
              this.id =  new SimpleStringProperty(id);
              this.via = new SimpleStringProperty(via);
              this.matricula = new SimpleStringProperty(matricula);
              this.nome = new SimpleStringProperty(nome);
              this.situacao = new SimpleStringProperty(situacao);
              this.grauHierarquico = new SimpleStringProperty(grauHierarquico);
              this.unidade = new SimpleStringProperty(unidade);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }
    public String getVia() {
        return via.get();
    }
    
     public void setVia(String via) {
         this.via.set(via);
    }

    public String getMatricula() {
        return matricula.get();
    }
    
    public void setMatricula(String matricula) {
        this.matricula.set(matricula);
    }

    public String getNome() {
        return nome.get();
    }
    public void setNome(String nome) {
        this.nome.set(nome);
    }
    public String getSituacao() {
        return situacao.get();
    }
    
    public void setSituacao(String situacao) {
        this.situacao.set(situacao);
    }


    public StringProperty getGrauHierarquico() {
        return grauHierarquico;
    }
    
    public void setGrauHierarquico(String grauHierarquico) {
        this.grauHierarquico.set(grauHierarquico);
    }

    public StringProperty getUnidade() {
        return unidade;
    }
    
    public void setUnidade(String unidade) {
        this.unidade.set(unidade);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.matricula);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Funcionario other = (Funcionario) obj;
        if (!Objects.equals(this.matricula.get(), other.matricula.get())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Funcionario{" + "matricula=" + matricula + '}';
    }


}
