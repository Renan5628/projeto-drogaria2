package br.com.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.dao.EstadoDAO;
import br.com.domain.Estado;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EstadoBean implements Serializable  {
	
	private Estado estado;
	private List<Estado> estados;
	private EstadoDAO estadoDAO = new EstadoDAO();
	
	public void salvar(){
		
		try {
			
			estadoDAO.merge(estado);
			novo();
			estados = estadoDAO.listar();
			Messages.addGlobalInfo("Estado salvo com sucesso!");
			
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreru um erro ao tentar salvar o estado.");
			e.printStackTrace();
		}
		
	}
	
	@PostConstruct
	public void listar(){
		
		try {
			estados = estadoDAO.listar();   
			
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreru um erro ao tentar listar os estados.");
		}
		
		
	}
	
	public void excluir(ActionEvent evento){
		try {
				estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");
				estadoDAO.excluir(estado);
				estados = estadoDAO.listar();
				Messages.addGlobalInfo("Estado removido com sucesso!");
			
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro durante a exclusão.");
			e.printStackTrace();
		}
		
	}
	
	public void editar(ActionEvent evento){
		
		estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");

	}
	
	public Estado getEstado() {
		return estado;
	}
	
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public void novo(){
		estado = new Estado();
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}
	
	

}
