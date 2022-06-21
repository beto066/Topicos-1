package br.unitins.skateshop.model;

public enum Nivel {
	AMADOR(1, "Amador"), 
	MEDIO(2, "Medio"), 
	SEMI(3, "Semiproficional"), 
	PROFICIONAL(4, "Proficional");
	
	private String descricao;
	private Integer id;
	
	private Nivel(Integer id, String descricao) {
		this.descricao = descricao;
		this.id = id;
	}

	public static Nivel valueOf(int id) {
		for(Nivel raridade : Nivel.values()) {
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
