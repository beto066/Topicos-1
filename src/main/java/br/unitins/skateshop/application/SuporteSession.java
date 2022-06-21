package br.unitins.skateshop.application;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class SuporteSession {
	private static SuporteSession session = null;
	
	private SuporteSession() {
		// TODO Auto-generated constructor stub
	}
	
	public static SuporteSession getInstance() {
		if (session == null) 
			session = new SuporteSession();
		return session;
	}
	
	private ExternalContext getExternalContext() {
		if (FacesContext.getCurrentInstance() == null)
			throw new RuntimeException("Opa, vc não esta utilizando um servior web");
		return FacesContext.getCurrentInstance().getExternalContext();
	}
	
	public void set(String key, Object obj) {
		getExternalContext().getSessionMap().put(key, obj);
	}
	
	public Object get(String key) {
		return getExternalContext().getSessionMap().get(key);
	}
	
	public void remove(String key) {
		getExternalContext().getSessionMap().remove(key);
	}
	
	public void invalidateSession() {
		getExternalContext().invalidateSession();
	}
}
