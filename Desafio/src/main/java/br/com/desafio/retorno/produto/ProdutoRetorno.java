package br.com.desafio.retorno.produto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.desafio.requisicao.produto.ProdutoRequisicao;
import br.com.desafio.retorno.Retorno;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ProdutoRetorno extends Retorno {

	@XmlElementWrapper(name = "tipos")
	@XmlElement(name = "tipo")
	private ProdutoRequisicao data;
	
	public ProdutoRequisicao getData() {
		return data;
	}

	public void setData(ProdutoRequisicao data) {
		this.data = data;
	}

}
