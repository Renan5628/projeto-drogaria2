package br.com.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.dao.ClienteDAO;
import br.com.dao.FuncionarioDAO;
import br.com.dao.ProdutoDAO;
import br.com.dao.VendaDAO;
import br.com.domain.Cliente;
import br.com.domain.Funcionario;
import br.com.domain.ItemVenda;
import br.com.domain.Produto;
import br.com.domain.Venda;


@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class VendaBean implements Serializable {
	
	private List<Produto> produtos;
	private List<ItemVenda> itensVenda;
	private List<Cliente> clientes;
	private List<Funcionario> funcionarios;
	private Funcionario funcionario;
	private Venda venda;
	
	
	
	public List<ItemVenda> getItensVenda() {
		return itensVenda;
	}
	
	public void setItensVenda(List<ItemVenda> itensVenda) {
		this.itensVenda = itensVenda;
	}
	
	
	public List<Produto> getProdutos() {
		return produtos;
	}
	
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	public Venda getVenda() {
		return venda;
	}
	
	public void setVenda(Venda venda) {
		this.venda = venda;
	}
	
	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}
	
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	
	@PostConstruct
	public void novo() {
		try {
			
			venda = new Venda();
			venda.setPrecoTotal(new BigDecimal("00.00") );
			
			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listar("descricao");
			
			itensVenda = new ArrayList<>();
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar carregar a tela de vendas.");
			erro.printStackTrace();
		}
	}
	
	public void adicionar(ActionEvent event){
		
		Produto produto = (Produto) event.getComponent().getAttributes().get("produtoSelecionado");
		
		int exist = -1;
		
		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
			if(itensVenda.get(posicao).getProduto().equals(produto)){
				exist = posicao;
			}
		}
		
		if(exist < 0){
			ItemVenda itemVenda = new ItemVenda();
			itemVenda.setPrecoParcial(produto.getPreco());
			itemVenda.setProduto(produto);
			itemVenda.setQuantidade(new Short("1"));
			
			itensVenda.add(itemVenda);
			
		}else{
			ItemVenda itemVenda = itensVenda.get(exist);
			itemVenda.setQuantidade(new Short(itemVenda.getQuantidade() + 1 + ""));
			itemVenda.setPrecoParcial(produto.getPreco().multiply(new BigDecimal(itemVenda.getQuantidade())));
			
		}
		
		calcular();
		
		
	}
	
	public void remover(ActionEvent event){
		ItemVenda itemVenda = (ItemVenda) event.getComponent().getAttributes().get("itemSelecionado");
		
		int exist = -1;
		
		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
			if(itensVenda.get(posicao).getProduto().equals(itemVenda.getProduto())){
				exist = posicao;
			}
		}
		if(exist > -1){
			itensVenda.remove(exist);
		}
		
		calcular();
		
	}
	
	public void calcular(){
		venda.setPrecoTotal(new BigDecimal("0.00"));
		
		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
			ItemVenda itemVenda = itensVenda.get(posicao);
			venda.setPrecoTotal(venda.getPrecoTotal().add(itemVenda.getPrecoParcial()));
		}
		
	}
	
	public void finalizar(){
		try {
			
			venda.setHorario(new Date());
			venda.setFuncionario(null); 
			venda.setCliente(null);
			
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listarOrdenado();
			
			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listarOrdenado();
			
			
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro ao finalizar a venda.");
			e.printStackTrace();
		}
	}
	
	public void salvar(){
	
		try {
			if (venda.getPrecoTotal().signum() == 0) {
				Messages.addGlobalError("Informe pelo menos um item para venda. ");
				return;
			}
			
			VendaDAO vendaDAO = new VendaDAO();
			vendaDAO.salvar(venda, itensVenda);
			
			novo();
			
			Messages.addGlobalInfo("Venda realizada com sucesso!");
			
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a venda.");
			e.printStackTrace();
		}
		
	}
	

}
