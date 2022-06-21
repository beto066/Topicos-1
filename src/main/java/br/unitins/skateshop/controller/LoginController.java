package br.unitins.skateshop.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.skateshop.application.Redirecionador;
import br.unitins.skateshop.application.Redirect;
import br.unitins.skateshop.application.SuporteFlash;
import br.unitins.skateshop.application.SuporteSession;
import br.unitins.skateshop.application.Util;
import br.unitins.skateshop.dao.UsuarioDao;
import br.unitins.skateshop.model.Usuario;

@Named
@ViewScoped
public class LoginController  implements Redirect, Serializable {
	private static final long serialVersionUID = -2064952918265534920L;
	private Usuario usuario;
	private String pesquisa;
	
	public LoginController() {
		try {
			String message = (String) SuporteSession.getInstance().get("sessionMessageError");
			if (message != null) 
				Util.addMessageError(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			String message = (String) SuporteSession.getInstance().get("sessionMessageWarn");
			if (message != null) 
				Util.addMessageWarn(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			String message = (String) SuporteSession.getInstance().get("sessionMessageInfo");
			if (message != null) 
				Util.addMessageInfo(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void entrar() {
		UsuarioDao dao = new UsuarioDao();
		
		String hash = Util.hash(getUsuario().getSenha() + getUsuario().getNomeUsuario());
		
		Usuario usu = dao.verificarLogin(usuario, hash);
		
		if(usu == null) {
			Util.addMessageError("Erro! login ou senha inválido!!!");
			return;
		}
		
		SuporteSession.getInstance().set("UsuarioLogado", usu);
		String pagina = (String) SuporteSession.getInstance().get("sessionPage");
		SuporteSession.getInstance().remove("sessionPage");
		if (pagina == null)
			pagina = "/SkateShop/faces/index.xhtml";
		
		Util.redirect(pagina);
	}
	
	public void limpar() {
		setUsuario(null);
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
	
	public Usuario getUsuario() {
		if(usuario == null)
			usuario = new Usuario();
		
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}
}
