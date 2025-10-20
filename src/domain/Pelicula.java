package domain;

public class Pelicula {
    private int id;
    private String titulo;
    private int anio;
    private int duracion; // en minutos
    private String genero;
    private int aforo;

    // Constructor vacío
    public Pelicula() {}

    // Constructor con todos los campos
    public Pelicula(int id, String titulo, int anio, int duracion, String genero, int aforo) {
        this.id = id;
        this.titulo = titulo;
        this.anio = anio;
        this.duracion = duracion;
        this.genero = genero;
        this.aforo = aforo;
    }

    // Constructor sin ID (por ejemplo, antes de asignarlo)
    public Pelicula(String titulo, int anio, int duracion, String genero, int aforo) {
        this(-1, titulo, anio, duracion, genero, aforo);
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

    public int getAforo() { return aforo; }
    public void setAforo(int aforo) { this.aforo = aforo; }

    @Override
    public String toString() {
        return "Pelicula{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", anio=" + anio +
                ", duracion=" + duracion +
                ", genero='" + genero + '\'' +
                ", aforo=" + aforo +
                '}';
    }
}
