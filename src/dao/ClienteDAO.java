package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;
import domain.Cliente;

public class ClienteDAO {

    // REGISTRAR: No enviamos el saldo explícitamente. La BD pondrá 0.0 por defecto.
    public boolean insertar(Cliente cliente) {
        String sql = "INSERT INTO cliente (nombre, email, telefono, direccion, contrasenya, fechaNacimiento) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getEmail());
            pstmt.setString(3, cliente.getNumTelefono()); 
            pstmt.setString(4, cliente.getDireccion());
            pstmt.setString(5, cliente.getContrasenya());
            pstmt.setString(6, cliente.getFechaNacimiento());

            int filas = pstmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ACTUALIZAR SALDO
    public void actualizarSaldo(Cliente c) {
        String sql = "UPDATE cliente SET saldo = ? WHERE email = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, c.getSaldo());
            pstmt.setString(2, c.getEmail());
            
            int filas = pstmt.executeUpdate();
            if(filas > 0) {
                System.out.println("Saldo actualizado en BD correctamente: " + c.getSaldo());
            } else {
                System.err.println("No se pudo actualizar el saldo (email no encontrado).");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // LOGIN: Recupera el usuario y SU SALDO
    public Cliente buscarPorEmail(String email) {
        String sql = "SELECT * FROM cliente WHERE email = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Cliente c = new Cliente(
                    rs.getString("nombre"),
                    rs.getString("email"),
                    rs.getString("telefono"),
                    rs.getString("direccion"),
                    rs.getString("contrasenya"),
                    rs.getString("fechaNacimiento")
                );
                
                double saldoBD = rs.getDouble("saldo");
                c.setSaldo(saldoBD);
                
                return c;
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    
    public List<String> obtenerPeliculasDeCliente(String emailCliente) {
        List<String> listaPeliculas = new ArrayList<>();
        
        String sql = "SELECT p.titulo " +
                     "FROM pelicula p " +
                     "JOIN compra c ON p.id = c.id_pelicula " +
                     "WHERE c.email_cliente = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, emailCliente);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                // Añadimos el título de la película a la lista
                listaPeliculas.add(rs.getString("titulo"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener películas del cliente: " + e.getMessage());
            e.printStackTrace();
        }
        
        return listaPeliculas;
    }
}