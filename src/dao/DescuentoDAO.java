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

}
