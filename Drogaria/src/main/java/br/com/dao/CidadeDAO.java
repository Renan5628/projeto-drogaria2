package br.com.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.domain.Cidade;
import br.com.util.HibernateUtil;

public class CidadeDAO extends GenericDAO<Cidade>{
	
	
	@SuppressWarnings("unchecked") 
	public List<Cidade> buscarPorEstado(Long estadoCodigo){
		Session sessao = HibernateUtil.getFabricaSessoes().openSession();

		try {
			Criteria consulta = sessao.createCriteria(Cidade.class);
			
			consulta.add(Restrictions.eqOrIsNull("estado.codigo", estadoCodigo));
			
			consulta.addOrder(Order.asc("nome"));
			List<Cidade> resultado = consulta.list();
			return resultado;

		} catch (Exception e) {
			throw e;

		} finally {
			sessao.close();
		}
	}

}
