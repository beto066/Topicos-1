package br.unitins.skateshop.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.skateshop.application.Redirecionador;
import br.unitins.skateshop.application.Redirect;
import br.unitins.skateshop.application.SuporteSession;
import br.unitins.skateshop.application.Util;
import br.unitins.skateshop.model.DetalhesEntrega;
import br.unitins.skateshop.model.ItemVenda;
import br.unitins.skateshop.model.TipoPagamento;
import br.unitins.skateshop.model.Usuario;
import br.unitins.skateshop.model.Venda;

@Named
@ViewScoped
public class FinalizacaoVendaController implements Redirect, Serializable {
	private static final long serialVersionUID = -3876191710071491475L;
	private Usuario usuarioLogado;
	private DetalhesEntrega entrega;
	private Integer idTipoPagamento;
	private String pesquisa;
	
	public void finalizar() {
		List<ItemVenda> listaItens = (List<ItemVenda>) SuporteSession.getInstance().get("carrinho");
		Util.finalizaPedido(getUsuarioLogado(), listaItens, getIdTipoPagamento());
	}
	
	public void limpar() {
		entrega = null;
	}
	
	public void sair() {
		SuporteSession.getInstance().invalidateSession();
		redirecionaLogin();
	}
	
	public void pesquisar() {
		SuporteSession.getInstance().set("Pesquisa", getPesquisa());
		redirecionaDashboard();
	}
	
	@Override
	public void redirecionaDashboard() {
		Redirecionador.redirecionaDashboard();
	}
	@Override
	public void redirecionaLogin() {
		Redirecionador.redirecionaLogin();
	}
	@Override
	public void redirecionaCadastroUsuario() {
		Redirecionador.redirecionaCadastroUsuario();
	}
	@Override
	public void redirecionaCadastroProduto() {
		Redirecionador.redirecionaCadastroProduto();
	}
	@Override
	public void redirecionaVendas() {
		Redirecionador.redirecionaVendas();
	}
	@Override
	public void redirecionaCarrinho() {
		Redirecionador.redirecionaCarrinho();
	}
	@Override
	public void redirecionaHistorico() {
		Redirecionador.redirecionaHistorico();
	}
	@Override
	public void redirecionaPerfil() {
		Redirecionador.redirecionaPerfil();
	}
	public void redirecionaFormUsuario() {
		Redirecionador.redirecionaFormUsuario();
	}
	public void redirecionaConsultaUsuario() {
		Redirecionador.redirecionaConsultaUsuario();
	}
	public void redirecionaConsultaProduto() {
		Redirecionador.redirecionaConsultaProduto();
	}
	public void redirecionaFinalizacao() {
		Redirecionador.redirecionaFinalizacao();
	}
	
	public TipoPagamento[] getListaTipoPagamento() {
		return TipoPagamento.values();
	}
	
	public Usuario getUsuarioLogado() {
		if (usuarioLogado == null) 
			usuarioLogado = (Usuario) SuporteSession.getInstance().get("UsuarioLogado");
		
		return usuarioLogado;
	}
	
	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	public DetalhesEntrega getEntrega() {
		if (entrega == null)
			entrega = new DetalhesEntrega();

		return entrega;
	}
	
	public void setEntrega(DetalhesEntrega entrega) {
		this.entrega = entrega;
	}

	public Integer getIdTipoPagamento() {
		return idTipoPagamento;
	}

	public void setIdTipoPagamento(Integer idTipoPagamento) {
		System.out.println(idTipoPagamento);
		this.idTipoPagamento = idTipoPagamento;
	}

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}
}
