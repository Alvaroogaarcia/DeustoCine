package dao;

import database.DBConnection;
import domain.Cliente;
import domain.Entidad;
import domain.Usuario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Comprueba si existe un usuario con el email dado
    private boolean existeEmail(String email) {
        String sql = "SELECT COUNT(1) AS cnt FROM usuario WHERE email = ?";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("cnt") > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertar(Usuario usuario) {
        if (usuario == null || usuario.getEmail() == null) {
            throw new IllegalArgumentException("Usuario o email nulo");
        }

        if (existeEmail(usuario.getEmail())) {
            System.err.println("⚠️ El email ya existe: " + usuario.getEmail());
            return false; // no insertado
        }
        
        boolean isCliente = usuario instanceof Cliente;
        
        if (isCliente) {
        	return insertarCliente( (Cliente)usuario);
        }
        
        boolean isEntidad = usuario instanceof Entidad;
        
        if (isEntidad) {
        	return insertarEntidad( (Entidad)usuario);
        }
        
//        if (isTrabajador) {
//        	return insertarTrabajador( (Trabajdor)usuario);
//        }
        
        return false;
        
    }
    
    
    public boolean insertarCliente (Cliente cliente) {
    	
    	String sql = "INSERT INTO usuario(nombre, email, numTelefono, direccion, contrasenya, fechaNacimiento) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, cliente.getNombre());
                ps.setString(2, cliente.getEmail());
                ps.setString(3, cliente.getNumTelefono());
                ps.setString(4, cliente.getDireccion());
                ps.setString(5, cliente.getContrasenya());
                ps.setString(6, cliente.getFechaNacimiento());
                ps.executeUpdate();
                System.out.println("Usuario insertado correctamente: " + cliente.getNombre());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error insertando usuario:");
            e.printStackTrace();
            return false;
        }
    	
    }
    
    public boolean insertarEntidad (Entidad entidad) {
    	
    	String sql = "INSERT INTO usuario(nombre, email, numTelefono, direccion, contrasenya, nif) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, entidad.getNombre());
                ps.setString(2, entidad.getEmail());
                ps.setString(3, entidad.getNumTelefono());
                ps.setString(4, entidad.getDireccion());
                ps.setString(5, entidad.getContrasenya());
                ps.setString(6, entidad.getNif());
                ps.executeUpdate();
                System.out.println("Usuario insertado correctamente: " + entidad.getNombre());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error insertando usuario:");
            e.printStackTrace();
            return false;
        }
    	
    }
    
 

    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT nombre, email, numTelefono, direccion, contrasenya, nif, fechaNacimiento FROM usuario";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                	Usuario u = null;
                	
                	String nif = rs.getString("nif");
                	if (nif == null || nif == "") {
                		u = new Entidad(nif);
                	}else {
                		u = new Cliente(LocalDate.parse(rs.getString("fechaNacimiento")));
                	}
                	
                	
                    u.setNombre(rs.getString("nombre"));
                    u.setEmail(rs.getString("email"));
                    u.setNumTelefono(rs.getString("numTelefono"));
                    u.setDireccion(rs.getString("direccion"));
                    u.setContrasenya(rs.getString("contrasenya"));
                    lista.add(u);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT nombre, email, numTelefono, direccion, contrasenya, nif, fechaNacimiento  FROM usuario WHERE email = ?";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                    	Usuario u = null;
                    	
                    	String nif = rs.getString("nif");
                    	if (nif == null || nif == "") {
                    		u = new Cliente(LocalDate.parse(rs.getString("fechaNacimiento")));
                    	}else {
                    		u = new Entidad(nif);
                    	}
                    	
                    	
                        u.setNombre(rs.getString("nombre"));
                        u.setEmail(rs.getString("email"));
                        u.setNumTelefono(rs.getString("numTelefono"));
                        u.setDireccion(rs.getString("direccion"));
                        u.setContrasenya(rs.getString("contrasenya"));
                        return u;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean eliminarPorEmail(String email) {
        String sql = "DELETE FROM usuario WHERE email = ?";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, email);
                int rows = ps.executeUpdate();
                return rows > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
