package br.unitins.skateshop.model;

public enum TipoPesquisa {
	DATA(1, "Data"), 
	TEXTO(2, "Texto");
	
	private String descricao;
	private Integer id;
	
	private TipoPesquisa(Integer id, String descricao) {
		this.descricao = descricao;
		this.id = id;
	}

	public static TipoPesquisa valueOf(int id) {
		for(TipoPesquisa tipo : TipoPesquisa.values()) {
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
