package br.com.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("drogaria")
public class DrogariaService {
	
	@GET
	public String exibir(){
		return "Curso Programação Web";
	}
	
}
