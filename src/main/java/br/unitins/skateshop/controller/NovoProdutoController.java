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
import br.unitins.skateshop.dao.FornecedorDao;
import br.unitins.skateshop.dao.ProdutoDao;
import br.unitins.skateshop.dao.VendaDao;
import br.unitins.skateshop.model.Produto;
import br.unitins.skateshop.model.TipoPagamento;
import br.unitins.skateshop.model.Usuario;
import br.unitins.skateshop.model.Venda;
import br.unitins.skateshop.model.Fornecedor;
import br.unitins.skateshop.model.ItemVenda;
import br.unitins.skateshop.model.Nivel;

@Named
@ViewScoped
public class NovoProdutoController implements Serializable, Redirect {
	private static final long serialVersionUID = -1041108461524838211L;
	private Produto produto;
	private List<Fornecedor> listaFornecedor;
	private Usuario usuarioLogado;
	private String pesquisa;
	
	public NovoProdutoController() {
		try {
			produto = (Produto) SuporteFlash.getInstance().get("flashProduto");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void incluir() {
		if (!validaCampos())
			return;
		
		ProdutoDao dao = new ProdutoDao();

		if (!dao.insert(getProduto())) {
			Util.addMessageWarn("Erro ao tentar cadaastrar o produto");
			limpar();
			return;
		}
		
		Util.addMessageInfo("produto cadastrado com sucesso");
		limpar();
	}
	
	public void alterar() {
		if (!validaCampos())
			return;
		
		ProdutoDao dao = new ProdutoDao();

		if (!dao.update(getProduto())) {
			Util.addMessageWarn("Erro ao tentar atualizar o produto");
			return;
		}
		
		Util.addMessageInfo("Produto atualizado com sucesso");
		redirecionaDashboard();
	}
	
	public void excluir() {
		ProdutoDao dao = new ProdutoDao();
		
		if (!dao.delete(getProduto().getId())) {
			Util.addMessageWarn("Erro ao tentar deletar o produto");
			return;
		}
		
		Util.addMessageInfo("Produto deletado com sucesso");
		redirecionaDashboard();
	}
	
	public void limpar() {
		Integer id = getProduto().getId();
		
		Produto produto = new Produto();
		produto.setId(id);
		
		setProduto(produto);
	}
	
	public void pesquisar() {
		SuporteSession.getInstance().set("Pesquisa", getPesquisa());
		redirecionaDashboard();
	}
	
	public void sair() {
		SuporteSession.getInstance().invalidateSession();
		redirecionaLogin();
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

	public boolean validaCampos() {
		boolean retorno = true;
		
		if (getProduto().getValor() == null || getProduto().getValor() < 0) {
			Util.addMessageWarn("Insira um valor valido");
			retorno = false;
		}
		
		if (getProduto().getEstoque() == null || getProduto().getEstoque() < 0) {
			Util.addMessageWarn("Insira uma quantidade do estoque valida");
			retorno = false;
		}
		
		if (getProduto().getDescricao() == null || getProduto().getDescricao().isBlank()) {
			Util.addMessageWarn("Insira uma descrição valida");
			retorno = false;
		}
		
		if (getProduto().getTipo() == null || getProduto().getTipo().isBlank()) {
			Util.addMessageWarn("Insira um tipo valido");
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

	public Produto getProduto() {
		if (produto == null)
			produto = new Produto();
		
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public Nivel[] getListaNivel() {
		return Nivel.values();
	}

	public List<Fornecedor> getListaFornecedor() {
		if (listaFornecedor == null) {
			FornecedorDao dao = new FornecedorDao();
			listaFornecedor = dao.getAll();
			if (listaFornecedor == null) 
				listaFornecedor = new ArrayList<Fornecedor>();
		}
		
		return listaFornecedor;
	}

	public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}

	public Usuario getUsuarioLogado() {
		if(usuarioLogado == null) {
			usuarioLogado = (Usuario) SuporteSession.getInstance().get("UsuarioLogado");
			
			if (usuarioLogado == null || usuarioLogado.getNivelAdm().getId() < 2)
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
