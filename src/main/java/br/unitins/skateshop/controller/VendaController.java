package br.unitins.skateshop.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.skateshop.application.Redirecionador;
import br.unitins.skateshop.application.Redirect;
import br.unitins.skateshop.application.SuporteFlash;
import br.unitins.skateshop.application.SuporteSession;
import br.unitins.skateshop.application.Util;
import br.unitins.skateshop.dao.VendaDao;
import br.unitins.skateshop.model.ItemVenda;
import br.unitins.skateshop.model.TipoPesquisa;
import br.unitins.skateshop.model.Usuario;
import br.unitins.skateshop.model.Venda;

@Named
@ViewScoped
public class VendaController implements Redirect, Serializable {
	private static final long serialVersionUID = -6722850329957357015L;
	private LocalDate pesquisaData;
	private String pesquisaTexto;
	private List<Venda> listaVenda;
	private Usuario usuarioLogado;
	private TipoPesquisa tipoPesquisa;

	public void pesquisar() {
		VendaDao dao = new VendaDao();
		List<Venda> listaPesquisa = new ArrayList<Venda>();
		
		if (tipoPesquisa == TipoPesquisa.DATA) 
			listaPesquisa = dao.searchByData(getPesquisaData());
		 else 
			listaPesquisa = dao.searchByNameUsuario(getPesquisaTexto());
		
		if (listaPesquisa == null || listaPesquisa.size() < 1) {
			Util.addMessageError("Erro ao tentar pesquisar!!!");
			return;
		}
		
		listaVenda = listaPesquisa;
		Util.addMessageInfo("Pesquisa realizada com sucesso!!!");
	}
	
	public void verDetalhes(Venda venda) {
		VendaDao dao = new VendaDao();
		Venda vendaCompleta = dao.getByVenda(venda);
		SuporteFlash.getInstance().set("flashVenda", vendaCompleta);
		
		Util.redirect("detalhesVenda.xhtml");
	}
	
	public void finalizar() {
		List<ItemVenda> carrinho = (List<ItemVenda>) SuporteSession.getInstance().get("carrinho");
		
		if (carrinho == null || carrinho.isEmpty()) {
			Util.addMessageError("Adcione um item antes de finalizar a compra");
			return;
		}
		
		if (usuarioLogado == null) {
			SuporteSession.getInstance().set("sessionMessageError", "Faça login antes de finalizar a compra");
			redirecionaLogin();
		}
		
		redirecionaFinalizacao();
	}
	
	public void sair() {
		SuporteSession.getInstance().invalidateSession();
		redirecionaLogin();
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
	@Override
	public void redirecionaFormUsuario() {
		Redirecionador.redirecionaFormUsuario();
	}
	@Override
	public void redirecionaConsultaUsuario() {
		Redirecionador.redirecionaConsultaUsuario();
	}
	@Override
	public void redirecionaConsultaProduto() {
		Redirecionador.redirecionaConsultaProduto();
	}
	@Override
	public void redirecionaFinalizacao() {
		Redirecionador.redirecionaFinalizacao();
	}
	
	public TipoPesquisa[] getListaTipoPesquisa() {
		return TipoPesquisa.values();
	}

	public LocalDate getPesquisaData() {
		return pesquisaData;
	}

	public void setPesquisaData(LocalDate pesquisaData) {
		this.pesquisaData = pesquisaData;
	}

	public TipoPesquisa getTipoPesquisa() {
		if(tipoPesquisa == null)
			tipoPesquisa = TipoPesquisa.DATA;
		return tipoPesquisa;
	}

	public void setTipoPesquisa(TipoPesquisa tipoPesquisa) {
		this.tipoPesquisa = tipoPesquisa;
	}

	public List<Venda> getListaVenda() {
		if (getUsuarioLogado() == null) {
			listaVenda = new ArrayList<Venda>();
			
			return listaVenda;
		}
		
		if (listaVenda == null) {
			VendaDao dao = new VendaDao();
			listaVenda = new ArrayList<Venda>();
			if (listaVenda == null)
				listaVenda = new ArrayList<Venda>();
		}
		
		return listaVenda;
	}
	
	public void setListaVenda(List<Venda> listaVenda) {
		this.listaVenda = listaVenda;
	}
	
	public Usuario getUsuarioLogado() {
		if (usuarioLogado == null) {
			usuarioLogado = (Usuario) SuporteSession.getInstance().get("UsuarioLogado");
			if (usuarioLogado == null) 
				redirecionaLogin();
		}
		
		return usuarioLogado;
	}
	
	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String getPesquisaTexto() {
		return pesquisaTexto;
	}

	public void setPesquisaTexto(String pesquisaTexto) {
		this.pesquisaTexto = pesquisaTexto;
	}
}
