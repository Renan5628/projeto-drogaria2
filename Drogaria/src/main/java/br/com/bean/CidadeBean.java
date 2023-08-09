package br.com.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.dao.CidadeDAO;
import br.com.dao.EstadoDAO;
import br.com.domain.Cidade;
import br.com.domain.Estado;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CidadeBean implements Serializable {
	
	private Cidade cidade;
	private List<Cidade> cidades;
	private List<Estado> estados;

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}
	
	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	public List<Estado> getEstados() {
		return estados;
	}
	
	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}
	

	@PostConstruct
	public void listar(){
		try {
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidades = cidadeDAO.listar();
			
		} catch (Exception e) {
			e.printStackTrace();
			Messages.addGlobalError("Ocorreu um erro na listagem.");
		}
	}
	
	public void excluir(ActionEvent evento){
		try {
			cidade = (Cidade) evento.getComponent().getAttributes().get("cidadeSelecionada");
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidadeDAO.excluir(cidade);
			cidades = cidadeDAO.listar();
			
			Messages.addGlobalInfo("Cidade removida com sucesso!");
		
	} catch (RuntimeException e) {
		Messages.addGlobalError("Ocorreu um erro durante a exclusão.");
		e.printStackTrace();
	}
		
	}
	
	public void salvar(){
		try {
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidadeDAO.merge(cidade);
			
			cidade = new Cidade();
			
			cidades = cidadeDAO.listar();
			
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar();
			
			Messages.addGlobalInfo("Cidade salva com sucesso!");

		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao salvar a cidade.");
			e.printStackTrace();
		}
	}
	
	public void novo(){
	
		try{
			cidade = new Cidade();
			
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar("nome");
			
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao gerar uma nova cidade.");
			e.printStackTrace();
		}
		}
	
	public void editar(ActionEvent evento){
		
		try{
			cidade = (Cidade) evento.getComponent().getAttributes().get("cidadeSelecionada");
			
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar();
			
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar selecionar uma cidade.");
			e.printStackTrace();
		}
		
		
		
		
	}

	
	
}
