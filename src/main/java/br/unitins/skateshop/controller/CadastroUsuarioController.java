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
public class CadastroUsuarioController implements Redirect, Serializable {
	private static final long serialVersionUID = -1041108461524838211L;
	private Usuario usuario;
	private String pesquisa;

	public void incluir() {
		if (!validaCampos()) 
			return;
		
		UsuarioDao dao = new UsuarioDao();
		
		String senha = Util.hash(getUsuario().getSenha() + getUsuario().getNomeUsuario());
		
		getUsuario().setSenha(senha);
		
		if (!dao.insert(getUsuario())) {
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
		
		String senha = Util.hash(getUsuario().getSenha() + getUsuario().getCpf());
		
		getUsuario().setSenha(senha);
		
		if (!dao.update(getUsuario())) {
			Util.addMessageWarn("Erro ao tentar atualizar o usuario");
			return;
		}
		
		Util.addMessageInfo("Usuario atualizado com sucesso");
		redirecionaPerfil();
	}
	
	public void excluir() {
		UsuarioDao dao = new UsuarioDao();
		
		if (!dao.delete(getUsuario().getId())) {
			Util.addMessageWarn("Erro ao tentar deletar o usuario");
			return;
		}
		
		Util.addMessageInfo("Usuario deletado com sucesso");
		redirecionaDashboard();
	}
	
	public void limpar() {
		Integer id = getUsuario().getId();
		
		Usuario usuario = new Usuario();
		usuario.setId(id);
		
		setUsuario(usuario);
	}
	
	public void pesquisar() {
		SuporteSession.getInstance().set("Pesquisa", getPesquisa());
		redirecionaDashboard();
	}
	
	public boolean validaCampos() {
		boolean retorno = true;
		
		if (getUsuario().getNome() == null || getUsuario().getNome().isBlank()) {
			Util.addMessageWarn("Insira um nome valido");
			retorno = false;
		}
		
		if (getUsuario().getNomeUsuario() == null || getUsuario().getNomeUsuario().isBlank()) {
			Util.addMessageWarn("Insira um nome de usuario valido");
			retorno = false;
		}
		
		if (getUsuario().getSenha() == null || getUsuario().getSenha().isBlank()) {
			Util.addMessageWarn("Insira uma senha valida");
			retorno = false;
		}
		
		if (getUsuario().getEmail() == null || getUsuario().getEmail().isBlank()) {
			Util.addMessageWarn("Insira um email valido");
			retorno = false;
		}
		
		if (getUsuario().getCpf() == null || !Util.isCPF(getUsuario().getCpf())) {
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

	public Usuario getUsuario() {
		if (usuario == null)
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
