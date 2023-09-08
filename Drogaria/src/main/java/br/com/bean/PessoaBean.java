package br.com.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.omnifaces.util.Messages;

import com.google.gson.Gson;

import br.com.dao.CidadeDAO;
import br.com.dao.EstadoDAO;
import br.com.dao.PessoaDAO;
import br.com.domain.Cidade;
import br.com.domain.Estado;
import br.com.domain.Fabricante;
import br.com.domain.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class PessoaBean implements Serializable {
	private Pessoa pessoa;
	private List<Pessoa> pessoas;
	private Estado estado;
	private List<Estado> estados;
	private List<Cidade> cidades;

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

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@PostConstruct
	public void listar() {
		try {
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as pessoas");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			pessoa = new Pessoa();
			estado = new Estado();
			
			Client cliente = ClientBuilder.newClient();
			WebTarget caminho = cliente.target("http://127.0.0.1:8080/Drogaria/rest/estado");
			String json = caminho.request().get(String.class);
			
			Gson gson = new Gson();
			Estado[] vetor = gson.fromJson(json, Estado[].class);
			
			estados = Arrays.asList(vetor);

			cidades = new ArrayList<Cidade>();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar uma nova pessoa");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
	
		try {
			pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionada");
			
			estado = pessoa.getCidade().getEstado();
			
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar("nome");
			
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidades = cidadeDAO.buscarPorEstado(estado.getCodigo());
			
			

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar uma nova pessoa");
			erro.printStackTrace();
		}

	}

	public void salvar() {
		try {
			
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoaDAO.merge(pessoa);
			pessoas = pessoaDAO.listar("nome");

			pessoa = new Pessoa();
			estado = new Estado();
			
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar("nome");

			cidades = new ArrayList<Cidade>();
			
			Messages.addGlobalInfo("Pessoa salva com sucesso!");
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar uma nova pessoa");
			erro.printStackTrace();
		}

	}

	public void excluir(ActionEvent evento) {

	}

	public void popular() {
		try {

			if (estado != null) {
				
				Client cliente = ClientBuilder.newClient();
				WebTarget caminho = cliente.target("http://127.0.0.1:8080/Drogaria/rest/cidade/{estadoCodigo}").resolveTemplate("estadoCodigo", estado.getCodigo());
				String json = caminho.request().get(String.class);
				
				Gson gson = new Gson();
				Cidade[] vetor = gson.fromJson(json, Cidade[].class);
				
				cidades = Arrays.asList(vetor);
				
			} else {
				cidades = new ArrayList<Cidade>();
			}

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar as cidades.");
			erro.printStackTrace();
		}
	}
}