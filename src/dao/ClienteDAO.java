package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            // Usamos getNumTelefono porque tu clase Cliente hereda de Usuario
            pstmt.setString(3, cliente.getNumTelefono()); 
            pstmt.setString(4, cliente.getDireccion());
            // Ojo: tu clase Cliente usa 'contrasena' en el constructor, pero 'getContrasenya' suele venir de Usuario. 
            // Asegúrate de usar el getter correcto. Asumo getContrasenya() o getContrasena().
            pstmt.setString(5, cliente.getContrasenya());
            
            // Usamos el getter que devuelve String, tal como lo tienes definido
            pstmt.setString(6, cliente.getFechaNacimiento());

            int filas = pstmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ACTUALIZAR SALDO: Este método conecta tu botón con la base de datos
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
                // AQUÍ LLAMAMOS A TU CONSTRUCTOR ESPECÍFICO
                // Orden: nombre, email, telefono, direccion, contrasenya, fechaNacimiento
                Cliente c = new Cliente(
                    rs.getString("nombre"),
                    rs.getString("email"),
                    rs.getString("telefono"),
                    rs.getString("direccion"),
                    rs.getString("contrasenya"),
                    rs.getString("fechaNacimiento") // Tu constructor lo convertirá a LocalDate
                );
                
                // IMPORTANTE: Leemos el saldo de la BD y lo ponemos en el objeto
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
}