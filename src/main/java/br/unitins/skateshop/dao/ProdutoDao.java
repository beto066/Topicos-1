package br.unitins.skateshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.skateshop.model.Fornecedor;
import br.unitins.skateshop.model.Nivel;
import br.unitins.skateshop.model.Produto;

public class ProdutoDao implements DAO<Produto> {

	@Override
	public boolean insert(Produto produto) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return false;
		}
		
		boolean resultado = true;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO produtos ( ");
		sql.append("  id_fornecedor, ");
		sql.append("  valor, ");
		sql.append("  estoque, ");
		sql.append("  nivel, ");
		sql.append("  descricao, ");
		sql.append("  marca, ");
		sql.append("  tipo ");
		sql.append(" ) VALUES ( ");
		sql.append("  ?,  ");
		sql.append("  ?,  ");
		sql.append("  ?,  ");		
		sql.append("  ?,  ");		
		sql.append("  ?,  ");		
		sql.append("  ?,  ");		
		sql.append("  ?  ");
		sql.append(") ");
		
		PreparedStatement stat = null;
		
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, produto.getFornecedor().getId());
			stat.setDouble(2, produto.getValor());
			stat.setInt(3, produto.getEstoque());
			stat.setInt(4, produto.getNivel().getId());
			stat.setString(5, produto.getDescricao());
			stat.setString(6, produto.getMarca());			
			stat.setString(7, produto.getTipo());
			
			stat.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			
			resultado = false;
		}
		
		try {
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultado;
	}

	@Override
	public boolean update(Produto produto) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return false;
		}

		boolean resultado = true;

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE produtos SET  ");
		sql.append("  id_produto = ?, ");
		sql.append("  valor = ?, ");
		sql.append("  estoque = ?, ");
		sql.append("  nivel = ?, ");
		sql.append("  descricao = ?, ");
		sql.append("  marca = ?, ");
		sql.append("  tipo = ? ");
		sql.append("WHERE ");
		sql.append("  id_produto = ?  ");

		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, produto.getFornecedor().getId());
			stat.setDouble(2, produto.getValor());
			stat.setInt(3, produto.getEstoque());
			stat.setInt(4, produto.getNivel().getId());
			stat.setString(5, produto.getDescricao());
			stat.setString(6, produto.getMarca());			
			stat.setString(7, produto.getTipo());
			stat.setInt(8, produto.getId());

			stat.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			resultado = false;
		}

		try {
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	@Override
	public boolean delete(int id) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return false;
		}

		boolean resultado = true;

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM produtos ");
		sql.append("WHERE ");
		sql.append("  id_produto = ?  ");

		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);

			stat.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			resultado = false;
		}

		try {
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	@Override
	public List<Produto> getAll() {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}

		List<Produto> lista = new ArrayList<Produto>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  p.id_produto, ");
		sql.append("  p.id_fornecedor, ");
		sql.append("  p.valor, ");
		sql.append("  p.estoque, ");
		sql.append("  p.nivel, ");
		sql.append("  p.descricao, ");
		sql.append("  p.marca, ");
		sql.append("  p.tipo, ");
		sql.append("  f.nome, ");
		sql.append("  f.contato, ");
		sql.append("  f.email, ");
		sql.append("  f.endereco, ");
		sql.append("  f.descricao_contrato ");
		sql.append("FROM ");
		sql.append("  produtos p LEFT JOIN ");
		sql.append("  fornecedores f ON ");
		sql.append("  p.id_fornecedor = f.id_fornecedor ");
		sql.append("ORDER BY ");
		sql.append("  p.descricao ");

		ResultSet rs = null;
		
		try {
			rs = conn.createStatement().executeQuery(sql.toString());
			while (rs.next()) {
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setId(rs.getInt("id_fornecedor"));
				fornecedor.setNome(rs.getString("nome"));
				fornecedor.setContato(rs.getString("contato"));
				fornecedor.setEmail(rs.getString("email"));
				fornecedor.setEndereco(rs.getString("endereco"));
				fornecedor.setDescricaoContrato(rs.getString("descricao_contrato"));
				
				Produto produto = new Produto();
				produto.setId(rs.getInt("id_produto"));
				produto.setFornecedor(fornecedor);
				produto.setValor((float) rs.getDouble("valor"));
				produto.setEstoque(rs.getInt("estoque"));
				produto.setNivel(Nivel.valueOf(rs.getInt("nivel")));
				produto.setDescricao(rs.getString("descricao"));
				produto.setMarca(rs.getString("marca"));
				produto.setTipo(rs.getString("tipo"));
				

				lista.add(produto);
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
	
	public Produto getById(Integer id) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}

		Produto produto = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  p.id_produto, ");
		sql.append("  p.id_fornecedor, ");
		sql.append("  p.valor, ");
		sql.append("  p.estoque, ");
		sql.append("  p.nivel, ");
		sql.append("  p.descricao, ");
		sql.append("  p.marca, ");
		sql.append("  p.tipo, ");
		sql.append("  f.nome, ");
		sql.append("  f.contato, ");
		sql.append("  f.email, ");
		sql.append("  f.endereco, ");
		sql.append("  f.descricao_contrato ");
		sql.append("FROM ");
		sql.append("  produtos p LEFT JOIN ");
		sql.append("  fornecedores f ON ");
		sql.append("  p.id_fornecedor = f.id_fornecedor ");
		sql.append("WHERE ");
		sql.append("  p.id_produto = ? ");

		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			rs = stat.executeQuery();
			if (rs.next()) {
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setId(rs.getInt("id_fornecedor"));
				fornecedor.setNome(rs.getString("nome"));
				fornecedor.setContato(rs.getString("contato"));
				fornecedor.setEmail(rs.getString("email"));
				fornecedor.setEndereco(rs.getString("endereco"));
				fornecedor.setDescricaoContrato(rs.getString("descricao_contrato"));
				
				produto = new Produto();
				produto.setId(rs.getInt("id_produto"));
				produto.setFornecedor(fornecedor);
				produto.setValor((float) rs.getDouble("valor"));
				produto.setEstoque(rs.getInt("estoque"));
				produto.setNivel(Nivel.valueOf(rs.getInt("nivel")));
				produto.setDescricao(rs.getString("descricao"));
				produto.setMarca(rs.getString("marca"));
				produto.setTipo(rs.getString("tipo"));
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
		return produto;
	}
	
	public List<Produto> search(String alvo) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}

		List<Produto> lista = new ArrayList<Produto>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  p.id_produto, ");
		sql.append("  p.id_fornecedor, ");
		sql.append("  p.valor, ");
		sql.append("  p.estoque, ");
		sql.append("  p.nivel, ");
		sql.append("  p.descricao, ");
		sql.append("  p.marca, ");
		sql.append("  p.tipo, ");
		sql.append("  f.nome, ");
		sql.append("  f.contato, ");
		sql.append("  f.email, ");
		sql.append("  f.endereco, ");
		sql.append("  f.descricao_contrato ");
		sql.append("FROM ");
		sql.append("  produtos p LEFT JOIN ");
		sql.append("  fornecedores f ON ");
		sql.append("  p.id_fornecedor = f.id_fornecedor ");
		sql.append("WHERE ");
		sql.append("  p.descricao LIKE ? ");		

		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, "%" + alvo + "%");
			
			rs = stat.executeQuery();
			while (rs.next()) {
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setId(rs.getInt("id_fornecedor"));
				fornecedor.setNome(rs.getString("nome"));
				fornecedor.setContato(rs.getString("contato"));
				fornecedor.setEmail(rs.getString("email"));
				fornecedor.setEndereco(rs.getString("endereco"));
				fornecedor.setDescricaoContrato(rs.getString("descricao_contrato"));
				
				Produto produto = new Produto();
				
				produto = new Produto();
				produto.setId(rs.getInt("id_produto"));
				produto.setFornecedor(fornecedor);
				produto.setValor((float) rs.getDouble("valor"));
				produto.setEstoque(rs.getInt("estoque"));
				produto.setNivel(Nivel.valueOf(rs.getInt("nivel")));
				produto.setDescricao(rs.getString("descricao"));
				produto.setMarca(rs.getString("marca"));
				produto.setTipo(rs.getString("tipo"));
				
				lista.add(produto);
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
		return lista;
	}
}
