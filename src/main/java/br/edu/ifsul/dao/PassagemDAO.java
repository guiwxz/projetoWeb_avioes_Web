/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.converters.ConverterOrdem;
import br.edu.ifsul.modelo.Passagem;
import java.io.Serializable;
import javax.ejb.Stateful;

/**
 *
 * @author GUI
 * @param <TIPO>
 */
@Stateful
public class PassagemDAO<TIPO> extends DAOGenerico<Passagem> implements Serializable {
    
    public PassagemDAO(){
        super();
        classePersistence = Passagem.class;
        
        listaOrdem.add(new Ordem("id", "ID", "="));
        listaOrdem.add(new Ordem("nome", "Nome", "like"));
        listaOrdem.add(new Ordem("pessoa.nome", "Pessoa", "like"));
        listaOrdem.add(new Ordem("classe.nome", "Classe", "like"));
        
        ordemAtual = listaOrdem.get(1);
        converterOrdem = new ConverterOrdem();
        converterOrdem.setListaOrdem(listaOrdem);
    }
}
