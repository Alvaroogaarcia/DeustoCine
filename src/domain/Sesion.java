package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Sesion {
    private String idSesion;
    private LocalDate fecha;
    private String sala;
    private List<Pelicula> peliculas;

    public Sesion(String idSesion, LocalDate fecha, String sala) {
        this.idSesion = idSesion;
        this.fecha = fecha;
        this.sala = sala;
        this.peliculas = new ArrayList<>();
    }

    public String getIdSesion() {
        return idSesion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getSala() {
        return sala;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void agregarPelicula(Pelicula pelicula) {
        peliculas.add(pelicula);
    }

    @Override
    public String toString() {
        return "Sesión [" + fecha + " - " + sala + " - " + peliculas.size() + " películas]";
    }
}
