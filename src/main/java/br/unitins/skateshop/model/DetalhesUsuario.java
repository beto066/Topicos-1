package br.unitins.skateshop.model;

public class DetalhesUsuario {
	private Integer QuantProdutosComprados;
	private Float ValorTotalGasto;
	private Integer QuantidadeCompras;
	
	public Integer getQuantProdutosComprados() {
		return QuantProdutosComprados;
	}
	
	public void setQuantProdutosComprados(Integer quantProdutosComprados) {
		QuantProdutosComprados = quantProdutosComprados;
	}
	
	public Float getValorTotalGasto() {
		return ValorTotalGasto;
	}
	
	public void setValorTotalGasto(Float valorTotalGasto) {
		ValorTotalGasto = valorTotalGasto;
	}
	
	public Integer getQuantidadeCompras() {
		return QuantidadeCompras;
	}

	public void setQuantidadeCompras(Integer quantidadeCompras) {
		QuantidadeCompras = quantidadeCompras;
	}
}
