package br.com.desafio.service.produto;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.desafio.controller.produto.ProdutoController;
import br.com.desafio.dto.produto.ProdutoDto;
import br.com.desafio.entity.produto.ProdutoEntity;
import br.com.desafio.requisicao.produto.ProdutoRequisicao;
import br.com.desafio.retorno.Retorno;
import br.com.desafio.retorno.produto.ProdutoRetorno;

@RequestScoped
@Path("/service")
@Produces("application/json; charset=UTF-8")
public class ProdutoService{
	
	
	@Inject
	private ProdutoController repository;
	
	/**
	 * @Consumes - determina o formato dos dados que vamos postar
	 * @Produces - determina o formato dos dados que vamos retornar
	 * 
	 * Esse método cadastra um novo produto
	 * */
	@POST	
	@Consumes("application/json; charset=UTF-8")
	@Path("/create")
	public Response create(ProdutoDto produto){
		Response response = null;
		ProdutoDto dto;
		ProdutoEntity entity = new ProdutoEntity();
		try {
			InitialContext init = new InitialContext();
			repository = (ProdutoController) init.lookup("java:module/ProdutoController");

			entity.setNome(produto.getNome());
			entity.setPreco(produto.getPreco());
			entity.setQuantidade(produto.getQuantidade());
 
			repository.salvar(entity);
			
			dto = new ProdutoDto(entity.getIdProduto(), entity.getNome(), 
						entity.getPreco(), entity.getQuantidade(), "Produto cadastrado com sucesso.");
			
			response = Response.ok(dto, MediaType.APPLICATION_JSON).build();

		} catch (Exception e) {
			dto = new ProdutoDto();
			dto.setMensagem(e.getMessage());
			response = Response.status(Status.OK).entity(dto).build();
		}
		return response;
	}
 
	/**
	 * Essse método altera um produto já cadastrado
	 * 
	 * @param produtoDto
	 * @return Response
	 * **/
	@PUT
	@Consumes("application/json; charset=UTF-8")	
	@Path("/update")
	public Response update(ProdutoDto produtoDto){
		Response response = null;
		ProdutoEntity entity = new ProdutoEntity();
		ProdutoDto dto;
		
		try {
			InitialContext init = new InitialContext();
			repository = (ProdutoController) init.lookup("java:module/ProdutoController");
 
			entity.setIdProduto(produtoDto.getIdProduto());
			entity.setNome(produtoDto.getNome());
			entity.setPreco(produtoDto.getPreco());
			entity.setQuantidade(produtoDto.getQuantidade());
 
			repository.alterar(entity);
 
			dto = new ProdutoDto(entity.getIdProduto(), entity.getNome(), 
					entity.getPreco(), entity.getQuantidade(), "Produto alterado com sucesso.");
		
			response = Response.ok(dto, MediaType.APPLICATION_JSON).build();
	
		} catch (Exception e) {
			dto = new ProdutoDto();
			dto.setMensagem(e.getMessage());
			response = Response.status(Status.OK).entity(dto).build();
		}
		
		return response;
	}
	
	/**
	 * Esse método lista todos produtos cadastrados na base
	 * @throws NamingException 
	 * */
	@GET
	@Path("/readAll")
	public Response readAll() throws NamingException{
		Response response = null;
		ProdutoRetorno produtoRetorno = new ProdutoRetorno();
		ProdutoRequisicao requisicao = new ProdutoRequisicao();

		List<String> msgsErro = new ArrayList<String>();
		List<ProdutoDto> produtos =  new ArrayList<ProdutoDto>();
		
		try{
			InitialContext init = new InitialContext();
			repository = (ProdutoController) init.lookup("java:module/ProdutoController");
	
			List<ProdutoEntity> produtosEntity = repository.getProdutos();
	
			produtosEntity.stream().forEach((produtoEntity) -> {
				produtos.add(new ProdutoDto(produtoEntity.getIdProduto(), produtoEntity.getNome(), produtoEntity.getPreco(),
						produtoEntity.getQuantidade(), "Listagem OK."));
			});
			
			requisicao.setProdutos(produtos);

			msgsErro.add("Leitura feita com sucesso.");

			produtoRetorno.setTemErro(Boolean.FALSE);
			produtoRetorno.setMsgsErro(msgsErro);
			produtoRetorno.setData(requisicao);
			
			Retorno retorno = produtoRetorno;
			response = Response.ok(retorno, MediaType.APPLICATION_JSON).build();
		} catch (Exception e){
			msgsErro.add("Erro ao ler produtos." + e.getMessage());
			Retorno retorno = new Retorno();
			retorno.setTemErro(Boolean.TRUE);
			retorno.setMsgsErro(msgsErro);
			response = Response.status(Status.OK).entity(retorno).build();
		}

		return response;
	}
 
	/**
	 * Esse método busca um produto cadastrado pelo id
	 * */
	@GET
	@Path("/read/{idProduto}")
	public ProdutoDto read(@PathParam("idProduto") Integer idProduto){
		ProdutoDto dto = null;

		try{
			InitialContext init = new InitialContext();
			repository = (ProdutoController) init.lookup("java:module/ProdutoController");
	
			ProdutoEntity produtoEntity = repository.getProdutoById(idProduto);
	 
			if(produtoEntity != null)
				dto = new ProdutoDto(produtoEntity.getIdProduto(), produtoEntity.getNome(), produtoEntity.getPreco(),
							produtoEntity.getQuantidade(), "OK.");
		} catch (Exception e){
			dto = new ProdutoDto();
			dto.setMensagem("Erro ao listar produto: " + e.getMessage());
		}

		return dto;
	}
 
	/**
	 * Excluindo um produto pelo id
	 * */
	@DELETE
	@Path("/delete/{idProduto}")	
	public Response delete(@PathParam("idProduto") Integer idProduto){
		Response response;
		ProdutoDto dto;
		
		try {
			InitialContext init = new InitialContext();
			repository = (ProdutoController) init.lookup("java:module/ProdutoController");

			repository.excluir(idProduto);
 
			dto = new ProdutoDto();
			dto.setMensagem("Produto excluído com sucesso.");
		
			response = Response.ok(dto, MediaType.APPLICATION_JSON).build();
	
		} catch (Exception e) {
			dto = new ProdutoDto();
			dto.setMensagem("Erro ao excluir produto " + e.getMessage());
			response = Response.status(Status.OK).entity(dto).build();
		}
		
		return response;
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
		ProdutoDto dto;
		
		try {
			InitialContext init = new InitialContext();
			repository = (ProdutoController) init.lookup("java:module/ProdutoController");

			entity = repository.realizarVenda(idProduto, quantidade);
			
			dto = new ProdutoDto(entity.getIdProduto(), entity.getNome(), 
					entity.getPreco(), entity.getQuantidade(), "Venda realizada com sucesso.");
		
			response = Response.ok(dto, MediaType.APPLICATION_JSON).build();
	
		} catch (Exception e) {
			dto = new ProdutoDto();
			dto.setMensagem("Erro ao realizar venda. " + e.getMessage());
			response = Response.status(Status.OK).entity(dto).build();
		}
		
		return response;
	}

}
