package br.com.desafio.dto.produto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe que atua como um Data Transfer Object que representa um Produto 
 * e é responsável por expor suas informações aos consumidores de serviço.
 *
 * @author Daniel Abreu
 *
 */
@XmlRootElement
public class ProdutoDto implements Serializable {
	
	private static final long serialVersionUID = -3606776613586206599L;
	
	private Integer idProduto;
	private String nome;
	private Float preco;
	private Integer quantidade;
	private String mensagem;
	
	public ProdutoDto(){
		
	}
	
	public ProdutoDto(Integer idProduto, String nome, Float preco, 
				Integer quantidade, String mensagem){
		super();
		this.idProduto = idProduto;
		this.nome = nome;
		this.preco = preco;
		this.quantidade = quantidade;
		this.mensagem = mensagem;
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

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
