package dao;

import database.DBConnection;
import domain.DescuentoPelicula;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DescuentoDAO {
	
	public boolean existePorId(String id) {
		String sql = "SELECT COUNT(*) FROM descuento WHERE id= ?";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setString(1, id);
			try (ResultSet rs = ps.executeQuery()){
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean insertar(DescuentoPelicula d) {
	
		String sql = "INSERT INTO descuento (codigo, porcentaje) VALUES (?, ?)";
		try (Connection conn= DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)){
			
			ps.setString(1, d.getCodigo());
			ps.setDouble(2, d.getPorcentaje());
			
			ps.executeUpdate();
			return true;
						
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	
	
	}
	// Busca un descuento por su código (lo usa la ruleta de PagoEntrada)
	public DescuentoPelicula buscarPorCodigo(String codigo) {
	    String sql = "SELECT id, codigo, porcentaje FROM descuento WHERE codigo = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, codigo);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                int id = rs.getInt("id");
	                String cod = rs.getString("codigo");
	                double porcentaje = rs.getDouble("porcentaje");

	                return new DescuentoPelicula(id, cod, porcentaje);
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return null;
	}

}
