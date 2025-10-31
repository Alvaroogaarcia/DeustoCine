package domain;

public class Pelicula {
    private int id;
    private String titulo;
    private int anio;
    private int duracion; // minutos
    private String genero;
    private int aforo;
    private String imagen; 

    public Pelicula() {}

    public Pelicula(int id, String titulo, int anio, int duracion, String genero, int aforo, String imagen) {
        this.id = id;
        this.titulo = titulo;
        this.anio = anio;
        this.duracion = duracion;
        this.genero = genero;
        this.aforo = aforo;
        this.imagen = imagen;
    }

    public Pelicula(String titulo, int anio, int duracion, String genero, int aforo, String imagen) {
        this(0, titulo, anio, duracion, genero, aforo, imagen);
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public int getAforo() { return aforo; }
    public void setAforo(int aforo) { this.aforo = aforo; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    @Override
    public String toString() {
        return "Pelicula{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", anio=" + anio +
                ", duracion=" + duracion +
                ", genero='" + genero + '\'' +
                ", aforo=" + aforo +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}
