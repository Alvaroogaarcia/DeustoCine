package dao;

import database.DBConnection;
import domain.DescuentoPelicula;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	
    // Se basa en la primera letra del código
    public DescuentoPelicula obtenerDescuentoAleatorioPorGenero(String genero) {
        char letra = obtenerLetraGenero(genero);
        String patron = letra + "%";

        String sql = "SELECT id, codigo, porcentaje FROM descuento WHERE codigo LIKE ?";
        List<DescuentoPelicula> lista = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, patron);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String cod = rs.getString("codigo");
                    double porcentaje = rs.getDouble("porcentaje");
                    lista.add(new DescuentoPelicula(id, cod, porcentaje));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (lista.isEmpty()) {
            return null; // no hay descuentos para ese género
        }

        Random random = new Random();
        return lista.get(random.nextInt(lista.size()));
    }

    // Misma lógica de letras que usáis al generar códigos en gui.Descuento
    private char obtenerLetraGenero(String genero) {
        if (genero == null) return 'X';

        switch (genero.toLowerCase()) {
            case "accion":          return 'A';
            case "comedia":         return 'C';
            case "drama":           return 'D';
            case "terror":          return 'T';
            case "ciencia ficcion": return 'S';
            case "romance":         return 'R';
            case "animacion":       return 'N';
            case "thriller":        return 'H';
            default:                return 'X';
        }
    }

}
