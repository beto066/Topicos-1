package br.unitins.skateshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.skateshop.model.Fornecedor;
import br.unitins.skateshop.model.Usuario;

public class FornecedorDao implements DAO<Fornecedor> {

	@Override
	public boolean insert(Fornecedor obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Fornecedor obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Fornecedor> getAll() {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}

		List<Fornecedor> lista = new ArrayList<Fornecedor>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  f.id_fornecedor, ");
		sql.append("  f.nome, ");
		sql.append("  f.contato, ");
		sql.append("  f.email, ");
		sql.append("  f.endereco, ");
		sql.append("  f.descricao_contrato ");
		sql.append("FROM ");
		sql.append("  fornecedores f ");
		sql.append("ORDER BY ");
		sql.append("  f.nome ");

		ResultSet rs = null;

		try {
			rs = conn.createStatement().executeQuery(sql.toString());
			while (rs.next()) {
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setId(rs.getInt("id_Fornecedor"));
				fornecedor.setNome(rs.getString("nome"));
				fornecedor.setContato(rs.getString("contato"));
				fornecedor.setEmail(rs.getString("email"));
				fornecedor.setEndereco(rs.getString("endereco"));
				fornecedor.setDescricaoContrato(rs.getString("descricao_contrato"));				

				lista.add(fornecedor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			lista = null;
		}

		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public Fornecedor getById(Integer id) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}

		Fornecedor fornecedor = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  f.id_fornecedor, ");
		sql.append("  f.nome, ");
		sql.append("  f.contato, ");
		sql.append("  f.email, ");
		sql.append("  f.endereco, ");
		sql.append("  f.descricao_contrato ");
		sql.append("FROM ");
		sql.append("  fornecedores f ");
		sql.append("WHERE ");
		sql.append("  f.id_fornecedor = ? ");
		sql.append("ORDER BY ");
		sql.append("  f.nome ");

		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			rs = stat.executeQuery();
			if (rs.next()) {
				fornecedor = new Fornecedor();
				fornecedor.setId(rs.getInt("id_Fornecedor"));
				fornecedor.setNome(rs.getString("nome"));
				fornecedor.setContato(rs.getString("contato"));
				fornecedor.setEmail(rs.getString("email"));
				fornecedor.setEndereco(rs.getString("endereco"));
				fornecedor.setDescricaoContrato(rs.getString("descricao_contrato"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fornecedor;
	}
}
