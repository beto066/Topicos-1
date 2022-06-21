package br.unitins.skateshop.model;

public class DetalhesEntrega {
	private String cidade;
	private String bairo;
	private String logradouro;
	private String cep;
	private String referencia;
	
	public String getCidade() {
		return cidade;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public String getBairo() {
		return bairo;
	}
	
	public void setBairo(String bairo) {
		this.bairo = bairo;
	}
	
	public String getLogradouro() {
		return logradouro;
	}
	
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
	public String getCep() {
		return cep;
	}
	
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	public String getReferencia() {
		return referencia;
	}
	
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
}
