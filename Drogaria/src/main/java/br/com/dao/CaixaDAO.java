package br.com.dao;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.domain.Caixa;
import br.com.util.HibernateUtil;

public class CaixaDAO extends GenericDAO<Caixa> {
	
	public Caixa buscar(Date dataAbertura) {
		
		Session sessao = HibernateUtil.getFabricaSessoes().openSession();
		
		try {
			
			Criteria consulta = sessao.createCriteria(Caixa.class);
			consulta.add(Restrictions.eq("dataAbertura", dataAbertura));
			Caixa resultado = (Caixa) consulta.uniqueResult();
			return resultado; 
			
		} catch (RuntimeException e) {
			throw e;
			
		}finally {
			sessao.close();
		}
	}

}
