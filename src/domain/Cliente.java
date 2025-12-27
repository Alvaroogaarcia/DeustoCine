package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {
    private LocalDate fechaNacimiento; 
    private Double saldo = 0.0;

    // Lista de IDs de películas compradas
    private List<Integer> idPeliculasCompradas;

    // Constructores
    public Cliente(LocalDate fechaNacimiento) {
        super();
        this.fechaNacimiento = fechaNacimiento;
        this.idPeliculasCompradas = new ArrayList<>();
    }

    public Cliente(String nombre, String email, String numTelefono, String direccion, String contrasena, String fechaNacimiento) {
        super(nombre, email, numTelefono, direccion, contrasena);
        this.fechaNacimiento = LocalDate.parse(fechaNacimiento); 
        this.idPeliculasCompradas = new ArrayList<>();
    }
    
    // Saldo
    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    // Fecha de nacimiento
    public String getFechaNacimiento() {
        return fechaNacimiento.toString();
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = LocalDate.parse(fechaNacimiento);
    }

    // Películas compradas
    public List<Integer> getIdPeliculasCompradas() {
        return idPeliculasCompradas;
    }

    public void setIdPeliculasCompradas(List<Integer> idPeliculasCompradas) {
        this.idPeliculasCompradas = idPeliculasCompradas;
    }

    public void agregarCompra(int idPelicula) {
        if (idPeliculasCompradas == null) {
            idPeliculasCompradas = new ArrayList<>();
        }
        idPeliculasCompradas.add(idPelicula);
    }

    @Override
    public String toString() {
        return super.toString() + ", fechaNacimiento=" + fechaNacimiento + ", peliculasCompradas=" + idPeliculasCompradas;
    }

    // Método para aplicar descuento
    public float aplicarDescuento(DescuentoPelicula descuento, float precioOriginal) {
        if (descuento != null) {
            float precioFinal = precioOriginal * (1 - (float) descuento.getPorcentaje() / 100);
            System.out.println("Descuento aplicado: " + descuento.getPorcentaje() + "% -> Precio final: " + precioFinal);
            return precioFinal;
        } else {
            System.out.println("Descuento no válido.");
            return precioOriginal;
        }
    }
}
