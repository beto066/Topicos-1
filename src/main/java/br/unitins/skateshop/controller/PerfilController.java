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
import br.unitins.skateshop.dao.ProdutoDao;
import br.unitins.skateshop.dao.UsuarioDao;
import br.unitins.skateshop.dao.VendaDao;
import br.unitins.skateshop.model.DetalhesUsuario;
import br.unitins.skateshop.model.ItemVenda;
import br.unitins.skateshop.model.Produto;
import br.unitins.skateshop.model.TipoPagamento;
import br.unitins.skateshop.model.Usuario;
import br.unitins.skateshop.model.Venda;

@Named
@ViewScoped
public class PerfilController implements Redirect, Serializable {
	private static final long serialVersionUID = -4322700289109036113L;
	private Usuario usuarioLogado;
	private String pesquisa;
	private DetalhesUsuario detalhes;
	
	public void pesquisar() {
		SuporteSession.getInstance().set("Pesquisa", getPesquisa());
		redirecionaDashboard();
	}
	
	public void editar() {		
		SuporteFlash.getInstance().set("flashUsuarioPerfil", getUsuarioLogado());
		
		redirecionaFormUsuario();
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

	public DetalhesUsuario getDetalhes() {
		if (detalhes == null) {
			UsuarioDao dao = new UsuarioDao();
			detalhes = dao.getDetalhes(usuarioLogado);
			if (detalhes == null)
				detalhes = new DetalhesUsuario();
		}
		return detalhes;
	}

	public void setDetalhes(DetalhesUsuario detalhes) {
		this.detalhes = detalhes;
	}

	public Usuario getUsuarioLogado() {
		if (usuarioLogado == null) {
			usuarioLogado = (Usuario) SuporteSession.getInstance().get("UsuarioLogado");
			
			if (usuarioLogado == null)
				redirecionaDashboard();
		}
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}
}
