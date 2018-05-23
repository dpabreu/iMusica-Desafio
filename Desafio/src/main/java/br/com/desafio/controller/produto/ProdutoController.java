package br.com.desafio.controller.produto;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.desafio.controller.Controller;
import br.com.desafio.http.Produto;
import br.com.desafio.repository.ProdutoRepository;
import br.com.desafio.repository.entity.ProdutoEntity;

@RequestScoped
@Path("/service")
@Produces("application/json; charset=UTF-8")
public class ProdutoController extends Controller{
	
	
	@Inject
	private ProdutoRepository repository;
	
	/**
	 * @Consumes - determina o formato dos dados que vamos postar
	 * @Produces - determina o formato dos dados que vamos retornar
	 * 
	 * Esse método cadastra um novo produto
	 * */
	@POST	
	@Consumes("application/json; charset=UTF-8")
	@Path("/create")
	public Response create(Produto produto){
		Response response = null;
		ProdutoEntity entity = new ProdutoEntity();
		try {
			InitialContext init = new InitialContext();
			repository = (ProdutoRepository) init.lookup("java:module/ProdutoRepository");

			entity.setNome(produto.getNome());
			entity.setPreco(produto.getPreco());
			entity.setQuantidade(produto.getQuantidade());
 
			repository.salvar(entity);
			
			Status status = Status.OK;
			response = build(status, entity);

			return response;
		} catch (Exception e) {
			Status status = Status.NOT_FOUND;
			response = build(status, entity);
			
			return response;
		}
	}
 
	/**
	 * Essse método altera um produto já cadastrado
	 * **/
	@PUT
	@Consumes("application/json; charset=UTF-8")	
	@Path("/update")
	public Response update(Produto produto){
		Response response = null;
		ProdutoEntity entity = new ProdutoEntity();
 
		try {
			InitialContext init = new InitialContext();
			repository = (ProdutoRepository) init.lookup("java:module/ProdutoRepository");
 
			entity.setIdProduto(produto.getIdProduto());
			entity.setNome(produto.getNome());
			entity.setPreco(produto.getPreco());
			entity.setQuantidade(produto.getQuantidade());
 
			repository.Alterar(entity);
 
			Status status = Status.OK;
			response = build(status, entity);

			return response;
		} catch (Exception e) {
			Status status = Status.NOT_FOUND;
			response = build(status, entity);

			return response;
		}
 
	}
	
	/**
	 * Esse método lista todos produtos cadastrados na base
	 * @throws NamingException 
	 * */
	@GET
	@Path("/readAll")
	public List<Produto> readAll() throws NamingException{
 
		List<Produto> produtos =  new ArrayList<Produto>();

		InitialContext init = new InitialContext();
		repository = (ProdutoRepository) init.lookup("java:module/ProdutoRepository");

		List<ProdutoEntity> produtosEntity = repository.getProdutos();

		produtosEntity.stream().forEach((produtoEntity) -> {
			produtos.add(new Produto(produtoEntity.getIdProduto(), produtoEntity.getNome(), produtoEntity.getPreco(),
					produtoEntity.getQuantidade()));
		});

		return produtos;
	}
 
	/**
	 * Esse método busca um produto cadastrado pelo id
	 * */
	@GET
	@Path("/read/{idProduto}")
	public Produto read(@PathParam("idProduto") Integer idProduto){

		try{
		InitialContext init = new InitialContext();
		repository = (ProdutoRepository) init.lookup("java:module/ProdutoRepository");

		ProdutoEntity produtoEntity = repository.getProdutoById(idProduto);
 
		if(produtoEntity != null)
			return new Produto(produtoEntity.getIdProduto(), produtoEntity.getNome(), produtoEntity.getPreco(),
					produtoEntity.getQuantidade());
		}catch (Exception e){
			
		}
		return null;
	}
	
	
 
	/**
	 * Excluindo um produto pelo id
	 * */
	@DELETE
	@Path("/delete/{idProduto}")	
	public String delete(@PathParam("idProduto") Integer idProduto){
 
		try {
			InitialContext init = new InitialContext();
			repository = (ProdutoRepository) init.lookup("java:module/ProdutoRepository");

			repository.excluir(idProduto);
 
			return "Registro excluido com sucesso!";
		} catch (Exception e) {
			return "Erro ao excluir o registro! " + e.getMessage();
		}
	}
	
	/**
	 * Realizando venda de um produto
	 * */
	@PUT
	@Consumes("application/json; charset=UTF-8")	
	@Path("/venda")
	public Response venda(@QueryParam("idProduto") Integer idProduto, @QueryParam("quantidade") Integer quantidade){
		Response response = null;
		ProdutoEntity entity = null;
		
		try {
			InitialContext init = new InitialContext();
			repository = (ProdutoRepository) init.lookup("java:module/ProdutoRepository");

			repository.realizarVenda(idProduto, quantidade);
			
			Status status = Status.OK;
			response = build(status, entity);

			return response;
		} catch (Exception e) {
			Status status = Status.NOT_FOUND;
			response = build(status, entity);

			return response;
		}		
	}

}
