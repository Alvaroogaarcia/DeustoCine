package domain;

import java.time.LocalDate;

public class Cliente extends Usuario {
    private LocalDate fechaNacimiento; 

    public Cliente(LocalDate fechaNacimiento) {
        super();
        this.fechaNacimiento = fechaNacimiento;
    }

    public Cliente(String nombre, String email, String numTelefono, String direccion, String contrasena, String fechaNacimiento) {
        super(nombre, email, numTelefono, direccion, contrasena);
        this.fechaNacimiento = LocalDate.parse(fechaNacimiento); 
    }

    // Getter
    public String getFechaNacimiento() {
        return fechaNacimiento.toString();
    }

    // Setter
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = LocalDate.parse(fechaNacimiento);
    }

    @Override
    public String toString() {
        return super.toString() + ", fechaNacimiento=" + fechaNacimiento;
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
