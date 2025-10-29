package dao;

import database.DBConnection;
import java.sql.*;


public class CompraDao {
	public Integer reservar(String emailUsuario, int idEntrada) {
		long ahora = System.currentTimeMillis();
		long expira = ahora + 5 * 60 * 1000;
		try (Connection c = DBConnection.getConnection()) {
			try (PreparedStatement ps = c.prepareStatement("SELECT estado FROM entrada WHERE id=?")) {
				ps.setInt(1, idEntrada);
				try (ResultSet rs = ps.executeQuery()) {
					if (!rs.next()) return null; // no existe
						if (!"LIBRE".equals(rs.getString(1))) return null; // ya ocupada
				}
			}
			try (PreparedStatement ps = c.prepareStatement("UPDATE entrada SET estado='RESERVADA' WHERE id=?")) {
				ps.setInt(1, idEntrada);
				ps.executeUpdate();
			}
			try (PreparedStatement ps = c.prepareStatement(
				"INSERT INTO compra(email_usuario, id_entrada, estado, creado_en, expira_en) VALUES (?,?,?,?,?)")) {
					ps.setString(1, emailUsuario);
					ps.setInt(2, idEntrada);
					ps.setString(3, "RESERVADA");
					ps.setLong(4, ahora);
					ps.setLong(5, expira);
					ps.executeUpdate();
			}
		
			try (Statement st = c.createStatement(); ResultSet rs = st.executeQuery("SELECT last_insert_rowid()")) {
				if (rs.next()) return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public boolean pagar(int idCompra) {
		try (Connection c = DBConnection.getConnection()) {
			int filas;
			try (PreparedStatement ps = c.prepareStatement(
			"UPDATE compra SET estado='PAGADA' WHERE id=? AND estado='RESERVADA'")) {
				ps.setInt(1, idCompra);
				filas = ps.executeUpdate();
			}
			if (filas == 0) return false;
			try (PreparedStatement ps = c.prepareStatement(
			"UPDATE entrada SET estado='VENDIDA' WHERE id=(SELECT id_entrada FROM compra WHERE id=?)")) {
				ps.setInt(1, idCompra);
				ps.executeUpdate();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
			
	public int expirarCaducadas() {
		int count = 0;
		long ahora = System.currentTimeMillis();
		try (Connection c = DBConnection.getConnection()) {
			try (PreparedStatement ps = c.prepareStatement(
			"SELECT id, id_entrada FROM compra WHERE estado='RESERVADA' AND expira_en < ?")) {
				ps.setLong(1, ahora);
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						int idCompra = rs.getInt("id");
						int idEntrada = rs.getInt("id_entrada");
			// Marcar compra como EXPIRADA
						try (PreparedStatement u1 = c.prepareStatement("UPDATE compra SET estado='EXPIRADA' WHERE id=?")) {
							u1.setInt(1, idCompra);
							u1.executeUpdate();
						}
			// Liberar la entrada
						try (PreparedStatement u2 = c.prepareStatement("UPDATE entrada SET estado='LIBRE' WHERE id=?")) {
							u2.setInt(1, idEntrada);
							u2.executeUpdate();
						}
						count++;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
}
