package br.com.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class HibernateContext implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent event) {
		HibernateUtil.getFabricaSessoes();
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		HibernateUtil.getFabricaSessoes().close();
	}

}
