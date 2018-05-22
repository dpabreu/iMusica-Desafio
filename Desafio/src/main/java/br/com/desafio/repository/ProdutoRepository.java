package br.com.desafio.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.desafio.repository.entity.ProdutoEntity;

//@Stateless
public class ProdutoRepository {
	
	
	private final EntityManagerFactory emFactory;

	private final EntityManager em;

	public ProdutoRepository() {
		this.emFactory = Persistence.createEntityManagerFactory("persistence_unit_desafio");
		this.em = this.emFactory.createEntityManager();
	}
	
	public void Salvar(ProdutoEntity produto){
			this.em.getTransaction().begin();
			this.em.persist(produto);
			this.em.getTransaction().commit();
	}

	public void Alterar(ProdutoEntity produto) {
		this.em.getTransaction().begin();
		this.em.merge(produto);
		this.em.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public List<ProdutoEntity> GetProdutos() {
		List<ProdutoEntity> produtos = new ArrayList<ProdutoEntity>();
		produtos = this.em.createQuery("select p from produto p order by p.nome").getResultList();
		return produtos;

	}

	public ProdutoEntity GetProdutoById(Integer idProduto) {
		return this.em.find(ProdutoEntity.class, idProduto);
	}

	public void Excluir(Integer idProduto) {
		ProdutoEntity produto = this.GetProdutoById(idProduto);

		this.em.getTransaction().begin();
		this.em.remove(produto);
		this.em.getTransaction().commit();
	}
	
//	public void realizarVenda(Integer idProduto)
}
