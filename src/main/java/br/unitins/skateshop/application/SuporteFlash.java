package br.unitins.skateshop.application;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import br.unitins.skateshop.model.Produto;
import br.unitins.skateshop.model.Usuario;

public class SuporteFlash {
	private static SuporteFlash flash;
	
	private SuporteFlash() {
	}
	
	public static SuporteFlash getInstance() {
		if (flash == null) 
			flash = new SuporteFlash();
		return flash;
	}
	
	private ExternalContext getExternalContext() {
		if (FacesContext.getCurrentInstance() == null)
			throw new RuntimeException("Opa, vc não esta utilizando um servior web");
		return FacesContext.getCurrentInstance().getExternalContext();
	}
	
	public void set(String key, Object obj) {
		getExternalContext().getFlash().put(key, obj);
	}
	
	public Object get(String key) {
		return getExternalContext().getFlash().get(key);
	}
	
	public Object keep(String key) {
		getExternalContext().getFlash().keep(key);
		return get(key);
	}
	
	public Object remove(String key) {
		return getExternalContext().getFlash().put(key, null);
	}
	
	public void invalidateSession() {
		getExternalContext().invalidateSession();
	}
}
