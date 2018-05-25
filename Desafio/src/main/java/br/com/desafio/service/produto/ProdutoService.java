package br.com.desafio.service.produto;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.desafio.controller.produto.ProdutoController;
import br.com.desafio.requisicao.produto.ProdutoRequisicao;
import br.com.desafio.retorno.Retorno;
import br.com.desafio.retorno.produto.ProdutoRetorno;

/**
 * Esta classe é responsável pele camada de serviços REST do 
 * Produto.
 *  
 * @author Daniel Abreu
 *
 */
@RequestScoped
@Path("/service")
/**
 * @Produces - determina o formato dos dados que vamos retornar
 * */
@Produces("application/json; charset=UTF-8")
public class ProdutoService{
	
	@Inject
	private ProdutoController controller;
	
	/**
	 * @Consumes - determina o formato dos dados que vamos postar
	 *
	 * Esse método cadastra um novo produto
	 * */
	@POST	
	@Consumes("application/json; charset=UTF-8")
	@Path("/create")
	public Response create(ProdutoRequisicao requisicao){
		Response response = null;
		ProdutoRetorno retorno = new ProdutoRetorno();
		retorno = controller.create(requisicao); 
		response = Response.ok(retorno, MediaType.APPLICATION_JSON).build();
		return response;
	}
 
	/**
	 * Essse método altera um produto já cadastrado
	 * 
	 * 
	 * @return Response
	 * **/
	@PUT
	@Consumes("application/json; charset=UTF-8")	
	@Path("/update")
	public Response update(ProdutoRequisicao requisicao){
		Response response = null;
		ProdutoRetorno retorno = new ProdutoRetorno();
		retorno = controller.update(requisicao);
		response = Response.ok(retorno, MediaType.APPLICATION_JSON).build();
		return response;
	}
	
	/**
	 * Esse método lista todos produtos cadastrados na base
	 * 
	 * */
	@GET
	@Path("/readAll")
	public Response readAll(){
		Response response = null;
		Retorno retorno = new Retorno();
		retorno = controller.readAll();
		response = Response.ok(retorno, MediaType.APPLICATION_JSON).build();
		return response;
	}
 
	/**
	 * Esse método busca um produto cadastrado pelo id
	 * 
	 * @param idProduto
	 * @return Response
	 * */
	@GET
	@Path("/read/{idProduto}")
	public Response read(@PathParam("idProduto") Integer idProduto){
		Response response = null;
		Retorno retorno = new Retorno();
		retorno = controller.readByIdProduto(idProduto);
		response = Response.ok(retorno, MediaType.APPLICATION_JSON).build();
		return response;
	}
 
	/**
	 * Excluindo um produto pelo id
	 * 
	 * @param idProduto
	 * @return Response
	 * */
	@DELETE
	@Path("/delete/{idProduto}")	
	public Response delete(@PathParam("idProduto") Integer idProduto){
		Response response;
		Retorno retorno = new Retorno();
		retorno = controller.delete(idProduto);
		response = Response.ok(retorno, MediaType.APPLICATION_JSON).build();
		return response;
	}
	
	/**
	 * Realizando venda de um produto
	 * 
	 * @param idProduto
	 * @param quantidade
	 * @return Response
	 * */
	@PUT
	@Consumes("application/json; charset=UTF-8")	
	@Path("/venda")
	public Response venda(@QueryParam("idProduto") Integer idProduto, @QueryParam("quantidade") Integer quantidade){
		Response response = null;
		Retorno retorno = new Retorno();
		retorno = controller.venda(idProduto, quantidade);
		response = Response.ok(retorno, MediaType.APPLICATION_JSON).build();
		return response;
	}

}
