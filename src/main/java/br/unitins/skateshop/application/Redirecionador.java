package br.unitins.skateshop.application;

public class Redirecionador {
	public static void redirecionaDashboard() {
		Util.redirect("/SkateShop/faces/index.xhtml");
	}

	public static void redirecionaLogin() {
		Util.redirect("/SkateShop/faces/deslogado/login.xhtml");
	}

	public static void redirecionaCadastroUsuario() {
		Util.redirect("/SkateShop/faces/deslogado/cadastroUsuario.xhtml");
	}

	public static void redirecionaCadastroProduto() {
		Util.redirect("/SkateShop/faces/adm/novoProduto.xhtml");
	}

	public static void redirecionaVendas() {
		Util.redirect("/SkateShop/faces/adm/vendas.xhtml");
	}

	public static void redirecionaCarrinho() {
		Util.redirect("/SkateShop/faces/carrinho.xhtml");
	}

	public static void redirecionaHistorico() {
		Util.redirect("/SkateShop/faces/logado/historicoVendas.xhtml");
	}
	
	public static void redirecionaPerfil() {
		Util.redirect("/SkateShop/faces/logado/perfil.xhtml");
	}

	public static void redirecionaFormUsuario() {
		Util.redirect("/SkateShop/faces/logado/formUsuarioAdm.xhtml");
	}
	
	public static void redirecionaConsultaUsuario() {
		Util.redirect("/SkateShop/faces/adm/consultaUsuario.xhtml");
	}

	public static void redirecionaConsultaProduto() {
		Util.redirect("/SkateShop/faces/adm/consultaProduto.xhtml");
	}
	
	public static void redirecionaFinalizacao() {
		Util.redirect("/SkateShop/faces/logado/finalizacaoVenda.xhtml");
	}
}
