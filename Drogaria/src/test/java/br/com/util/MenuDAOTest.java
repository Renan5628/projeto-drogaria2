package br.com.util;

import java.util.List;

import org.junit.Test;

import br.com.dao.MenuDAO;
import br.com.domain.Menu;

public class MenuDAOTest {
	
	@Test
	public void listar() {
		
		MenuDAO dao = new MenuDAO();
		
		List<Menu> lista = dao.listar();
		
		for (Menu menu : lista) {
			if (menu.getCaminho() == null) {
				System.out.println(menu.getRotulo() + " - " + menu.getCaminho());
				for (Menu item : menu.getItensMenu()){
					System.out.println("\t" + item.getRotulo() + " - " + item.getCaminho());
				}
			}
		}
		
	}

}
