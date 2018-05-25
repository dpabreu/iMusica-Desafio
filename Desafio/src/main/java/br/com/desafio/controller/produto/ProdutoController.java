package br.com.desafio.controller.produto;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.desafio.dto.produto.ProdutoDto;
import br.com.desafio.entity.produto.ProdutoEntity;
import br.com.desafio.repository.produto.ProdutoRepository;
import br.com.desafio.requisicao.produto.ProdutoRequisicao;
import br.com.desafio.retorno.produto.ProdutoRetorno;

/**
 * Esta classe é responsável pela cadamada de negócio do Produto.
 * 
 * @author Daniel Abreu
 *
 */
@Stateless
public class ProdutoController {
	
	@Inject
	private ProdutoRepository repository;
	
	private ProdutoRepository setJNDI() throws NamingException{
		InitialContext init = new InitialContext();
		return (ProdutoRepository) init.lookup("java:module/ProdutoRepository");		
	}
	
	public ProdutoRetorno create(ProdutoRequisicao requisicao){
		ProdutoEntity entity = new ProdutoEntity();
		ProdutoRetorno retorno = new ProdutoRetorno();

		List<String> msgsErro = new ArrayList<String>();
		try {
			repository = setJNDI();

			entity.setNome(requisicao.getNome());
			entity.setPreco(requisicao.getPreco());
			entity.setQuantidade(requisicao.getQuantidade());
 
			repository.salvar(entity);
			
			msgsErro.add("Cadastro realizado com sucesso.");

			retorno.setTemErro(Boolean.FALSE);
			retorno.setMsgsErro(msgsErro);
			retorno.setData(requisicao);

		} catch (Exception e) {
			msgsErro.add("Erro ao cadastrar produto." + e.getMessage());
			retorno.setTemErro(Boolean.TRUE);
			retorno.setMsgsErro(msgsErro);
		}
		return retorno;
	}
	
	public ProdutoRetorno update(ProdutoRequisicao requisicao){
		ProdutoEntity entity = new ProdutoEntity();
		ProdutoRetorno retorno = new ProdutoRetorno();

		List<String> msgsErro = new ArrayList<String>();
		try {
			repository = setJNDI();
 
			entity.setIdProduto(requisicao.getIdProduto());
			entity.setNome(requisicao.getNome());
			entity.setPreco(requisicao.getPreco());
			entity.setQuantidade(requisicao.getQuantidade());
 
			repository.alterar(entity);
 
			msgsErro.add("Cadastro atualizado com sucesso.");

			retorno.setTemErro(Boolean.FALSE);
			retorno.setMsgsErro(msgsErro);
			retorno.setData(requisicao);
		} catch (Exception e) {
			msgsErro.add("Erro ao atualizar produto." + e.getMessage());
			retorno.setTemErro(Boolean.TRUE);
			retorno.setMsgsErro(msgsErro);
		}
		
		return retorno;
	}
	
	public ProdutoRetorno readAll(){
		ProdutoRequisicao requisicao = new ProdutoRequisicao();
		ProdutoRetorno retorno = new ProdutoRetorno();

		List<String> msgsErro = new ArrayList<String>();
		List<ProdutoDto> produtos =  new ArrayList<ProdutoDto>();
		
		try{
			repository = setJNDI();
	
			List<ProdutoEntity> produtosEntity = repository.getProdutos();
	
			produtosEntity.stream().forEach((produtoEntity) -> {
				produtos.add(new ProdutoDto(produtoEntity.getIdProduto(), produtoEntity.getNome(), produtoEntity.getPreco(),
						produtoEntity.getQuantidade(), "Listagem OK."));
			});
			
			requisicao.setProdutos(produtos);

			msgsErro.add("Leitura feita com sucesso.");

			retorno.setTemErro(Boolean.FALSE);
			retorno.setMsgsErro(msgsErro);
			retorno.setData(requisicao);
		} catch (Exception e){
			msgsErro.add("Erro ao ler produtos." + e.getMessage());
			retorno.setTemErro(Boolean.TRUE);
			retorno.setMsgsErro(msgsErro);
		}
		
		return retorno;
	}
	
	public ProdutoRetorno readByIdProduto(Integer idProduto){
		ProdutoRequisicao requisicao = new ProdutoRequisicao();
		ProdutoRetorno retorno = new ProdutoRetorno();

		List<String> msgsErro = new ArrayList<String>();
		List<ProdutoDto> produtos =  new ArrayList<ProdutoDto>();
		try{
			repository = setJNDI();
			ProdutoDto dto = new ProdutoDto();
			ProdutoEntity produtoEntity = repository.getProdutoById(idProduto);
	 
			if(produtoEntity != null){
				dto = new ProdutoDto(produtoEntity.getIdProduto(), produtoEntity.getNome(), produtoEntity.getPreco(),
							produtoEntity.getQuantidade(), "OK.");
				
				produtos.add(dto);
			}
			
			if(produtos.size() > 0){
				requisicao.setIdProduto(dto.getIdProduto());
				requisicao.setNome(dto.getNome());
				requisicao.setPreco(dto.getPreco());
				requisicao.setQuantidade(dto.getQuantidade());
				requisicao.setProdutos(produtos);
	
				msgsErro.add("Leitura feita com sucesso.");
				retorno.setTemErro(Boolean.FALSE);
			}else{
				msgsErro.add("Produto não localizado.");
				retorno.setTemErro(Boolean.TRUE);
			}
			
			retorno.setMsgsErro(msgsErro);
			retorno.setData(requisicao);
		} catch (Exception e){
			msgsErro.add("Erro ao ler produtos." + e.getMessage());
			retorno.setTemErro(Boolean.TRUE);
			retorno.setMsgsErro(msgsErro);
		}
		
		return retorno;
	}
	
	public ProdutoRetorno delete(Integer idProduto){
		ProdutoRetorno retorno = new ProdutoRetorno();

		List<String> msgsErro = new ArrayList<String>();
		try {
			repository = setJNDI();

			repository.excluir(idProduto);
 
			msgsErro.add("Registro excluído com sucesso.");

			retorno.setTemErro(Boolean.FALSE);
			retorno.setMsgsErro(msgsErro);
		} catch (Exception e) {
			msgsErro.add("Erro ao excluir produtos." + e.getMessage());
			retorno.setTemErro(Boolean.TRUE);
			retorno.setMsgsErro(msgsErro);
		}

		return retorno;
	}
	
	public ProdutoRetorno venda(Integer idProduto, Integer qtdVenda){
		ProdutoRetorno retorno = new ProdutoRetorno();

		List<String> msgsErro = new ArrayList<String>();
		try {
			repository = setJNDI();
			repository.realizarVenda(idProduto, qtdVenda);
	
			msgsErro.add("Venda realizada com sucesso.");

			retorno.setTemErro(Boolean.FALSE);
			retorno.setMsgsErro(msgsErro);
		} catch (Exception e) {
			msgsErro.add("Erro ao realizar venda." + e.getMessage());
			retorno.setTemErro(Boolean.TRUE);
			retorno.setMsgsErro(msgsErro);
		}
		
		return retorno;
	}
}
