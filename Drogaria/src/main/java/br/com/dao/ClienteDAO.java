package br.com.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.domain.Cliente;
import br.com.util.HibernateUtil;

public class ClienteDAO extends GenericDAO<Cliente> {

	@SuppressWarnings("unchecked")
	public List<Cliente> listarOrdenado() {
		Session sessao = HibernateUtil.getFabricaSessoes().openSession();

		try {
			Criteria consulta = sessao.createCriteria(Cliente.class);
			consulta.createAlias("pessoa", "p");
			consulta.addOrder(Order.asc("p.nome"));

			List<Cliente> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException e) {
			throw e;

		} finally {
			sessao.close();
		}

	}

}
