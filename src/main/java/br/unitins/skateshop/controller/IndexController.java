package br.unitins.skateshop.controller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.websocket.Session;

import br.unitins.skateshop.application.Redirecionador;
import br.unitins.skateshop.application.Redirect;
import br.unitins.skateshop.application.SuporteFlash;
import br.unitins.skateshop.application.SuporteSession;
import br.unitins.skateshop.application.Util;
import br.unitins.skateshop.dao.ProdutoDao;
import br.unitins.skateshop.dao.VendaDao;
import br.unitins.skateshop.model.ItemVenda;
import br.unitins.skateshop.model.Produto;
import br.unitins.skateshop.model.TipoPagamento;
import br.unitins.skateshop.model.Usuario;
import br.unitins.skateshop.model.Venda;

/**
 * Controlador do tamplate e da tela principal onde mostra os produtos.
 * @author 55989
 */
@Named
@ViewScoped
public class IndexController implements Redirect, Serializable {
	private static final long serialVersionUID = -4322700289109036113L; 
	private String pesquisa;
	private List<Produto> listaProduto;
	private Usuario usuarioLogado;
	
	public IndexController() {
		String message = (String) SuporteSession.getInstance().get("sessionMessageError");
		Util.addMessageError(message);
		SuporteSession.getInstance().remove("sessionMessageError");
		
		pesquisa = pegaPesquisa();
		
		if (pesquisa != null && !pesquisa.isBlank())
			pesquisar();
	}

	public void comprar(Produto produto) {
		List<ItemVenda> carrinho = (List<ItemVenda>) SuporteSession.getInstance().get("carrinho");
		
		if(carrinho == null)
			carrinho = new ArrayList<ItemVenda>();
		
		ItemVenda item = new ItemVenda();
		item.setProduto(produto);
		item.setValor(produto.getValor());
		item.setQuantidade(1);
		
		int index = carrinho.indexOf(item);
		
		if(index >= 0) {
			int quantidade = carrinho.get(index).getQuantidade();
			carrinho.get(index).setQuantidade(quantidade + 1);
			carrinho.get(index).setValor(item.getValor());
		} else {
			carrinho.add(item);
		}
		
		SuporteSession.getInstance().set("carrinho", carrinho);
		
		Util.addMessageInfo("Item adcionado no carrinho com sucesso!!!");
	}
	
	public void pesquisar() {		
		ProdutoDao dao = new ProdutoDao();
		List<Produto> listaPesquisa = new ArrayList<Produto>();
		
		listaPesquisa = dao.search(getPesquisa());
		
		if (listaPesquisa == null || listaPesquisa.size() < 1) {
			Util.addMessageError("Erro ao tentar pesquisar!!!");
			return;
		}
		
		listaProduto = listaPesquisa;
		Util.addMessageInfo("Pesquisa realizada com sucesso!!!");
	}
	
	public void editar(int id) {
		ProdutoDao dao = new ProdutoDao();
		Produto produto = dao.getById(id);
		
		SuporteFlash.getInstance().set("flashProduto", produto);
		
		listaProduto = null;
		redirecionaCadastroProduto();
	}
	
	public void excluir(Integer id) {
		ProdutoDao dao = new ProdutoDao();
		
		if (!dao.delete(id)) {
			Util.addMessageWarn("Erro ao tentar deletar o produto");
			return;
		}
		
		Util.addMessageInfo("Produto deletado com sucesso");
		listaProduto = null;
	}
	
	public void sair() {
		SuporteSession.getInstance().invalidateSession();
		redirecionaLogin();
	}
	
	public void finalizarCompra() {
		List<ItemVenda> carrinho = (List<ItemVenda>) SuporteSession.getInstance().get("carrinho");
		
		if (carrinho == null || carrinho.isEmpty()) {
			Util.addMessageError("Adcione um item antes de finalizar a compra");
			return;
		}
		
		if (usuarioLogado == null) {
			SuporteSession.getInstance().set("sessionMessageError", "Fa??a login antes de finalizar a compra");
			redirecionaLogin();
		}
			
		redirecionaFinalizacao();
	}
	
	private String pegaPesquisa() {
		try {
			String sessionPesquisa = (String) SuporteSession.getInstance().get("Pesquisa");
			SuporteSession.getInstance().remove("Pesquisa");
			return sessionPesquisa;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
		if (usuarioLogado == null) 
			usuarioLogado = (Usuario) SuporteSession.getInstance().get("UsuarioLogado");
			
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public List<Produto> getListaProduto() {
		if (listaProduto == null) {
			ProdutoDao dao = new ProdutoDao();
			listaProduto = dao.getAll();
		}
		
		return listaProduto;
	}
	
	public void setListaProduto(List<Produto> listaProduto) {		
		this.listaProduto = listaProduto;
	}

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}
}
