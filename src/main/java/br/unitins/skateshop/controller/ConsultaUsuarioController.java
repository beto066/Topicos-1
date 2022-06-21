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
import br.unitins.skateshop.model.ItemVenda;
import br.unitins.skateshop.model.Produto;
import br.unitins.skateshop.model.TipoPagamento;
import br.unitins.skateshop.model.Usuario;
import br.unitins.skateshop.model.Venda;

@Named
@ViewScoped
public class ConsultaUsuarioController implements Redirect, Serializable {
	private static final long serialVersionUID = -4322700289109036113L;
	private Usuario usuarioLogado;
	private List<Usuario> listaUsuario;
	private String pesquisa;
	
	public void pesquisar() {
		UsuarioDao dao = new UsuarioDao();
		List<Usuario> listaPesquisa = new ArrayList<Usuario>();
		
		listaPesquisa = dao.search(getPesquisa());
		
		if (listaPesquisa == null || listaPesquisa.size() < 1) {
			Util.addMessageError("Erro ao tentar pesquisar!!!");
			return;
		}
		
		listaUsuario = listaPesquisa;
		Util.addMessageInfo("Pesquisa realizada com sucesso!!!");
	}
	
	public void editar(int id) {
		UsuarioDao dao = new UsuarioDao();
		Usuario usuario = dao.getById(id);
		
		SuporteFlash.getInstance().set("flashUsuario", usuario);
		
		redirecionaFormUsuario();
	}
	
	public void excluir(Integer id) {
		UsuarioDao dao = new UsuarioDao();
		
		if (!dao.delete(id)) {
			Util.addMessageWarn("Erro ao tentar deletar o usuario");
			return;
		}

		setListaUsuario(null);
		Util.addMessageInfo("Usuario deletado com sucesso");
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
	
	public List<Usuario> getListaUsuario() {
		if (listaUsuario == null) {
			UsuarioDao dao = new UsuarioDao();
			listaUsuario = dao.getAll();
		}
		
		return listaUsuario;
	}
	
	public void setListaUsuario(List<Usuario> listaUsuario) {		
		this.listaUsuario = listaUsuario;
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
