package br.com.bean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.dao.FabricanteDAO;
import br.com.dao.ProdutoDAO;
import br.com.domain.Fabricante;
import br.com.domain.Produto;
import br.com.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ProdutoBean implements Serializable {
	private Produto  produto;
	private List<Produto> produtos;
	private List<Fabricante> fabricantes;
	private StreamedContent foto;
	
	public StreamedContent getFoto() {
		return foto;
	}
	
	public void setFoto(StreamedContent foto) {
		this.foto = foto;
	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(List<Fabricante> fabricantes) {
		this.fabricantes = fabricantes;
	}


	@PostConstruct
	public void listar() {
		try {
			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listar("descricao");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os produtos");
			erro.printStackTrace();
		}
	}
	
	public void novo() {
		try {
			produto = new Produto();

			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricantes = fabricanteDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar gerar um novo produto");
			erro.printStackTrace();
		}
	}
	
	public void editar(ActionEvent evento){
		try {
			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricantes = fabricanteDAO.listar();
			
			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");
			produto.setCaminho("C:/ProgramaçãoWeb/Uploads/" + produto.getCodigo() + ".png");
			
			
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar um produto");
			erro.printStackTrace();
		}	
	}
	
	public void salvar() {
		try {
			
			if(produto.getCaminho() == null){
				Messages.addGlobalError("O campo foto é obrigatório.");
				return;
			}
			
			ProdutoDAO produtoDAO = new ProdutoDAO();
			Produto produtoRetorno = produtoDAO.merge(produto);
			
			Path origem = Paths.get(produto.getCaminho());
			Path destino = Paths.get("C:/ProgramaçãoWeb/Uploads/" + produtoRetorno.getCodigo() + ".png" );
			
			Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
			produto = new Produto();

			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricantes = fabricanteDAO.listar();

			produtos = produtoDAO.listar();

			Messages.addGlobalInfo("Produto salvo com sucesso");
			
		} catch (RuntimeException | IOException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar o produto");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtoDAO.excluir(produto);
			
			Path arquivo = Paths.get("C:/ProgramaçãoWeb/Uploads/" + produto.getCodigo() + ".png" );
			
			Files.deleteIfExists(arquivo);

			produtos = produtoDAO.listar();
			Messages.addGlobalInfo("Produto removido com sucesso");
			
			
		} catch (RuntimeException | IOException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o produto");
			erro.printStackTrace();
		}
	}
	
	public void upload(FileUploadEvent event){
		try {
			
			UploadedFile arquivoUpload = event.getFile();
			Path arquivoTemp = Files.createTempFile(null, null);
			Files.copy(arquivoUpload.getInputstream(), arquivoTemp, StandardCopyOption.REPLACE_EXISTING);
			produto.setCaminho(arquivoTemp.toString());
			
			Messages.addGlobalInfo("Upload armazenado!");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void imprimir() throws FileNotFoundException {
		
		try {
			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formListagem:tabela");
			Map<String, Object> filtros = tabela.getFilters();

			String proDescricao = (String) filtros.get("descricao");
			String fabDescricao = (String) filtros.get("fabricante.descricao");
			
			FileInputStream inputStream = new FileInputStream(Faces.getRealPath("/reports/produtos.jrxml"));
			JasperReport caminho = JasperCompileManager.compileReport(inputStream);
			
			String caminhoBanner = Faces.getRealPath("/resources/images/banner.png");
			

			Map<String, Object> parametros = new HashMap<>();
			if (proDescricao == null) {
				parametros.put("PRODUTO_DESCRICAO", "%%");
			} else {
				parametros.put("PRODUTO_DESCRICAO", "%" + proDescricao + "%");
			}
			if (fabDescricao == null) {
				parametros.put("FABRICANTE_DESCRICAO", "%%");
			} else {
				parametros.put("FABRICANTE_DESCRICAO", "%" + fabDescricao + "%");
			}
			
			parametros.put("CAMINHO_BANNER", caminhoBanner);

			Connection conexao = HibernateUtil.getConexao();

			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);
			
			JasperViewer.viewReport(relatorio, true);
			
			
		} catch (JRException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar o relatório");
			erro.printStackTrace();
		}
	}	  
	
	public void download(ActionEvent event) {
		
		try {
			produto = (Produto) event.getComponent().getAttributes().get("produtoSelecionado");
			
			InputStream stream = new FileInputStream("C:/ProgramaçãoWeb/Uploads/" + produto.getCodigo() +".png");
			foto = new DefaultStreamedContent(stream, "image/png", produto.getCodigo()+".png");
			
		} catch (FileNotFoundException e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar fazer o download da foto.");
			e.printStackTrace();
		}
		
	}
	
	  
	
}