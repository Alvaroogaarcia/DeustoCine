package domain;

public class Entidad extends Usuario {
    private String nif; 

    public Entidad() {
        super(); 
    }

    public Entidad(String nombre, String email, String numTelefono, String direccion, String contraseña, String nif) {
        super(nombre, email, numTelefono, direccion, contraseña);
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
}
