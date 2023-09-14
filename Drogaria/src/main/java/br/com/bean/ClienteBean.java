package br.com.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.dao.ClienteDAO;
import br.com.dao.PessoaDAO;
import br.com.domain.Cliente;
import br.com.domain.Estado;
import br.com.domain.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable {
	private Cliente cliente;
	private Pessoa pessoa;
	private List<Cliente> clientes;
	private List<Pessoa> pessoas;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}
	
	public Pessoa getPessoa() {
		return pessoa;
	}
	
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	@PostConstruct
	public void listar() {
		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listar("dataDoCadastro");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os clientes");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			cliente = new Cliente();

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("nome");
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar criar um novo cliente");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			clienteDAO.merge(cliente);

			cliente = new Cliente();
			
			clientes = clienteDAO.listar("dataDoCadastro");

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("nome");
			
			Messages.addGlobalInfo("Cliente salvo com sucesso");
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o cliente");
			erro.printStackTrace();
		}
	}
	
	public void excluir(ActionEvent evento){
		try {
				cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");
				
				ClienteDAO clienteDAO = new ClienteDAO();
				clienteDAO.excluir(cliente);
				
				clientes = clienteDAO.listar();
				Messages.addGlobalInfo("Cliente removido com sucesso!");
			
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro durante a exclusão.");
			e.printStackTrace();
		}
		
	}
	
	public void editar(ActionEvent evento){
		
		cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");
		
		pessoa = cliente.getPessoa();
		
		PessoaDAO pessoaDAO = new PessoaDAO();
		pessoas = pessoaDAO.listar("nome");

	}
}