package domain;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase que representa una valoración de película
 * Almacena la puntuación y comentario de un usuario sobre una película
 */
public class Valoracion {
    
    private int id;
    private String emailUsuario;
    private int idPelicula;
    private int puntuacion; // De 1 a 5
    private String comentario;
    private long fecha; // Timestamp en milisegundos
    
    /**
     * Constructor completo
     */
    public Valoracion(String emailUsuario, int idPelicula, int puntuacion, String comentario, long fecha) {
        this.emailUsuario = emailUsuario;
        this.idPelicula = idPelicula;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.fecha = fecha;
    }
    
    /**
     * Constructor sin fecha (usa fecha actual)
     */
    public Valoracion(String emailUsuario, int idPelicula, int puntuacion, String comentario) {
        this(emailUsuario, idPelicula, puntuacion, comentario, System.currentTimeMillis());
    }
    
    // Getters
    public int getId() { 
        return id; 
    }
    
    public String getEmailUsuario() { 
        return emailUsuario; 
    }
    
    public int getIdPelicula() { 
        return idPelicula; 
    }
    
    public int getPuntuacion() { 
        return puntuacion; 
    }
    
    public String getComentario() { 
        return comentario; 
    }
    
    public long getFecha() { 
        return fecha; 
    }
    
    // Setters
    public void setId(int id) { 
        this.id = id; 
    }
    
    public void setEmailUsuario(String emailUsuario) { 
        this.emailUsuario = emailUsuario; 
    }
    
    public void setIdPelicula(int idPelicula) { 
        this.idPelicula = idPelicula; 
    }
    
    public void setPuntuacion(int puntuacion) { 
        if (puntuacion >= 1 && puntuacion <= 5) {
            this.puntuacion = puntuacion;
        }
    }
    
    public void setComentario(String comentario) { 
        this.comentario = comentario; 
    }
    
    public void setFecha(long fecha) { 
        this.fecha = fecha; 
    }
    
    /**
     * Obtiene la fecha formateada
     */
    public String getFechaFormateada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(new Date(fecha));
    }
    
    /**
     * Calcula hace cuánto tiempo se hizo la valoración
     */
    public String getTiempoTranscurrido() {
        long diff = System.currentTimeMillis() - fecha;
        long segundos = diff / 1000;
        long minutos = segundos / 60;
        long horas = minutos / 60;
        long dias = horas / 24;
        
        if (dias > 0) {
            return dias + (dias == 1 ? " día" : " días");
        } else if (horas > 0) {
            return horas + (horas == 1 ? " hora" : " horas");
        } else if (minutos > 0) {
            return minutos + (minutos == 1 ? " minuto" : " minutos");
        } else {
            return "Recién";
        }
    }
    
    /**
     * Obtiene las estrellas visuales
     */
    public String getEstrellasVisuales() {
        return "*".repeat(puntuacion);
    }
    
    /**
     * Obtiene el nombre del usuario (parte antes del @)
     */
    public String getNombreUsuario() {
        if (emailUsuario != null && emailUsuario.contains("@")) {
            return emailUsuario.split("@")[0];
        }
        return emailUsuario;
    }
    
    @Override
    public String toString() {
        return "Valoracion{" +
               "usuario='" + emailUsuario + '\'' +
               ", pelicula=" + idPelicula +
               ", puntuacion=" + puntuacion +
               ", comentario='" + comentario + '\'' +
               ", fecha=" + getFechaFormateada() +
               '}';
    }
}