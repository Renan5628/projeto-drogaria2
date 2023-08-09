package br.com.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.dao.CidadeDAO;
import br.com.dao.ClienteDAO;
import br.com.dao.EstadoDAO;
import br.com.dao.FabricanteDAO;
import br.com.dao.FuncionarioDAO;
import br.com.dao.ItemVendaDAO;
import br.com.dao.PessoaDAO;
import br.com.dao.ProdutoDAO;
import br.com.dao.VendaDAO;
import br.com.domain.Cidade;
import br.com.domain.Cliente;
import br.com.domain.Estado;
import br.com.domain.Fabricante;
import br.com.domain.Funcionario;
import br.com.domain.ItemVenda;
import br.com.domain.Pessoa;
import br.com.domain.Produto;
import br.com.domain.Venda;

public class EstadoDAOTest {

	@Test
	@Ignore
	public void salvarEstado() {
		Estado estado = new Estado();

		estado.setNome("Rio Grande do Sul");
		estado.setSigla("RS");

		EstadoDAO estadoDAO = new EstadoDAO();
		estadoDAO.salvar(estado);

	}

	@Test
	@Ignore
	public void listarEstado() {
		EstadoDAO estadoDAO = new EstadoDAO();
		List<Estado> estados = estadoDAO.listar();

		for (Estado estado : estados) {
			System.out.println(estado.getNome() + " " + estado.getSigla());
		}

	}

	@Test
	@Ignore
	public void buscarEstado() {
		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.buscar(3l);

		System.out.println(estado.getNome());

	}

	@Test
	@Ignore
	public void excluirEstado() {

		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.buscar(3l);

		estadoDAO.excluir(estado);

		System.out.println(estado.getNome());

	}

	@Test
	@Ignore
	public void editarEstado() {

		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.buscar(3l);

		estado.setNome("Minas Gerais");
		estado.setSigla("MG");
		estadoDAO.editar(estado);

		System.out.println(estado.getNome());

	}

	@Test
	@Ignore
	public void salvarFabricante() {
		Fabricante fabricante = new Fabricante();
		FabricanteDAO fabricanteDAO = new FabricanteDAO();

		fabricante.setDescricao("Aché");

		fabricanteDAO.salvar(fabricante);

	}

	@Test
	@Ignore
	public void salvarCidade() throws ParseException {

		ClienteDAO clienteDAO = new ClienteDAO();
		Cliente cliente = new Cliente();
		PessoaDAO pessoaDAO = new PessoaDAO();
		Pessoa pessoa = new Pessoa();

		pessoa = pessoaDAO.buscar(1l);
		cliente.setDataDoCadastro(new SimpleDateFormat("dd/MM/yyyy").parse("29/07/2023"));
		cliente.setLiberado(false);
		cliente.setPessoa(pessoa);

		clienteDAO.merge(cliente);

	}
	
	@Test
	@Ignore
	public void salvarFuncionario() throws ParseException{
		
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		Funcionario funcionario = new Funcionario();
		Pessoa pessoa = new Pessoa();
		PessoaDAO pessoaDAO = new PessoaDAO();
		
		pessoa = pessoaDAO.buscar(2l);
		funcionario.setCarteiraTrabalho("07050806");
		funcionario.setDataAdmissao(new SimpleDateFormat("dd/MM/yyyy").parse("29/05/2023"));
		funcionario.setPessoa(pessoa);
		
		funcionarioDAO.merge(funcionario);
		
		
	}

	@Test
	@Ignore
	public void listarCidade() {
		CidadeDAO cidadeDAO = new CidadeDAO();
		List<Cidade> cidades = cidadeDAO.listar();

		System.out.println(cidades.size());

	}

	@Test
	@Ignore
	public void buscarCidade() {
		Cidade cidade = new CidadeDAO().buscar(1l);
		System.out.println(cidade.getNome());
	}

	@Test
	@Ignore
	public void excluirCidade() {
		Long codigo = 4l;

		CidadeDAO cidadeDAO = new CidadeDAO();
		Cidade cidade = new Cidade();

		cidade = cidadeDAO.buscar(codigo);
		cidadeDAO.excluir(cidade);

	}

	@Test
	@Ignore
	public void editarCidade() {

		Long codigo = 2l;

		CidadeDAO cidadeDAO = new CidadeDAO();
		Cidade cidade = new Cidade();

		cidade = cidadeDAO.buscar(codigo);
		cidade.setNome("Andradas");
		cidade.setEstado(new EstadoDAO().buscar(3l));

		cidadeDAO.editar(cidade);

	}

	@Test
	@Ignore
	public void fabricanteMerge() {
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		Fabricante fabricante = fabricanteDAO.buscar(1l);

		fabricante.setDescricao("Jhonson&Jhonson");

		fabricanteDAO.merge(fabricante);

	}
	
	@SuppressWarnings("unused")
	@Test
	@Ignore
	public void salvarVenda() throws ParseException{
		
		Venda venda = new Venda();
		VendaDAO vendaDAO = new VendaDAO();
		Cliente cliente = new Cliente();
		ClienteDAO clienteDAO = new ClienteDAO();
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		Funcionario funcionario = new Funcionario();
		
		
	    venda.setCliente(clienteDAO.buscar(2l));
	    venda.setCodigo(1l);
	    venda.setFuncionario(funcionarioDAO.buscar(2l));
	    venda.setHorario(new SimpleDateFormat("dd/MM/yyyy").parse("29/05/2023"));
	    venda.setPrecoTotal(new BigDecimal("00.00"));
	    
	    vendaDAO.salvar(venda);
		
		
	}
	
	@SuppressWarnings("unused")
	@Test
	@Ignore
	public void itemVenda(){
		
		ItemVenda itemVenda = new ItemVenda();
		ItemVendaDAO itemVendaDAO = new ItemVendaDAO();
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		Funcionario funcionario = new Funcionario();
		Venda venda = new Venda();
		VendaDAO vendaDAO = new VendaDAO();
		Produto produto = new Produto();
		ProdutoDAO produtoDAO = new ProdutoDAO();
		
		itemVenda.setCodigo(1l);
		itemVenda.setPrecoParcial(new BigDecimal("00.00"));
		itemVenda.setProduto(produtoDAO.buscar(5l));
		itemVenda.setQuantidade((short) '1');
		itemVenda.setVenda(vendaDAO.buscar(1l));
		
		itemVendaDAO.salvar(itemVenda);
		
		
		
	}

}
