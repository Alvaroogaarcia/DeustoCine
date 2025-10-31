package domain;

public abstract class Usuario {
    private String nombre;
    private String email;
    private String numTelefono;
    private String direccion;
    private String contrasenya;

    public Usuario() {
    }

    public Usuario(String nombre, String email, String numTelefono, String direccion, String contrasenya) {
        this.nombre = nombre;
        this.email = email;
        this.numTelefono = numTelefono;
        this.direccion = direccion;
        this.contrasenya = contrasenya;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNumTelefono() { return numTelefono; }
    public void setNumTelefono(String numTelefono) { this.numTelefono = numTelefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getContrasenya() { return contrasenya; }
    public void setContrasenya(String contrasenya) { this.contrasenya = contrasenya; }

    @Override
    public String toString() {
        return "Usuario [nombre=" + nombre + ", email=" + email + ", numTelefono=" + numTelefono +
                ", direccion=" + direccion + ", contrasenya=" + contrasenya + "]";
    }
}
