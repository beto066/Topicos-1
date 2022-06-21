package br.unitins.skateshop.model;

public enum TipoPagamento {
	BOLETO(1, "Boleto"), 
	CARTAOCREDITO(2, "Cartão de credito"), 
	CARTAOEBITO(3, "Cartão de debito"), 
	PIX(4, "PIX");
	
	private String descricao;
	private Integer id;
	
	private TipoPagamento(Integer id, String descricao) {
		this.descricao = descricao;
		this.id = id;
	}

	public static TipoPagamento valueOf(int id) {
		for(TipoPagamento tipo : TipoPagamento.values()) {
			if (id == tipo.getId())
				return tipo;
		}
		return null;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public Integer getId() {
		return id;
	}
}
