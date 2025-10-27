package domain;

public class Sesion {
    private int id;
    private int idPelicula;
    private String fecha;  
    private String hora;   
    private String sala;

    public Sesion() {}

    public Sesion(int idPelicula, String fecha, String hora, String sala) {
        this.idPelicula = idPelicula;
        this.fecha = fecha;
        this.hora = hora;
        this.sala = sala;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdPelicula() { return idPelicula; }
    public void setIdPelicula(int idPelicula) { this.idPelicula = idPelicula; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getSala() { return sala; }
    public void setSala(String sala) { this.sala = sala; }

    @Override
    public String toString() {
        return "Sesión [película=" + idPelicula + ", fecha=" + fecha + ", hora=" + hora + ", sala=" + sala + "]";
    }
}
