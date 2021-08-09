/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author GUI
 */
public class Util {
    
  public static String getMensagemErro(Exception e){
    while(e.getCause() != null){
      e = (Exception) e.getCause();
    }
    String msg = e.getMessage();
    if(msg.contains("violates foreign key constraint")){
        msg = "Registro não pode ser excluído por possuir relações em outras tabelas";
    }
    return msg;
  }
    
    public static void mensagemErro(String mensagem){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, "");
        context.addMessage(null, msg);
    }
    
    public static void mensagemInformacao(String mensagem) {
        FacesContext contexto = FacesContext.getCurrentInstance();
        contexto.getExternalContext().getFlash().setKeepMessages(true);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, "");
        contexto.addMessage(null, msg);               
    }
     
    
}
