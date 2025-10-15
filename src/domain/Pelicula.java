package domain;

public class Pelicula {
    private int id;
    private String titulo;
    private int anio;
    private int duracion; // minutos
    private String genero;
    private String sinopsis;

    // Constructor vacío
    public Pelicula() {}

    // Constructor con todos los campos (incluyendo id)
    public Pelicula(int id, String titulo, int anio, int duracion, String genero, String sinopsis) {
        this.id = id;
        this.titulo = titulo;
        this.anio = anio;
        this.duracion = duracion;
        this.genero = genero;
        this.sinopsis = sinopsis;
    }

    // Constructor sin id (para insertar nuevos registros)
    public Pelicula(String titulo, int anio, int duracion, String genero, String sinopsis) {
        this(0, titulo, anio, duracion, genero, sinopsis);
    }

    // Constructor corto sin sinopsis (comodidad)
    public Pelicula(String titulo, int anio, int duracion, String genero) {
        this(titulo, anio, duracion, genero, "");
    }

    // Getters y setters
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

    public String getSinopsis() { return sinopsis; }
    public void setSinopsis(String sinopsis) { this.sinopsis = sinopsis; }

    @Override
    public String toString() {
        return "Pelicula{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", anio=" + anio +
                ", duracion=" + duracion +
                ", genero='" + genero + '\'' +
                (sinopsis != null && !sinopsis.isEmpty() ? ", sinopsis='" + sinopsis + '\'' : "") +
                '}';
    }
}

