package domain;

import java.util.ArrayList;
import java.util.List;

public class Entidad extends Usuario {
    private String nif;
    private List<Sesion> sesiones;
    private List<DescuentoPelicula> descuentos; 
    
    public Entidad(String nif) {
    	this.nif = nif;
    }

    public Entidad(String nombre, String email, String numTelefono, String direccion, String contraseña, String nif) {
        super(nombre, email, numTelefono, direccion, contraseña);
        this.nif = nif;
        this.sesiones = new ArrayList<>();
        this.descuentos = new ArrayList<>();
    }

    // Métodos para sesiones
    public void agregarSesion(Sesion sesion) { sesiones.add(sesion); }
    public List<Sesion> getSesiones() { return sesiones; }

    // Métodos para descuentos
    public void agregarDescuento(DescuentoPelicula descuento) { descuentos.add(descuento); }
    public List<DescuentoPelicula> getDescuentos() { return descuentos; }

    public String getNif() { return nif; }
    public void setNif(String nif) { this.nif = nif; }
}
