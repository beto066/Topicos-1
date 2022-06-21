package br.unitins.skateshop.model;

public enum NivelAdm {
	COMUM(1, "Comum"), 
	ADMINISTRADOR(2, "Administrador"), 
	MASTER(3, "Master");
	
	private String descricao;
	private Integer id;
	
	private NivelAdm(Integer id, String descricao) {
		this.descricao = descricao;
		this.id = id;
	}
	
	public static NivelAdm valueOf(int id) {
		for(NivelAdm raridade : NivelAdm.values()) {
			if (id == raridade.getId())
				return raridade;
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
