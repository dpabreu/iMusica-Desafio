package br.com.desafio.http;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Produto {
	
	private Integer idProduto;
	private String nome;
	private Float preco;
	private Integer quantidade;
	
	public Produto(){
		
	}
	
	
	public Produto(Integer idProduto, String nome, Float preco, Integer quantidade){
		super();
		this.idProduto = idProduto;
		this.nome = nome;
		this.preco = preco;
		this.quantidade = quantidade;
	}
	
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

}
