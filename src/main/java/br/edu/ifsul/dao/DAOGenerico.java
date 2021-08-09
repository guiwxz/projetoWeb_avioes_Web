/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author GUI
 * @param <TIPO>
 */
public class DAOGenerico<TIPO> implements Serializable {
    
    // consulta que sera paginada
    private List<TIPO> listaObjetos;
    private List<TIPO> listaTodos;
    
    @PersistenceContext(unitName="ProjetoWeb_avioes_WEB")
    protected EntityManager em;
    
    protected Class classePersistence;
    
    public DAOGenerico(){
        
    }

    public List<TIPO> getListaObjetos() {
        String query = "from "+classePersistence.getSimpleName();
        return em.createQuery(query).getResultList();
    }

    public void setListaObjetos(List<TIPO> listaObjetos) {
        this.listaObjetos = listaObjetos;
    }

    public List<TIPO> getListaTodos() {
        String query = "from "+classePersistence.getSimpleName();
        
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
    
}
