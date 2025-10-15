package domain;

public class Cliente extends Usuario {
    private String fechaNacimiento; // Puedes usar String o LocalDate

    public Cliente() {
        super();
    }

    public Cliente(String nombre, String email, String numTelefono, String direccion, String contrasenya, String fechaNacimiento) {
        super(nombre, email, numTelefono, direccion, contrasenya);
        this.fechaNacimiento = fechaNacimiento; // 🔑 asignar el valor
    }

    // Getter
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    // Setter
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return super.toString() + ", fechaNacimiento=" + fechaNacimiento;
    }
    
    public float aplicarDescuento(Descuento descuento, float precioOriginal) {
        if(descuento != null && descuento.isActivo()) {
            float precioFinal = precioOriginal * (1 - descuento.getPorcentaje() / 100);
            descuento.setActivo(false); // se marca como usado
            System.out.println("Descuento aplicado: " + descuento.getPorcentaje() + "% -> Precio final: " + precioFinal);
            return precioFinal;
        } else {
            System.out.println("Descuento no válido o ya usado.");
            return precioOriginal;
        }
    }
}
