package gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import dao.PeliculaDAO;
import domain.Pelicula;

public class TablaPeliculasEntidad extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTable tablaPeliculas;
    private DefaultTableModel modeloTabla;
    private PeliculaDAO peliculaDAO;

    public TablaPeliculasEntidad() {
        peliculaDAO = new PeliculaDAO();

        setTitle("Deusto Cine - Películas");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        cargarPeliculas();

    }

    private void initComponents() {
        String[] columnas = {
                "ID",
                "Título",
                "Año",
                "Duración (min)",
                "Género",
                "Aforo",
                "Imagen"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPeliculas = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tablaPeliculas);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void cargarPeliculas() {
        modeloTabla.setRowCount(0);

        List<Pelicula> peliculas = peliculaDAO.listar();

        for (Pelicula p : peliculas) {
            Object[] fila = {
                    p.getId(),
                    p.getTitulo(),
                    p.getAnio(),
                    p.getDuracion(),
                    p.getGenero(),
                    p.getAforo(),
                    p.getImagen()
            };
            modeloTabla.addRow(fila);
        }
    }

    public JTable getTablaPeliculas() {
        return tablaPeliculas;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public void recargarPeliculas() {
        cargarPeliculas();
    }

}
