/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.converters.ConverterOrdem;
import br.edu.ifsul.modelo.VooAgendado;
import java.io.Serializable;
import javax.ejb.Stateful;

/**
 *
 * @author GUI
 * @param <TIPO>
 */
@Stateful
public class VooAgendadoDAO<TIPO> extends DAOGenerico<VooAgendado> implements Serializable {
    
    public VooAgendadoDAO(){
        super();
        classePersistence = VooAgendado.class;
        
        listaOrdem.add(new Ordem("id", "ID", "="));
        listaOrdem.add(new Ordem("aeronave", "Aeronave", "like"));
        
        ordemAtual = listaOrdem.get(1);
        converterOrdem = new ConverterOrdem();
        converterOrdem.setListaOrdem(listaOrdem);
    }
    
    @Override
    public VooAgendado localizar(Object id) throws Exception {
        VooAgendado objeto = em.find(VooAgendado.class, id);
        //Deve-se inicializar a coleção ou coleçoes do objeto para não
        // dar erro de lazy inicialization exception
        objeto.getPassagens().size();
        return objeto;
    }
}
