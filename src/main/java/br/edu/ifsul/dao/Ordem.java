/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import java.io.Serializable;

/**
 *
 * @author GUI
 */
public class Ordem implements Serializable {
    protected String atributo;
    protected String label;
    protected String operator;

    public Ordem(String atributo, String label, String operator) {
        this.atributo = atributo;
        this.label = label;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return label;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
    
    
}
