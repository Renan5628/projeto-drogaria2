package br.com.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.dao.ClienteDAO;
import br.com.dao.FuncionarioDAO;
import br.com.dao.PessoaDAO;
import br.com.domain.Cliente;
import br.com.domain.Funcionario;
import br.com.domain.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FuncionarioBean implements Serializable{
	
	private Funcionario funcionario;
	private List<Funcionario> funcionarios;
	private Pessoa pessoa;
	private List<Pessoa> pessoas;
	
	
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}
	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
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
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listar();
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os funcionários.");
			erro.printStackTrace();
		}
	}
	
	
	public void novo() {
		funcionario = new Funcionario();

		PessoaDAO pessoaDAO = new PessoaDAO();
		pessoas = pessoaDAO.listar("nome");
	}
	
	public void salvar() {
		try {
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarioDAO.salvar(funcionario);

			funcionario = new Funcionario();
			
			funcionarios = funcionarioDAO.listar();

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("nome");
			
			Messages.addGlobalInfo("Funcionário salvo com sucesso");
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o funcionário");
			erro.printStackTrace();
		}
	}
	
	
	public void excluir(ActionEvent evento){
		try {
				funcionario = (Funcionario) evento.getComponent().getAttributes().get("funcionarioSelecionado");
				
				FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
				funcionarioDAO.excluir(funcionario);
				
				funcionarios = funcionarioDAO.listar();
				Messages.addGlobalInfo("Funcionario removido com sucesso!");
			
		} catch (RuntimeException e) {
			Messages.addGlobalError("Ocorreu um erro durante a exclusão.");
			e.printStackTrace();
		}
		
	}
	
	
	public void editar(ActionEvent evento){
		
		funcionario = (Funcionario) evento.getComponent().getAttributes().get("funcionarioSelecionado");
		
		pessoa = funcionario.getPessoa();
		
		PessoaDAO pessoaDAO = new PessoaDAO();
		pessoas = pessoaDAO.listar("nome");

	}

}
