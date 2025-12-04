package domain;

import java.util.List;
import java.util.ArrayList;

public class GestorRecursivoPelicula {

    // Recolecta títulos en un StringBuilder recursivamente
    public static void recogerTitulosRecursivo(List<Pelicula> peliculas, int i, StringBuilder sb) {
        if (peliculas == null) return;
        if (i >= peliculas.size()) return; // caso base
        Pelicula p = peliculas.get(i);
        if (p != null) {
            sb.append(p.getTitulo());
            sb.append("\n");
        }
        recogerTitulosRecursivo(peliculas, i + 1, sb);
    }

    // Contar películas recursivamente
    public static int contarRecursivo(List<Pelicula> peliculas, int i) {
        if (peliculas == null) return 0;
        if (i >= peliculas.size()) return 0;
        return 1 + contarRecursivo(peliculas, i + 1);
    }

    // versión que devuelva String directamente
    public static String titulosComoString(List<Pelicula> peliculas) {
        StringBuilder sb = new StringBuilder();
        recogerTitulosRecursivo(peliculas, 0, sb);
        return sb.toString();
    }

    // FILTRAR POR GÉNERO (RECURSIVO) 
    public static void filtrarPorGeneroRecursivo(List<Pelicula> origen, int i, String genero, List<Pelicula> resultado) {
        if (origen == null) return;
        if (i >= origen.size()) return;

        Pelicula p = origen.get(i);
        if (p != null && p.getGenero() != null && p.getGenero().equalsIgnoreCase(genero)) {
            resultado.add(p);
        }

        filtrarPorGeneroRecursivo(origen, i + 1, genero, resultado);
    }

    // BUSCAR POR AÑO (RECURSIVO) 
    public static void buscarPorAnioRecursivo(List<Pelicula> origen, int i, int anio, List<Pelicula> resultado) {
        if (origen == null) return;
        if (i >= origen.size()) return;

        Pelicula p = origen.get(i);
        if (p != null && p.getAnio() == anio) {
            resultado.add(p);
        }

        buscarPorAnioRecursivo(origen, i + 1, anio, resultado);
    }

    // CALCULAR DURACIÓN TOTAL (RECURSIVO) 
    public static int duracionTotalRecursiva(List<Pelicula> peliculas, int i) {
        if (peliculas == null) return 0;
        if (i >= peliculas.size()) return 0;

        return peliculas.get(i).getDuracion() + duracionTotalRecursiva(peliculas, i + 1);
    }
}
