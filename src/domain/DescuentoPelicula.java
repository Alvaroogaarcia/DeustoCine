package domain;

public class DescuentoPelicula {

    private int id;            
    private String codigo;    
    private double porcentaje;

    public DescuentoPelicula(String codigo, double porcentaje) {
        this.codigo = codigo;
        this.porcentaje = porcentaje;
    }

    public DescuentoPelicula(int id, String codigo, double porcentaje) {
        this.id = id;
        this.codigo = codigo;
        this.porcentaje = porcentaje;
    }

    public int getId() { return id; }
    public String getCodigo() { return codigo; }
    public double getPorcentaje() { return porcentaje; }

    public void setId(int id) { this.id = id; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setPorcentaje(double porcentaje) { this.porcentaje = porcentaje; }

    @Override
    public String toString() {
        return "Descuento {id=" + id +
                ", codigo='" + codigo + '\'' +
                ", porcentaje=" + porcentaje +
                '}';
    }
}
