package br.unitins.skateshop.controller;

import java.io.Serializable;
import java.time.LocalDateTime;
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
import br.unitins.skateshop.model.Produto;
import br.unitins.skateshop.model.TipoPagamento;
import br.unitins.skateshop.model.Usuario;
import br.unitins.skateshop.model.Venda;

@Named
@ViewScoped
public class CarrinhoController implements Redirect, Serializable {
	private static final long serialVersionUID = 909455745953415408L;
	private Usuario usuarioLogado;
	private List<ItemVenda> listaItens;
	private String pesquisa;
	
	public void remover(ItemVenda item) {
		List<ItemVenda> carrinho = (List<ItemVenda>) SuporteSession.getInstance().get("carrinho");
		
		if(carrinho == null)
			carrinho = new ArrayList<ItemVenda>();
		
		int index = carrinho.indexOf(item);
		
		if(index >= 0) {
			int quantidade = carrinho.get(index).getQuantidade();
			
			carrinho.get(index).setQuantidade(quantidade - 1);
			
			if (quantidade <= 1) 
				carrinho.remove(index);
			
		} else {
			Util.addMessageInfo("Item não encontrado no carrinho co sucesso!!!");
		}
		
		SuporteSession.getInstance().set("carrinho", carrinho);
		
		listaItens = (List<ItemVenda>) SuporteSession.getInstance().get("carrinho");
		
		Util.addMessageInfo("Item removido do carrinho com sucesso!!!");
	}
	
	public void pesquisar() {
		SuporteSession.getInstance().set("Pesquisa", getPesquisa());
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
	
	public Usuario getUsuarioLogado() {
		if (usuarioLogado == null) {
			usuarioLogado = (Usuario) SuporteSession.getInstance().get("UsuarioLogado");
		}
		
		return usuarioLogado;
	}
	
	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	public List<ItemVenda> getListaItens() {
		if (listaItens == null) {
			listaItens = (List<ItemVenda>) SuporteSession.getInstance().get("carrinho");
			
			if (listaItens == null) 
				listaItens = new ArrayList<ItemVenda>();
			
		}
		return listaItens;
	}
	
	public void setListaItens(List<ItemVenda> listaItens) {
		this.listaItens = listaItens;
	}

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}
}
