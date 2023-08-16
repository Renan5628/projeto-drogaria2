package br.com.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import br.com.dao.CaixaDAO;
import br.com.domain.Caixa;

public class CaixaDAOTest {
	
	
	@Test
	@Ignore
	public void salvar() throws ParseException {
		Caixa caixa = new Caixa();
		caixa.setDataAbertura(new SimpleDateFormat("dd/MM/yyyy").parse("15/08/2023"));
		caixa.setvalorAbertura(new BigDecimal("100.00"));
		
		CaixaDAO caixaDAO = new CaixaDAO();
		caixaDAO.salvar(caixa);
	}
	
	@Test
	@Ignore
	public void buscar() throws ParseException {
		CaixaDAO caixaDAO = new CaixaDAO();
		Caixa caixa = caixaDAO.buscar(new SimpleDateFormat("dd/MM/yyyy").parse("15/08/2023"));
		
		Assert.assertNotNull(caixa);
		
		
	}
}
