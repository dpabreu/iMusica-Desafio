package br.com.desafio.repository;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.desafio.repository.entity.ProdutoEntity;

@Stateless
/**
 * 
 * @author daniel
 * A anotação TransactionManagement assegura que todos os métodos
 * do EJB sejam transacionais. Resolvendo o problema de concorrência
 * no método realizarVenda().
 * Mesmo podendo usar a anotação TransactionAttribute somente no método,
 * achei uma melhor estratégia usar a anotação global, pois temos métodos
 * de CRUD que também precisam ser transacionais.
 * 
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProdutoRepository {
	
	@PersistenceContext
	private EntityManager em;

	public void salvar(ProdutoEntity produto){
		this.em.persist(produto);
		this.em.flush();
	}

	public void Alterar(ProdutoEntity produto) {
		this.em.merge(produto);
		this.em.flush();
	}

	@SuppressWarnings("unchecked")
	public List<ProdutoEntity> getProdutos() {
		List<ProdutoEntity> produtos = new ArrayList<ProdutoEntity>();
		produtos = this.em.createQuery("select p from produto p order by p.nome").getResultList();
		return produtos;

	}

	public ProdutoEntity getProdutoById(Integer idProduto) {
		return this.em.find(ProdutoEntity.class, idProduto);
	}

	public void excluir(Integer idProduto) {
		ProdutoEntity produto = this.getProdutoById(idProduto);

		this.em.remove(produto);
		this.em.flush();
	}
	
	public ProdutoEntity realizarVenda(Integer idProduto, Integer qtdVenda){
		ProdutoEntity produto = this.getProdutoById(idProduto);
		
		produto.setQuantidade(produto.getQuantidade() - qtdVenda);
		
		this.em.merge(produto);
		this.em.flush();

		return produto;
		
	}
}
