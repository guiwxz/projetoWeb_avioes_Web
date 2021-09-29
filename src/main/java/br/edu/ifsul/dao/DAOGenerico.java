/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.converters.ConverterOrdem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author GUI
 * @param <TIPO>
 */
public class DAOGenerico<TIPO> implements Serializable {

    public ConverterOrdem getConverterOrdem() {
        return converterOrdem;
    }

    public void setConverterOrdem(ConverterOrdem converterOrdem) {
        this.converterOrdem = converterOrdem;
    }
    
    // consulta que sera paginada
    private List<TIPO> listaObjetos;
    private List<TIPO> listaTodos;
    
    @PersistenceContext(unitName="ProjetoWeb_avioes_WEB")
    protected EntityManager em;
    
    protected Class classePersistence;
    
    protected String filtro = "";
    protected List<Ordem> listaOrdem = new ArrayList();
    protected Ordem ordemAtual;
    protected ConverterOrdem converterOrdem;
    protected Integer maximoObjetos = 5;
    protected Integer posicaoAtual = 0;
    protected Integer totalObjetos = 0;
    
    public DAOGenerico(){
        
    }

    public List<TIPO> getListaObjetos() {
        String query = "from "+classePersistence.getSimpleName();
        String where = "";
        
        //removendo caracateres nocivos para tratar injecao de SQL
        filtro = filtro.replaceAll("[';-]", "");
        if (filtro.length() > 0) {
            switch(ordemAtual.getOperator()){
                case "=":
                    if(ordemAtual.getAtributo().equals("id")){
                        try{
                            Integer.parseInt(filtro);
                        }catch(Exception e){
                            filtro = "0";
                        }
                    }
                    where += " where " + ordemAtual.getAtributo() + " = '" + filtro + "' ";
                    break;
                    
                case "like":
                    where += " where upper("+ordemAtual.getAtributo() + ") like '" + filtro.toUpperCase() + "%' ";
            }
        }
        
        query += where;
        query += " order by " + ordemAtual.getAtributo();
        System.out.println("QUery"+ query);
        totalObjetos = em.createQuery(query).getResultList().size();
        
        return em.createQuery(query).setFirstResult(posicaoAtual).setMaxResults(maximoObjetos).getResultList();
    }
    
    public void primeiro(){
        posicaoAtual = 0;
    }
    
    public void anterior(){
        posicaoAtual -= maximoObjetos;
        if (posicaoAtual < 0) {
            posicaoAtual = 0;
        }
    }
    
    public void proximo(){
        if (posicaoAtual + maximoObjetos < totalObjetos){
            posicaoAtual += maximoObjetos;
        }
    }
    
    public void ultimo(){
        int resto = totalObjetos % maximoObjetos;
        if (resto > 0) {
            posicaoAtual = totalObjetos - resto;
        } else {
            posicaoAtual = totalObjetos - maximoObjetos;
        }
    }

    public String getMensagemNavegacao() {
        int ate = posicaoAtual + maximoObjetos;
        if (ate > totalObjetos) {
            ate = totalObjetos;
        }
        if (totalObjetos > 0) {
            return "Listando de " + (posicaoAtual+1) + " até " + ate + " de " + totalObjetos + " registros";
        } else {
            return "Nenhum registro encontrado...";
        }
    }

    public void setListaObjetos(List<TIPO> listaObjetos) {
        this.listaObjetos = listaObjetos;
    }

    public List<TIPO> getListaTodos() {//AAAAAAAAAAAAA
        String query = " from "+classePersistence.getSimpleName() + " order by " + ordemAtual.getAtributo();
        
        return em.createQuery(query).getResultList();
    }

    public void setListaTodos(List<TIPO> listaTodos) {
        this.listaTodos = listaTodos;
    }
    
    public void persist(TIPO obj) throws Exception {
        em.persist(obj);
    }
    public void merge(TIPO obj) throws Exception {
        em.merge(obj);
    }
    
    @RolesAllowed("ADMINISTRADOR")
    public void remover(TIPO obj) throws Exception {
        obj = em.merge(obj);
        em.remove(obj);
    }
    public TIPO localizar(Object id) throws Exception {
        return (TIPO) em.find(classePersistence, id);
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Class getClassePersistence() {
        return classePersistence;
    }

    public void setClassePersistence(Class classePersistence) {
        this.classePersistence = classePersistence;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public List<Ordem> getListaOrdem() {
        return listaOrdem;
    }

    public void setListaOrdem(List<Ordem> listaOrdem) {
        this.listaOrdem = listaOrdem;
    }

    public Ordem getOrdemAtual() {
        return ordemAtual;
    }

    public void setOrdemAtual(Ordem ordemAtual) {
        this.ordemAtual = ordemAtual;
    }

    public Integer getMaximoObjetos() {
        return maximoObjetos;
    }

    public void setMaximoObjetos(Integer maximoObjetos) {
        this.maximoObjetos = maximoObjetos;
    }

    public Integer getPosicaoAtual() {
        return posicaoAtual;
    }

    public void setPosicaoAtual(Integer posicaoAtual) {
        this.posicaoAtual = posicaoAtual;
    }

    public Integer getTotalObjetos() {
        return totalObjetos;
    }

    public void setTotalObjetos(Integer totalObjetos) {
        this.totalObjetos = totalObjetos;
    }
    
}
