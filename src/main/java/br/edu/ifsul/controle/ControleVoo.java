/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.AeroportoDAO;
import br.edu.ifsul.dao.VooDAO;
import br.edu.ifsul.modelo.Aeroporto;
import br.edu.ifsul.modelo.Voo;
import br.edu.ifsul.modelo.VooAgendado;
import br.edu.ifsul.util.Util;
import br.edu.ifsul.util.UtilRelatorios;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author GUI
 */
@Named(value="controleVoo")
@ViewScoped
public class ControleVoo implements Serializable {
    @EJB
    private VooDAO<Voo> dao;
    private Voo objeto;
    private Boolean novo;
    @EJB
    protected AeroportoDAO<Aeroporto> daoAeroporto;
    protected Aeroporto aeroporto;
    
    protected VooAgendado vooAgendado;
    protected Boolean novoVooAgendado;
    private int abaAtiva;
    
    public ControleVoo(){
        
    }
    
    public void imprimirVoos(){
        HashMap parametros = new HashMap();
        UtilRelatorios.imprimeRelatorio("RelatorioVoos", parametros, dao.getListaCompleta());
    }
    
    public void imprimirVoo(Object id){
        try {
            
            objeto = dao.localizar(id);
            
            if (objeto == null) {
                System.out.println("aAAAAAAAAAAAAAAAAAAAAAAA CHOOOOOOOO");
            } else {
                List<Voo> lista = new ArrayList<>();
                lista.add(objeto);
                HashMap parametros = new HashMap();
                UtilRelatorios.imprimeRelatorio("RelatorioVoos", parametros, lista);
            }
            
        } catch (Exception e) {
            Util.mensagemErro("Erro ao imprimir relatório: " + Util.getMensagemErro(e));
        }
        
    }
    
    public void removerAeroporto(Aeroporto obj) {
        objeto.getEscalas().remove(obj);
        Util.mensagemInformacao("Aeroporto removida com sucesso!");
    }

    public void adicionarAeroporto() {
        System.out.println("AAAAAAAAAA: " + aeroporto.getNome());
        
        if (!objeto.getEscalas().contains(aeroporto)) {
            if (aeroporto != null) {
                objeto.getEscalas().add(aeroporto);
                Util.mensagemInformacao("Aeroporto adicionado com sucesso!");
            } else {
                Util.mensagemErro("Selecione o aeroporto");
            }
        } else {
            Util.mensagemErro("Esse aeroporto já foi especificado");
        }
    }
    
    public void novoVooAgendado(){
        novoVooAgendado = true;
        vooAgendado = new VooAgendado();
    }
    
    public void alterarVooAgendado(int index){
        novoVooAgendado = false;
        vooAgendado = objeto.getVoosAgendados().get(index);
    }
    
    public void salvarVooAgendado(){
        if(novoVooAgendado){
            objeto.setVooAgendado(vooAgendado);
        }
        Util.mensagemInformacao("Passagem adicionado ou atualizado com sucesso");
    }
    
    public void removerVooAgendado(int index){
        objeto.removerVooAgendado(index);
        Util.mensagemInformacao("Passagem removida com sucesso");
    }
    
    public String listar(){
        return "/privado/voo/listar?faces-redirect=true";
    }
    
    public void novo(){
        objeto = new Voo();
        novo = true;
        abaAtiva = 0;
    }
    
    public void alterar(Object id){
        try{
            objeto = dao.localizar(id);
            novo = false;
            abaAtiva = 0;
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
            if(novo){
                dao.persist(objeto);
            } else {
                dao.merge(objeto);
            }
            Util.mensagemInformacao("Objeto persistido com sucesso!");
        
        }catch(Exception e){
            Util.mensagemErro("Erro ao persistir objeto: " + Util.getMensagemErro(e));
        }
    }
    
    public VooDAO<Voo> getDao() {
        return dao;
    }

    public void setDao(VooDAO<Voo> dao) {
        this.dao = dao;
    }

    public Voo getObjeto() {
        return objeto;
    }

    public void setObjeto(Voo objeto) {
        this.objeto = objeto;
    }

    public VooAgendado getVooAgendado() {
        return vooAgendado;
    }

    public void setVooAgendado(VooAgendado vooAgendado) {
        this.vooAgendado = vooAgendado;
    }

    public Boolean getNovoVooAgendado() {
        return novoVooAgendado;
    }

    public void setNovoVooAgendado(Boolean novoVooAgendado) {
        this.novoVooAgendado = novoVooAgendado;
    }

    public AeroportoDAO<Aeroporto> getDaoAeroporto() {
        return daoAeroporto;
    }

    public void setDaoAeroporto(AeroportoDAO<Aeroporto> daoAeroporto) {
        this.daoAeroporto = daoAeroporto;
    }

    public Aeroporto getAeroporto() {
        return aeroporto;
    }

    public void setAeroporto(Aeroporto aeroporto) {
        this.aeroporto = aeroporto;
    }

    public Boolean getNovo() {
        return novo;
    }

    public void setNovo(Boolean novo) {
        this.novo = novo;
    }

    public int getAbaAtiva() {
        return abaAtiva;
    }

    public void setAbaAtiva(int abaAtiva) {
        this.abaAtiva = abaAtiva;
    }
}
