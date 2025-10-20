package domain;

public class DescuentoPelicula {
    private String id;
    private String codigo;
    private double porcentaje; // Porcentaje de descuento (ej: 10.0 = 10%)

    public DescuentoPelicula(String id, String codigo, double porcentaje) {
        this.id = id;
        this.codigo = codigo;
        this.porcentaje = porcentaje;
    }

    public String getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    @Override
    public String toString() {
        return "Descuento{" +
                "id='" + id + '\'' +
                ", codigo='" + codigo + '\'' +
                ", porcentaje=" + porcentaje +
                '}';
    }
}
