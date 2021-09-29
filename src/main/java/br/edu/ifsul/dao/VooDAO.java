/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.converters.ConverterOrdem;
import br.edu.ifsul.modelo.Voo;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateful;

/**
 *
 * @author GUI
 * @param <TIPO>
 */
@Stateful
public class VooDAO<TIPO> extends DAOGenerico<Voo> implements Serializable {
    
    public VooDAO(){
        super();
        classePersistence = Voo.class;
        
        listaOrdem.add(new Ordem("id", "ID", "="));
        listaOrdem.add(new Ordem("descricao", "Descrição", "like"));
        
        ordemAtual = listaOrdem.get(1);
        converterOrdem = new ConverterOrdem();
        converterOrdem.setListaOrdem(listaOrdem);
    }
    
    @Override
    public Voo localizar(Object id) throws Exception {
        Voo obj = em.find(Voo.class, id);
        // uso para evitar o erro de lazy inicialization exception
        obj.getVoosAgendados().size();
        obj.getEscalas().size();
        return obj;
    }
    
    public List<Voo> getListaCompleta(){
        String query = "select distinct v from Voo v join fetch v.voosAgendados a order by a.id";
        return em.createQuery(query).getResultList();
    }
}
