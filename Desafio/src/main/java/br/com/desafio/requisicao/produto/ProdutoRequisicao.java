package br.com.desafio.requisicao.produto;

import java.util.List;

import br.com.desafio.dto.produto.ProdutoDto;

public class ProdutoRequisicao {
	
	private Integer idProduto;
	private String nome;
	private Float preco;
	private Integer quantidade;
	private Integer qtdVenda;
	
	private List<ProdutoDto> produtos;

	public Integer getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Float getPreco() {
		return preco;
	}

	public void setPreco(Float preco) {
		this.preco = preco;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public List<ProdutoDto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<ProdutoDto> produtos) {
		this.produtos = produtos;
	}

	public Integer getQtdVenda() {
		return qtdVenda;
	}

	public void setQtdVenda(Integer qtdVenda) {
		this.qtdVenda = qtdVenda;
	}
	
}
