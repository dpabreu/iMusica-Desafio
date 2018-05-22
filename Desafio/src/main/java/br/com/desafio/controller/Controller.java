package br.com.desafio.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class Controller {

	protected Response build(Status status, Object object){
		return Response.status(status)
				.entity(object)
				.header("Acess-Control-Allow-Origin", "*")
				.header("Acess-Control-Allow-Credentials", "true")
				.header("Acess-Control-Allow-Headers", "Origin, X-Request-Width, Content-Type, Accept")
				.header("Cache-Control", "no-cache, no-store, must-revalidate")
				.header("Pragma", "no-cache")
				.header("Expires", 0)
				.build();
	}
}
