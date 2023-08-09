package br.com.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class Cliente extends GenericDomain{
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataDoCadastro;
	
	@Column(nullable = false)
	private Boolean liberado;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Pessoa pessoa;

	public Date getDataDoCadastro() {
		return dataDoCadastro;
	}

	public void setDataDoCadastro(Date dataDoCadastro) {
		this.dataDoCadastro = dataDoCadastro;
	}

	public Boolean getLiberado() {
		return liberado;
	}

	public void setLiberado(Boolean liberado) {
		this.liberado = liberado;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	
	
}
