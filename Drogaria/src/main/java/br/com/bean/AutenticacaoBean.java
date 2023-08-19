package br.com.bean;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.omnifaces.util.Messages.Message;

import br.com.domain.Pessoa;
import br.com.domain.Usuario;

@ManagedBean
@SessionScoped
public class AutenticacaoBean {

	private Usuario usuario;
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@PostConstruct
	public void iniciar() {
		usuario = new Usuario();
		usuario.setPessoa(new Pessoa());
	}
	
	public void autenticar() {
		try {
			
			Faces.redirect("./pages/Principal.xhtml");
			
		} catch (IOException e) {
			e.printStackTrace();
			Messages.addGlobalError("Erro no redirecionamento de tela");
		}
		
	}
	
}
