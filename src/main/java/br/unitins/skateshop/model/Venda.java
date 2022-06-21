package br.unitins.skateshop.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import javax.faces.convert.DateTimeConverter;

public class Venda implements Cloneable{
	private Integer id;
	private Usuario usuario;
	private LocalDateTime date;
	private Float desconto;
	private TipoPagamento tipo_pagamento;
	private Float totalVenda;
	private ArrayList<ItemVenda> listaItem;
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Venda other = (Venda) obj;
		return Objects.equals(id, other.id);
	}
	
	public Venda getClone() {
		try {
			return (Venda) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	public DateTimeConverter getConverter() {
//		DateTimeConverter converter = new DateTimeConverter();
//		
//		return null;
//	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Float getDesconto() {
		return desconto;
	}
	
	public void setDesconto(Float desconto) {
		this.desconto = desconto;
	}
	
	public TipoPagamento getTipo_pagamento() {
		return tipo_pagamento;
	}
	
	public void setTipo_pagamento(TipoPagamento tipo_pagamento) {
		this.tipo_pagamento = tipo_pagamento;
	}

	public ArrayList<ItemVenda> getListaItem() {
		return listaItem;
	}

	public void setListaItem(ArrayList<ItemVenda> listaItem) {
		this.listaItem = listaItem;
	}

	public Float getTotalVenda() {
		return totalVenda;
	}

	public void setTotalVenda(Float totalVenda) {
		this.totalVenda = totalVenda;
	}
}
