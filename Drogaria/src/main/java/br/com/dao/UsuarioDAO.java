package br.com.dao;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.domain.Usuario;
import br.com.util.HibernateUtil;

public class UsuarioDAO extends GenericDAO<Usuario> {
		
	
	public Usuario autenticar(String cpf, String senha) {
		
		Session sessao = HibernateUtil.getFabricaSessoes().openSession();
		
		try {
			Criteria consulta = sessao.createCriteria(Usuario.class);
			
			consulta.createAlias("pessoa", "p");
			consulta.add(Restrictions.eq("p.cpf", cpf));
			
			SimpleHash hash = new SimpleHash("md5",senha);
			consulta.add(Restrictions.eq("senha", hash.toHex()));
			
			Usuario resultado = (Usuario) consulta.uniqueResult();
			
			return resultado;
			
		} catch (Exception e) {
			throw e;
			
		}finally {
			sessao.close();
		}
		
	}
		
		
}
