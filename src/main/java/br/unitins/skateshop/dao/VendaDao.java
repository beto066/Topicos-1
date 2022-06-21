package br.unitins.skateshop.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import br.unitins.skateshop.model.Fornecedor;
import br.unitins.skateshop.model.ItemVenda;
import br.unitins.skateshop.model.Nivel;
import br.unitins.skateshop.model.TipoPagamento;
import br.unitins.skateshop.model.Usuario;
import br.unitins.skateshop.model.Venda;

public class VendaDao implements DAO<Venda> {

	@Override
	public boolean insert(Venda venda) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return false;
		}
		
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		boolean resultado = true;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO vendas ( ");
		sql.append("  id_usuario, ");
		sql.append("  data, ");
		sql.append("  desconto, ");
		sql.append("  tipo_pagamento ");
		sql.append(") VALUES ( ");	
		sql.append("  ?,  ");		
		sql.append("  ?,  ");		
		sql.append("  ?,  ");		
		sql.append("  ?  ");
		sql.append(") ");
		
		PreparedStatement stat = null;
		
		try {
			stat = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			stat.setInt(1, venda.getUsuario().getId());
			stat.setTimestamp(2, Timestamp.valueOf(venda.getDate()));
			
			if (venda.getDesconto() == null || venda.getDesconto() == 0)
				stat.setDouble(3, 0);
			else
				stat.setDouble(3, venda.getDesconto());
			stat.setInt(4, venda.getTipo_pagamento().getId());
			
			stat.execute();
			
			// Obtendo o id gerado pelo banco.
			ResultSet rs = stat.getGeneratedKeys();
			if (rs.next()) {
				venda.setId(rs.getInt(1));
			}
			
			salvarItensVenda(venda, conn);
			
			// Salvando no banco (Resolvido)
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
	
	
	private void salvarItensVenda(Venda venda, Connection conn) throws SQLException {
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO itens_venda ( ");
		sql.append("  valor, ");
		sql.append("  quantidade, ");
		sql.append("  id_produto, ");
		sql.append("  id_venda ");
		sql.append(") VALUES ( ");	
		sql.append("  ?,  ");		
		sql.append("  ?,  ");		
		sql.append("  ?,  ");		
		sql.append("  ?  ");
		sql.append(") ");
		
		PreparedStatement stat = null;
		for (ItemVenda itemVenda : venda.getListaItem()) {
			stat = conn.prepareStatement(sql.toString());
			stat.setDouble(1, itemVenda.getValor());
			stat.setInt(2, itemVenda.getQuantidade());
			stat.setInt(3, itemVenda.getProduto().getId());
			stat.setDouble(4, venda.getId());
			stat.execute();
		}
	}

	@Override
	public boolean update(Venda venda) {
		return false;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}
	
	public List<Venda> getByUsuario(Usuario usu) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}

		List<Venda> lista = new ArrayList<Venda>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  v.id_venda, ");
		sql.append("  v.data, ");
		sql.append("  v.tipo_pagamento, ");
		sql.append("  SUM(i.valor) AS total_venda ");
		sql.append("FROM ");
		sql.append("  vendas v ");
		sql.append("LEFT JOIN ");
		sql.append("  itens_venda i ");
		sql.append("ON ");
		sql.append("  i.id_venda = v.id_venda ");
		sql.append("WHERE ");
		sql.append("  v.id_usuario = ? ");
		sql.append("GROUP BY ");
		sql.append("  v.id_venda ");
		sql.append("ORDER BY ");
		sql.append("  v.data DESC ");

		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, usu.getId());
			
			rs = stat.executeQuery();
			while (rs.next()) {
				Venda venda = new Venda();
				venda.setId(rs.getInt("id_venda"));
				venda.setDate((rs.getTimestamp("data").toLocalDateTime()));
				venda.setTipo_pagamento(TipoPagamento.valueOf(rs.getInt("tipo_pagamento")));
				venda.setTotalVenda(rs.getFloat("total_venda"));
				venda.setUsuario(usu);
				
				lista.add(venda);
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
	
	/**
	 * @author 55989
	 * @param venda <p>
	 * A venda que vai ser buscada
	 * </p>
	 * @return <p>
	 * Restorna a propia venda com os itens.
	 * </p>
	 */
	public Venda getByVenda(Venda venda) {
		Connection conn = DAO.getConnection();
		if (conn == null) 
			return null;

		venda.setListaItem(new ArrayList<ItemVenda>());
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  i.id_item_venda, ");
		sql.append("  i.id_produto, ");
		sql.append("  i.valor, ");
		sql.append("  i.quantidade ");
		sql.append("FROM ");
		sql.append("  itens_venda i ");
		sql.append("WHERE ");
		sql.append(" i.id_venda = ? ");

		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, venda.getId());

			rs = stat.executeQuery();
			ProdutoDao dao = new ProdutoDao();
			while (rs.next()) {
				ItemVenda item = new ItemVenda();
				item.setId(rs.getInt("id_item_venda"));
				item.setValor(rs.getFloat("valor"));
				item.setQuantidade(rs.getInt("quantidade"));
				item.setProduto(dao.getById(rs.getInt("id_produto")));
				
				venda.getListaItem().add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			venda.setListaItem(new ArrayList<ItemVenda>());
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
		return venda;
	}
	
	@Override
	public List<Venda> getAll() {
//		Connection conn = DAO.getConnection();
//		if (conn == null) {
//			return null;
//		}
//
//		List<Venda> lista = new ArrayList<Venda>();
//
//		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT ");
//		sql.append("  p.id_venda, ");
//		sql.append("  p.id_fornecedor, ");
//		sql.append("  p.valor, ");
//		sql.append("  p.estoque, ");
//		sql.append("  p.nivel, ");
//		sql.append("  p.descricao, ");
//		sql.append("  p.marca, ");
//		sql.append("  p.tipo, ");
//		sql.append("  f.nome, ");
//		sql.append("  f.contato, ");
//		sql.append("  f.email, ");
//		sql.append("  f.endereco, ");
//		sql.append("  f.descricao_contrato ");
//		sql.append("FROM ");
//		sql.append("  vendas p LEFT JOIN ");
//		sql.append("  fornecedores f ON ");
//		sql.append("  p.id_fornecedor = f.id_fornecedor ");
//		sql.append("ORDER BY ");
//		sql.append("  p.descricao ");
//
//		ResultSet rs = null;
//		
//		try {
//			rs = conn.createStatement().executeQuery(sql.toString());
//			while (rs.next()) {
//				Fornecedor fornecedor = new Fornecedor();
//				fornecedor.setId(rs.getInt("id_fornecedor"));
//				fornecedor.setNome(rs.getString("nome"));
//				fornecedor.setContato(rs.getString("contato"));
//				fornecedor.setEmail(rs.getString("email"));
//				fornecedor.setEndereco(rs.getString("endereco"));
//				fornecedor.setDescricaoContrato(rs.getString("descricao_contrato"));
//				
//				Venda venda = new Venda();
//				venda.setId(rs.getInt("id_venda"));
//				venda.setFornecedor(fornecedor);
//				venda.setValor((float) rs.getDouble("valor"));
//				venda.setEstoque(rs.getInt("estoque"));
//				venda.setNivel(Nivel.valueOf(rs.getInt("nivel")));
//				venda.setDescricao(rs.getString("descricao"));
//				venda.setMarca(rs.getString("marca"));
//				venda.setTipo(rs.getString("tipo"));
//				
//
//				lista.add(venda);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			lista = null;
//		}
//
//		try {
//			rs.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			conn.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		return null;
	}


	public List<Venda> searchByUsuario(Usuario usu, LocalDate alvo) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}

		List<Venda> lista = new ArrayList<Venda>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  v.id_venda, ");
		sql.append("  v.data, ");
		sql.append("  v.tipo_pagamento, ");
		sql.append("  SUM(i.valor) AS total_venda ");
		sql.append("FROM ");
		sql.append("  vendas v ");
		sql.append("LEFT JOIN ");
		sql.append("  itens_venda i ");
		sql.append("ON ");
		sql.append("  i.id_venda = v.id_venda ");
		sql.append("WHERE ");
		sql.append("  v.id_usuario = ? AND ");
		sql.append("  v.data BETWEEN ? AND ?");
		sql.append("GROUP BY ");
		sql.append("  v.id_venda ");
		sql.append("ORDER BY ");
		sql.append("  v.data DESC ");

		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, usu.getId());
			stat.setDate(2, Date.valueOf(alvo));
			stat.setDate(3, Date.valueOf(LocalDate.of(alvo.getYear(), alvo.getMonthValue(), alvo.getDayOfMonth() + 1)));
			
			rs = stat.executeQuery();
			while (rs.next()) {
				Venda venda = new Venda();
				venda.setId(rs.getInt("id_venda"));
				venda.setDate((rs.getTimestamp("data").toLocalDateTime()));
				venda.setTipo_pagamento(TipoPagamento.valueOf(rs.getInt("tipo_pagamento")));
				venda.setTotalVenda(rs.getFloat("total_venda"));
				venda.setUsuario(usu);
				
				lista.add(venda);
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


	public List<Venda> searchByData(LocalDate alvo) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}

		List<Venda> lista = new ArrayList<Venda>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  v.id_usuario, ");
		sql.append("  v.id_venda, ");
		sql.append("  v.data, ");
		sql.append("  v.tipo_pagamento, ");
		sql.append("  SUM(i.valor) AS total_venda ");
		sql.append("FROM ");
		sql.append("  vendas v ");
		sql.append("LEFT JOIN ");
		sql.append("  itens_venda i ");
		sql.append("ON ");
		sql.append("  i.id_venda = v.id_venda ");
		sql.append("WHERE ");
		sql.append("  v.data BETWEEN ? AND ?");
		sql.append("GROUP BY ");
		sql.append("  v.id_venda ");
		sql.append("ORDER BY ");
		sql.append("  v.data DESC ");

		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setDate(1, Date.valueOf(alvo));
			stat.setDate(2, Date.valueOf(LocalDate.of(alvo.getYear(), alvo.getMonthValue(), alvo.getDayOfMonth() + 1)));
			
			rs = stat.executeQuery();
			while (rs.next()) {
				Venda venda = new Venda();
				venda.setId(rs.getInt("id_venda"));
				venda.setDate((rs.getTimestamp("data").toLocalDateTime()));
				venda.setTipo_pagamento(TipoPagamento.valueOf(rs.getInt("tipo_pagamento")));
				venda.setTotalVenda(rs.getFloat("total_venda"));
				venda.setUsuario(new UsuarioDao().getById(rs.getInt("id_usuario")));
				
				lista.add(venda);
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

	public List<Venda> searchByNameUsuario(String alvo) {
		Connection conn = DAO.getConnection();
		if (conn == null) {
			return null;
		}

		List<Venda> lista = new ArrayList<Venda>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  v.id_usuario, ");
		sql.append("  v.id_venda, ");
		sql.append("  v.data, ");
		sql.append("  v.tipo_pagamento, ");
		sql.append("  SUM(i.valor) AS total_venda ");
		sql.append("FROM ");
		sql.append("  vendas v ");
		sql.append("LEFT JOIN ");
		sql.append("  itens_venda i ");
		sql.append("ON ");
		sql.append("  i.id_venda = v.id_venda ");
		sql.append("INNER JOIN ");
		sql.append("  usuarios u ");
		sql.append("ON ");
		sql.append("  u.id_usuario = v.id_usuario ");
		sql.append("WHERE ");
		sql.append("  u.nome LIKE ? ");
		sql.append("GROUP BY ");
		sql.append("  v.id_venda ");
		sql.append("ORDER BY ");
		sql.append("  v.data DESC ");

		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, "%" + alvo + "%");
		
			rs = stat.executeQuery();
			while (rs.next()) {
				Venda venda = new Venda();
				venda.setId(rs.getInt("id_venda"));
				venda.setDate((rs.getTimestamp("data").toLocalDateTime()));
				venda.setTipo_pagamento(TipoPagamento.valueOf(rs.getInt("tipo_pagamento")));
				venda.setTotalVenda(rs.getFloat("total_venda"));
				venda.setUsuario(new UsuarioDao().getById(rs.getInt("id_usuario")));
				
				lista.add(venda);
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
}
