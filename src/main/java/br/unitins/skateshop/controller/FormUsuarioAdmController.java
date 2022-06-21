package br.unitins.skateshop.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.skateshop.application.Redirecionador;
import br.unitins.skateshop.application.Redirect;
import br.unitins.skateshop.application.SuporteFlash;
import br.unitins.skateshop.application.SuporteSession;
import br.unitins.skateshop.application.Util;
import br.unitins.skateshop.dao.UsuarioDao;
import br.unitins.skateshop.model.ItemVenda;
import br.unitins.skateshop.model.NivelAdm;
import br.unitins.skateshop.model.Usuario;

@Named
@ViewScoped
public class FormUsuarioAdmController implements Redirect, Serializable  {
	private static final long serialVersionUID = -5341888184721453922L;
	private Usuario usuarioLogado;
	private Usuario usuarioAlvo;
	private String Pesquisa;
	
	public FormUsuarioAdmController() {
		try {
			usuarioAlvo = (Usuario) SuporteFlash.getInstance().get("flashUsuario");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			usuarioAlvo = (Usuario) SuporteFlash.getInstance().get("flashUsuarioPerfil");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void incluir() {
		if (!validaCampos()) 
			return;
		
		UsuarioDao dao = new UsuarioDao();
		
		String senha = Util.hash(getUsuarioAlvo().getSenha() + getUsuarioAlvo().getNomeUsuario());
		
		getUsuarioAlvo().setSenha(senha);
		
		if (!dao.insert(getUsuarioAlvo())) {
			Util.addMessageWarn("Erro ao tentar cadaastrar o usuario");
			limpar();
			return;
		}
		
		Util.addMessageInfo("Usuario cadastrado com sucesso");
		limpar();
	}
	
	public void alterar() {
		if (!validaCampos()) 
			return;
		
		UsuarioDao dao = new UsuarioDao();
		
		String senha = Util.hash(getUsuarioAlvo().getSenha() + getUsuarioAlvo().getCpf());
		
		getUsuarioAlvo().setSenha(senha);
		
		if (!dao.update(getUsuarioAlvo())) {
			Util.addMessageWarn("Erro ao tentar atualizar o usuario");
			return;
		}
		
		Util.addMessageInfo("Usuario atualizado com sucesso");
		redirecionaPerfil();
	}
	
	public void excluir() {
		UsuarioDao dao = new UsuarioDao();
		
		if (!dao.delete(getUsuarioAlvo().getId())) {
			Util.addMessageWarn("Erro ao tentar deletar o usuario");
			return;
		}
		
		Util.addMessageInfo("Usuario deletado com sucesso");
		redirecionaConsultaUsuario();
	}
	
	public void limpar() {
		Integer id = getUsuarioAlvo().getId();
		
		Usuario usuario = new Usuario();
		usuario.setId(id);
		
		setUsuarioAlvo(usuario);
	}
	
	public void pesquisar() {
		SuporteSession.getInstance().set("Pesquisa", getPesquisa());
		redirecionaDashboard();
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
	
	public boolean validaCampos() {
		boolean retorno = true;
		
		if (getUsuarioAlvo().getNome() == null || getUsuarioAlvo().getNome().isBlank()) {
			Util.addMessageWarn("Insira um nome valido");
			retorno = false;
		}
		
		if (getUsuarioAlvo().getNomeUsuario() == null || getUsuarioAlvo().getNomeUsuario().isBlank()) {
			Util.addMessageWarn("Insira um nome de usuario valido");
			retorno = false;
		}
		
		if (getUsuarioAlvo().getSenha() == null || getUsuarioAlvo().getSenha().isBlank()) {
			Util.addMessageWarn("Insira uma senha valida");
			retorno = false;
		}
		
		if (getUsuarioAlvo().getEmail() == null || getUsuarioAlvo().getEmail().isBlank()) {
			Util.addMessageWarn("Insira um email valido");
			retorno = false;
		}
		
		if (getUsuarioAlvo().getCpf() == null || !Util.isCPF(getUsuarioAlvo().getCpf())) {
			Util.addMessageWarn("Insira um CPF valido");
			retorno = false;
		}
		
		return retorno;
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
	
	public NivelAdm[] getListaNivelAdm() {
		return NivelAdm.values();
	}

	public Usuario getUsuarioLogado() {
		if (usuarioLogado == null) {
			usuarioLogado = (Usuario) SuporteSession.getInstance().get("UsuarioLogado");
			if (usuarioLogado == null)
				usuarioLogado = new Usuario();
		}
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuario) {
		this.usuarioLogado = usuario;
	}
	
	public Usuario getUsuarioAlvo() {
		if(getUsuarioLogado().getNivelAdm().getId() == 1) {
			usuarioAlvo = usuarioLogado;
		}
		
		if (usuarioAlvo == null)
			usuarioAlvo = new Usuario();
		return usuarioAlvo;
	}
	
	public void setUsuarioAlvo(Usuario usuarioAlvo) {
		this.usuarioAlvo = usuarioAlvo;
	}

	public String getPesquisa() {
		return Pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		Pesquisa = pesquisa;
	}
}
