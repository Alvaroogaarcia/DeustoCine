package domain;

import java.util.ArrayList;
import java.util.List;

public class Entidad extends Usuario {
    private String nif; 
    private List<Descuento> descuentos = new ArrayList<>();

    public Entidad() {
        super(); 
    }

    public Entidad(String nombre, String email, String numTelefono, String direccion, String contrasenya, String nif) {
        super(nombre, email, numTelefono, direccion, contrasenya);
        this.nif = nif; 
    }

    // Getter
    public String getNif() {
        return nif;
    }

    // Setter
    public void setNif(String nif) {
        this.nif = nif;
    }

    @Override
    public String toString() {
        return super.toString() + ", nif=" + nif;
    }
    
    public void crearDescuento(int id, float porcentaje, String codigo, String descripcion) {
        Descuento nuevo = new Descuento(id, porcentaje, codigo, descripcion);
        descuentos.add(nuevo);
        System.out.println("Descuento creado: " + nuevo);
    }
    
    //Para saber si el descuento esta activo
    public List<Descuento> listarDescuentos() {
        List<Descuento> activos = new ArrayList<>();
        for (Descuento d : descuentos) {
            if (d.isActivo()) {
                activos.add(d);
            }
        }
        return activos;
    }
}
