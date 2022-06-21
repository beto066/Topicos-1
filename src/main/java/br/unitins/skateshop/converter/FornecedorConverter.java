package br.unitins.skateshop.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.unitins.skateshop.dao.FornecedorDao;
import br.unitins.skateshop.model.Fornecedor;

@FacesConverter(forClass = Fornecedor.class)
public class FornecedorConverter implements Converter<Fornecedor> {

	@Override
	public Fornecedor getAsObject(FacesContext context, UIComponent component, String id) {
		if (id == null || id.isBlank())
			return null;
		
		FornecedorDao dao = new FornecedorDao();
		return dao.getById(Integer.valueOf(id));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Fornecedor fornecedor) {
		if (fornecedor == null || fornecedor.getId() == null)
			return null;
		
		return fornecedor.getId().toString();
	}

}
