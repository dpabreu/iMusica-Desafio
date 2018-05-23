package br.com.desafio.controller.produto;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.desafio.entity.produto.ProdutoEntity;

@Stateless
public class ProdutoController {
	
	@PersistenceContext
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void salvar(ProdutoEntity produto){
		this.em.persist(produto);
		this.em.flush();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void alterar(ProdutoEntity produto) {
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

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void excluir(Integer idProduto) {
		ProdutoEntity produto = this.getProdutoById(idProduto);

		this.em.remove(produto);
		this.em.flush();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ProdutoEntity realizarVenda(Integer idProduto, Integer qtdVenda){
		ProdutoEntity produto = this.getProdutoById(idProduto);
		
		produto.setQuantidade(produto.getQuantidade() - qtdVenda);
		
		this.em.merge(produto);
		this.em.flush();
		
		return produto;
	}
}
