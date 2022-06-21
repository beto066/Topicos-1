package br.unitins.skateshop.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.skateshop.model.DetalhesUsuario;
import br.unitins.skateshop.model.NivelAdm;
import br.unitins.skateshop.model.Produto;
import br.unitins.skateshop.model.Usuario;

public class UsuarioDao implements DAO<Usuario> {
	@Override
	public boolean insert(Usuario usu) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return false;
		}
		
		boolean resultado = true;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO usuarios ( ");
		sql.append("  nome, ");
		sql.append("  nome_usuario, ");
		sql.append("  senha, ");
		sql.append("  email, ");
		sql.append("  cpf, ");
		sql.append("  contato, ");
		sql.append("  nivel_administrador ");
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
			stat.setString(1, usu.getNome());
			stat.setString(2, usu.getNomeUsuario());
			stat.setString(3, usu.getSenha());
			stat.setString(4, usu.getEmail());
			stat.setString(5, usu.getCpf());
			stat.setString(6, usu.getContato());
			stat.setInt(7, usu.getNivelAdm().getId());
			
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
	public boolean update(Usuario usu) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return false;
		}

		boolean resultado = true;

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE usuarios SET  ");
		sql.append("  nome = ?, ");
		sql.append("  nome_usuario = ?, ");
		sql.append("  senha = ?, ");
		sql.append("  email = ?, ");
		sql.append("  cpf = ?, ");
		sql.append("  contato = ?, ");
		sql.append("  nivel_administrador = ? ");
		sql.append("WHERE ");
		sql.append("  id_usuario = ?  ");

		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, usu.getNome());
			stat.setString(2, usu.getNomeUsuario());
			stat.setString(3, usu.getSenha());
			stat.setString(4, usu.getEmail());
			stat.setString(5, usu.getCpf());
			stat.setString(6, usu.getContato());
			stat.setInt(7, usu.getNivelAdm().getId());
			stat.setInt(8, usu.getId());

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
		sql.append("DELETE FROM usuarios ");
		sql.append("WHERE ");
		sql.append("  id_usuario = ?  ");

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
	public List<Usuario> getAll() {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}

		List<Usuario> lista = new ArrayList<Usuario>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  u.id_usuario, ");
		sql.append("  u.nome, ");
		sql.append("  u.nome_usuario, ");
		sql.append("  u.senha, ");
		sql.append("  u.email, ");
		sql.append("  u.cpf, ");
		sql.append("  contato, ");
		sql.append("  nivel_administrador ");
		sql.append("FROM ");
		sql.append("  usuarios u ");
		sql.append("ORDER BY ");
		sql.append("  u.nome ");

		ResultSet rs = null;

		try {
			rs = conn.createStatement().executeQuery(sql.toString());
			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("id_usuario"));
				usuario.setNome(rs.getString("nome"));
				usuario.setNomeUsuario(rs.getString("nome_usuario"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setEmail(rs.getString("email"));
				usuario.setCpf(rs.getString("cpf"));
				usuario.setContato(rs.getString("contato"));
				usuario.setNivelAdm(NivelAdm.valueOf(rs.getInt("nivel_administrador")));
				

				lista.add(usuario);
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
	
	public Usuario getById(Integer id) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}

		Usuario usuario = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  u.id_usuario, ");
		sql.append("  u.nome, ");
		sql.append("  u.nome_usuario, ");
		sql.append("  u.senha, ");
		sql.append("  u.email, ");
		sql.append("  u.cpf, ");
		sql.append("  contato, ");
		sql.append("  nivel_administrador ");
		sql.append("FROM ");
		sql.append("  usuarios u ");
		sql.append("WHERE ");
		sql.append("  u.id_usuario = ? ");

		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			rs = stat.executeQuery();
			if (rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getInt("id_usuario"));
				usuario.setNome(rs.getString("nome"));
				usuario.setNomeUsuario(rs.getString("nome_usuario"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setEmail(rs.getString("email"));
				usuario.setCpf(rs.getString("cpf"));
				usuario.setContato(rs.getString("contato"));
				usuario.setNivelAdm(NivelAdm.valueOf(rs.getInt("nivel_administrador")));
				
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
		return usuario;
	}
	
	public Usuario verificarLogin(Usuario usu, String hash) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}

		Usuario usuario = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  u.id_usuario, ");
		sql.append("  u.nome, ");
		sql.append("  u.nome_usuario, ");
		sql.append("  u.senha, ");
		sql.append("  u.email, ");
		sql.append("  u.cpf, ");
		sql.append("  contato, ");
		sql.append("  nivel_administrador ");
		sql.append("FROM ");
		sql.append("  usuarios u ");
		sql.append("WHERE ");
		sql.append("  u.nome_usuario = ? AND ");
		sql.append("  u.senha = ? ");

		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, usu.getNomeUsuario());
			stat.setString(2, hash);
			
			rs = stat.executeQuery();
			if (rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getInt("id_usuario"));
				usuario.setNome(rs.getString("nome"));
				usuario.setNomeUsuario(rs.getString("nome_usuario"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setEmail(rs.getString("email"));
				usuario.setCpf(rs.getString("cpf"));
				usuario.setContato(rs.getString("contato"));
				usuario.setNivelAdm(NivelAdm.valueOf(rs.getInt("nivel_administrador")));
				
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
		return usuario;
	}

	public List<Usuario> search(String alvo) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}

		List<Usuario> lista = new ArrayList<Usuario>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  u.id_usuario, ");
		sql.append("  u.nome, ");
		sql.append("  u.nome_usuario, ");
		sql.append("  u.senha, ");
		sql.append("  u.email, ");
		sql.append("  u.cpf, ");
		sql.append("  contato, ");
		sql.append("  nivel_administrador ");
		sql.append("FROM ");
		sql.append("  usuarios u ");
		sql.append("WHERE ");
		sql.append("  u.nome LIKE ? ");
		sql.append("ORDER BY ");
		sql.append("  u.nome ");

		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, "%" + alvo + "%");
			
			rs = stat.executeQuery();
			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("id_usuario"));
				usuario.setNome(rs.getString("nome"));
				usuario.setNomeUsuario(rs.getString("nome_usuario"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setEmail(rs.getString("email"));
				usuario.setCpf(rs.getString("cpf"));
				usuario.setContato(rs.getString("contato"));
				usuario.setNivelAdm(NivelAdm.valueOf(rs.getInt("nivel_administrador")));
				

				lista.add(usuario);
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
	
	public DetalhesUsuario getDetalhes(Usuario usu) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}
		
		DetalhesUsuario detalhes = new DetalhesUsuario();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  COUNT(v.id_venda) AS quant_compras, ");
		sql.append("  SUM(i.quantidade) AS quant_produtos, ");
		sql.append("  SUM((i.quantidade * i.valor)) AS total_gasto ");
		sql.append("FROM ");
		sql.append("  usuarios u ");
		sql.append("LEFT JOIN ");
		sql.append("  vendas v ON ");
		sql.append("  v.id_usuario = u.id_usuario ");
		sql.append("LEFT JOIN ");
		sql.append("  itens_venda i ON ");
		sql.append("  i.id_venda = v.id_venda ");
		sql.append("LEFT JOIN ");
		sql.append("  produtos p ON ");
		sql.append("  p.id_produto = i.id_produto ");
		sql.append("WHERE ");
		sql.append("  u.id_usuario = ? ");
		sql.append("GROUP BY ");
		sql.append("  u.id_usuario ");
		
		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, usu.getId());
			
			rs = stat.executeQuery();
			if (rs.next()) {
				detalhes.setQuantidadeCompras(rs.getInt("quant_compras"));
				detalhes.setQuantProdutosComprados(rs.getInt("quant_produtos"));
				detalhes.setValorTotalGasto(rs.getFloat("total_gasto"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			detalhes = null;
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
		
		return detalhes;
	}
}
