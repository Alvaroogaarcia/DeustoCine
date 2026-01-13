package domain;

public class Sesion {

    private int id;
    private int idEntidad;
    private int idPelicula;
    private String fecha;
    private String hora;
    private String sala;

    public Sesion() {}

    public Sesion(int idEntidad, int idPelicula, String fecha, String hora, String sala) {
        this.idEntidad = idEntidad;
        this.idPelicula = idPelicula;
        this.fecha = fecha;
        this.hora = hora;
        this.sala = sala;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(int idEntidad) {
        this.idEntidad = idEntidad;
    }

    public int getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    @Override
    public String toString() {
        return "Sesion [entidad=" + idEntidad + ", pelicula=" + idPelicula +
               ", fecha=" + fecha + ", hora=" + hora + ", sala=" + sala + "]";
    }
}
