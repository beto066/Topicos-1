package br.unitins.skateshop.application;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.apache.commons.codec.digest.DigestUtils;

import br.unitins.skateshop.dao.VendaDao;
import br.unitins.skateshop.model.ItemVenda;
import br.unitins.skateshop.model.TipoPagamento;
import br.unitins.skateshop.model.Usuario;
import br.unitins.skateshop.model.Venda;

public class Util {	
	public static String hash(String valor) {
		return DigestUtils.sha256Hex(valor);
	}
	
//	public static String hash(Usuario usuario) {
//		return DigestUtils.sha256Hex(usuario.getLogin()+usuario.getSenha());
//	}

	public static void addMessageInfo(String message) {
		addMessage(message, FacesMessage.SEVERITY_INFO);
	}

	public static void addMessageWarn(String message) {
		addMessage(message, FacesMessage.SEVERITY_WARN);
	}

	public static void addMessageError(String message) {
		addMessage(message, FacesMessage.SEVERITY_ERROR);
	}

	private static void addMessage(String message, Severity severity) {
		FacesMessage fm = new FacesMessage(severity, message, null);
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}
	
	public static boolean isCPF(String CPF) {
		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222")
				|| CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555")
				|| CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888")
				|| CPF.equals("99999999999") || (CPF.length() != 11))
			return (false);

		char dig10, dig11;
		int sm, i, r, num, peso;

		// "try" - protege o codigo para eventuais erros de conversao 
		//   	de tipo (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				// converte o i-esimo caractere do CPF em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posicao de '0' na tabela ASCII)
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				// converte no respectivo caractere numerico
				dig10 = (char) (r + 48); 
			
			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			// Verifica se os digitos calculados conferem com os digitos 
			//		informados.
			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}
	
	public static void finalizaPedido(Usuario usu, List<ItemVenda> listaItens, Integer idTipoPagamento) {
		if (usu == null) {
			SuporteSession.getInstance().set("sessionMessageError", "Faça login antes de finalizar a compra");
			SuporteSession.getInstance().set("sessionPage", "/SkateShop/faces/carrinho.xhtml");
			
			Redirecionador.redirecionaLogin();
			
			return;
		}
		
		if(listaItens == null || listaItens.isEmpty()) {
			Util.addMessageError("Adicione um produto antes de finalizar a venda");
			return;
		}
		
		if (idTipoPagamento == null)
			idTipoPagamento = 1;
		
		Venda venda = new Venda();
		venda.setUsuario(usu);
		venda.setDate(LocalDateTime.now());
		venda.setListaItem((ArrayList<ItemVenda>) listaItens);
		venda.setTipo_pagamento(TipoPagamento.valueOf(idTipoPagamento));
		
		VendaDao dao = new VendaDao();
		dao.insert(venda);
		
		Util.addMessageInfo("Compra realizada com sucesso");
		Redirecionador.redirecionaDashboard();
	}
	
	public static void redirect(String page) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
