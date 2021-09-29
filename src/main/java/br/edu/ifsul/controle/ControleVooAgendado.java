/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.ClasseDAO;
import br.edu.ifsul.dao.PassagemDAO;
import br.edu.ifsul.dao.PessoaDAO;
import br.edu.ifsul.dao.VooAgendadoDAO;
import br.edu.ifsul.dao.VooDAO;
import br.edu.ifsul.modelo.Classe;
import br.edu.ifsul.modelo.Passagem;
import br.edu.ifsul.modelo.Pessoa;
import br.edu.ifsul.modelo.Voo;
import br.edu.ifsul.modelo.VooAgendado;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author GUI
 */
@Named(value="controleVooAgendado")
@ViewScoped
public class ControleVooAgendado implements Serializable {
    @EJB
    private VooAgendadoDAO<VooAgendado> dao;
    private VooAgendado objeto;
    
    @EJB
    protected ClasseDAO<Classe> daoClasse;
    @EJB
    protected PessoaDAO<Pessoa> daoPessoa;
    @EJB
    private VooDAO<Voo> daoVoo;
    
    protected Passagem passagem;
    protected Boolean novaPassagem;
    
    public ControleVooAgendado(){
        
    }
    
    public void novaPassagem(){
        novaPassagem = true;
        passagem = new Passagem();
    }
    
    public void alterarPassagem(int index){
        novaPassagem = false;
        passagem = objeto.getPassagens().get(index);
    }
    
    public void salvarPassagem(){
        if(novaPassagem){
            objeto.setPassagem(passagem);
        }
        Util.mensagemInformacao("Passagem adicionado ou atualizado com sucesso");
    }
    
    public void removerPassagem(int index){
        objeto.removerPassagem(index);
        Util.mensagemInformacao("Passagem removida com sucesso");
    }
    
    public String listar(){
        return "/privado/vooAgendado/listar?faces-redirect=true";
    }
    
    public void novo(){
        objeto = new VooAgendado();
    }
    
    public void alterar(Object id){
        try{
            this.objeto = dao.localizar(id);
        }catch(Exception e){
            Util.mensagemErro("Erro ao recuperar objeto: " + Util.getMensagemErro(e));
        }
    }
    
    public void excluir(Object id){
        try{
            objeto = dao.localizar(id);
            dao.remover(objeto);
            Util.mensagemInformacao("Objeto removido com sucesso");
            
        }catch(Exception e){
            Util.mensagemErro("Erro ao remover objeto: "+Util.getMensagemErro(e));
        }
    }

    public void salvar(){
        try{
            if(objeto.getId() == null){
                dao.persist(objeto);
            } else {
                dao.merge(objeto);
            }
            Util.mensagemInformacao("Objeto persistido com sucesso!");
        
        }catch(Exception e){
            Util.mensagemErro("Erro ao persistir objeto: " + Util.getMensagemErro(e));
        }
    }
    
    public VooAgendadoDAO<VooAgendado> getDao() {
        return dao;
    }

    public void setDao(VooAgendadoDAO<VooAgendado> dao) {
        this.dao = dao;
    }

    public VooAgendado getObjeto() {
        return objeto;
    }

    public void setObjeto(VooAgendado objeto) {
        this.objeto = objeto;
    }

    public Passagem getPassagem() {
        return passagem;
    }

    public void setPassagem(Passagem passagem) {
        this.passagem = passagem;
    }

    public Boolean getNovaPassagem() {
        return novaPassagem;
    }

    public void setNovaPassagem(Boolean novaPassagem) {
        this.novaPassagem = novaPassagem;
    }

    public ClasseDAO<Classe> getDaoClasse() {
        return daoClasse;
    }

    public void setDaoClasse(ClasseDAO<Classe> daoClasse) {
        this.daoClasse = daoClasse;
    }

    public PessoaDAO<Pessoa> getDaoPessoa() {
        return daoPessoa;
    }

    public void setDaoPessoa(PessoaDAO<Pessoa> daoPessoa) {
        this.daoPessoa = daoPessoa;
    }

    public VooDAO<Voo> getDaoVoo() {
        return daoVoo;
    }

    public void setDaoVoo(VooDAO<Voo> daoVoo) {
        this.daoVoo = daoVoo;
    }

    
}
