package br.com.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.util.HibernateUtil;

public class GenericDAO<E> {
	
	private Class<E> entity;
	
	
	@SuppressWarnings("unchecked")
	public GenericDAO() {
		this.entity = (Class<E>) ((ParameterizedType)getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}
	
	public void salvar(E entity){
		
		Session sessao = HibernateUtil.getFabricaSessoes().openSession();
		Transaction transaction = null;
		
		try {
			transaction = sessao.beginTransaction();
			sessao.save(entity);
			transaction.commit();
			
		} catch (RuntimeException e) {
			 if(transaction != null){
				 transaction.rollback();
			 }
			 throw e;
			 
		} finally {
			sessao.close();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<E> listar(){
		Session sessao = HibernateUtil.getFabricaSessoes().openSession();
		
		try {
			Criteria consulta = sessao.createCriteria(entity);
			List<E> resultado = consulta.list();
			return resultado;
			
		} catch (Exception e) {
			throw e;
			
		}finally {
			sessao.close();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<E> listar(String campoOrdenação){
		Session sessao = HibernateUtil.getFabricaSessoes().openSession();
		
		try {
			Criteria consulta = sessao.createCriteria(entity);
			consulta.addOrder(Order.asc(campoOrdenação));
			List<E> resultado = consulta.list();
			return resultado;
			
		} catch (Exception e) {
			throw e;
			
		}finally {
			sessao.close();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public E buscar(Long codigo){
		Session sessao = HibernateUtil.getFabricaSessoes().openSession();
		
		try {
			Criteria consulta = sessao.createCriteria(entity);
			consulta.add(Restrictions.idEq(codigo));
			E resultado = (E) consulta.uniqueResult();
			return resultado;
			
		} catch (Exception e) {
			throw e;
			
		}finally {
			sessao.close();
		}
		
	}
	
	
	public void excluir(E entity){
		
		Session sessao = HibernateUtil.getFabricaSessoes().openSession();
		Transaction transaction = null;
		
		try {
			transaction = sessao.beginTransaction();
			sessao.delete(entity);
			transaction.commit();
			
		} catch (RuntimeException e) {
			 if(transaction != null){
				 transaction.rollback();
			 }
			 throw e;
			 
		} finally {
			sessao.close();
		}
		
	}
	
	public void editar(E entity){
		
		Session sessao = HibernateUtil.getFabricaSessoes().openSession();
		Transaction transaction = null;
		
		try {
			transaction = sessao.beginTransaction();
			sessao.update(entity);
			transaction.commit();
			
		} catch (RuntimeException e) {
			 if(transaction != null){
				 transaction.rollback();
			 }
			 throw e;
			 
		} finally {
			sessao.close();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public E merge(E entity){
		
		Session sessao = HibernateUtil.getFabricaSessoes().openSession();
		Transaction transaction = null;
		
		try {
			transaction = sessao.beginTransaction();
			 E retorno = (E) sessao.merge(entity);
			transaction.commit();
			
			return retorno;
			
		} catch (RuntimeException e) {
			 if(transaction != null){
				 transaction.rollback();
			 }
			 throw e;
			 
		} finally {
			sessao.close();
		}
		
	}
	
}
