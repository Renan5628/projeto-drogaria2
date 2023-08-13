package br.com.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.dao.ProdutoDAO;
import br.com.domain.Produto;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class HistoricoBean implements Serializable {
	
	private Produto produto;
	private Boolean exibir;
	
	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	@PostConstruct
	public void novo() {
		produto = new Produto();
		exibir = false;
	}
	
	public Boolean getExibir() {
		return exibir;
	}
	public void setExibir(Boolean exibir) {
		this.exibir = exibir;
	}
	
	public void buscar() {
		try {
			
			ProdutoDAO dao = new ProdutoDAO();
			Produto resultado = dao.buscar(produto.getCodigo());
			
			if (resultado == null) {
				Messages.addGlobalWarn("Não existe o produto cadastrado para o código informado.");
				exibir = false;
			}else {
				exibir = true;
				produto = resultado;
			}
			
			
		} catch (Exception e) {
			Messages.addGlobalError("Erro ao buscar o produto pelo código.");
			e.printStackTrace();
		}
	}
	
}
