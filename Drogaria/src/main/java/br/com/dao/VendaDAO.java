package br.com.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.domain.ItemVenda;
import br.com.domain.Produto;
import br.com.domain.Venda;
import br.com.util.HibernateUtil;

public class VendaDAO extends GenericDAO<Venda>{
	
	public void salvar(Venda venda, List<ItemVenda> itensVenda){
		
		Session sessao = HibernateUtil.getFabricaSessoes().openSession();
		Transaction transaction = null;
		
		try {
			transaction = sessao.beginTransaction();
			sessao.save(venda);
			
			for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
				
				ItemVenda itemvenda = itensVenda.get(posicao);
				itemvenda.setVenda(venda);
				
				sessao.save(itemvenda);
				
				Produto produto = itemvenda.getProduto();
				
				int quantidade = produto.getQuantidade() - itemvenda.getQuantidade();
				
				if(quantidade >= 0) {
					
					produto.setQuantidade(new Short(quantidade + ""));
					sessao.update(produto);
					
				}else {
					throw new RuntimeException("Quantidade insuficiente em estoque.");
					
				}
				
				
			}
			
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

}
