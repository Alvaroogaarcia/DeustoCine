package gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.PeliculaDAO;
import dao.SesionDAO;
import domain.Cliente;
import domain.Sesion;

public class CompraEntradas extends JFrame {

    private static final String RUTA_IMAGENES = "resources/data/imagenes/";
    
    private JTextField txtBusqueda;
    private JButton btnBuscar;
    private JButton btnFiltro;

    private JPanel panelCartelera;
    private JScrollPane scrollCartelera;

    private SesionDAO sesionDAO;
    private PeliculaDAO peliculaDAO;
    private Cliente cliente;
    
    public CompraEntradas(Cliente cliente) {
        sesionDAO = new SesionDAO();
        peliculaDAO = new PeliculaDAO();
        
        setTitle("DeustoCine - Compra de entradas");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        this.cliente = cliente;
    
        JPanel panelSuperior = new JPanel(new BorderLayout(5, 5));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        txtBusqueda = new JTextField();
        panelSuperior.add(txtBusqueda, BorderLayout.CENTER);
    
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        btnFiltro = new JButton("Filtro");
        btnBuscar = new JButton("Buscar");
        panelBotones.add(btnFiltro);
        panelBotones.add(btnBuscar);
    
        panelSuperior.add(panelBotones, BorderLayout.EAST);
        add(panelSuperior, BorderLayout.NORTH);
    
        panelCartelera = new JPanel(new GridLayout(0, 3, 20, 20));
        panelCartelera.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        scrollCartelera = new JScrollPane(panelCartelera);
        add(scrollCartelera, BorderLayout.CENTER);
    
        btnBuscar.addActionListener(e -> cargarSesiones(txtBusqueda.getText().trim()));
        btnFiltro.addActionListener(e -> {
            String genero = JOptionPane.showInputDialog(this, "Introduce un genero:");
            if (genero != null && !genero.trim().isEmpty()) {
                cargarSesionesPorGenero(genero.trim());
            }
        });
    
        cargarSesiones("");
    }

    private void cargarSesiones(String textoTitulo) {
        panelCartelera.removeAll();
        List<Sesion> listaSesiones = sesionDAO.listar();
        for (Sesion s : listaSesiones) {
            domain.Pelicula p = peliculaDAO.buscarPorId(s.getIdPelicula());
            if (p == null) continue;
            if (textoTitulo != null && !textoTitulo.isBlank() &&
                !p.getTitulo().toLowerCase().contains(textoTitulo.toLowerCase())) continue;
            panelCartelera.add(crearPanelSesion(s, p));
        }
        panelCartelera.revalidate();
        panelCartelera.repaint();
    }

    private void cargarSesionesPorGenero(String genero) {
        panelCartelera.removeAll();
        List<Sesion> listaSesiones = sesionDAO.listar();
        for (Sesion s : listaSesiones) {
            domain.Pelicula p = peliculaDAO.buscarPorId(s.getIdPelicula());
            if (p != null && genero.equalsIgnoreCase(p.getGenero())) {
                panelCartelera.add(crearPanelSesion(s, p));
            }
        }
        panelCartelera.revalidate();
        panelCartelera.repaint();
    }

    private JPanel crearPanelSesion(Sesion sesion, domain.Pelicula pelicula) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY, 1, true));
        panel.setBackground(java.awt.Color.WHITE);

        JLabel lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon icono = cargarImagen(pelicula.getImagen(), 200, 260);
        if (icono != null) lblImagen.setIcon(icono);
        else lblImagen.setText("Sin imagen");
        panel.add(lblImagen, BorderLayout.CENTER);

        String textoHtml = "<html><center><b>" + pelicula.getTitulo() + "</b><br>"
                + sesion.getFecha() + " - " + sesion.getHora() + "<br>"
                + "Sala: " + sesion.getSala() + "</center></html>";
        panel.add(new JLabel(textoHtml, SwingConstants.CENTER), BorderLayout.SOUTH);

        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                mostrarVentanaDetalle(sesion, pelicula);
            }
        });

        return panel;
    }

    private ImageIcon cargarImagen(String nombreImagen, int ancho, int alto) {
        if (nombreImagen == null || nombreImagen.isBlank()) return null;
        ImageIcon iconoOriginal = new ImageIcon(RUTA_IMAGENES + nombreImagen);
        if (iconoOriginal.getIconWidth() <= 0) return null;
        Image imgEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imgEscalada);
    }

    private void mostrarVentanaDetalle(Sesion sesion, domain.Pelicula pelicula) {
        JDialog dialogo = new JDialog(this, "Detalles de la sesion", true);
        dialogo.setSize(600, 400);
        dialogo.setLocationRelativeTo(this);
        dialogo.setLayout(new BorderLayout(10, 10));

        JPanel panelContenido = new JPanel(new BorderLayout(10, 10));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon icono = cargarImagen(pelicula.getImagen(), 180, 240);
        if (icono != null) lblImagen.setIcon(icono);
        else lblImagen.setText("Sin imagen");
        panelContenido.add(lblImagen, BorderLayout.WEST);

        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<h2>").append(pelicula.getTitulo()).append("</h2>");
        sb.append("<b>Año:</b> ").append(pelicula.getAnio()).append("<br>");
        sb.append("<b>Duracion:</b> ").append(pelicula.getDuracion()).append(" min<br>");
        sb.append("<b>Genero:</b> ").append(pelicula.getGenero()).append("<br>");
        sb.append("<b>Aforo:</b> ").append(pelicula.getAforo()).append("<br><br>");
        sb.append("<b>Fecha sesion:</b> ").append(sesion.getFecha()).append("<br>");
        sb.append("<b>Hora:</b> ").append(sesion.getHora()).append("<br>");
        sb.append("<b>Sala:</b> ").append(sesion.getSala()).append("<br>");
        sb.append("</html>");
        panelContenido.add(new JLabel(sb.toString()), BorderLayout.CENTER);

        dialogo.add(panelContenido, BorderLayout.CENTER);

        JPanel panelBoton = new JPanel();
        JButton btnComprar = new JButton("Comprar entrada");
        btnComprar.setPreferredSize(new Dimension(160, 35));
        btnComprar.addActionListener(e -> {
            dialogo.dispose();
            PagoEntrada ventanaPago = new PagoEntrada(cliente, pelicula.getId(), pelicula.getGenero());
            ventanaPago.setVisible(true);
        });

        panelBoton.add(btnComprar);
        dialogo.add(panelBoton, BorderLayout.SOUTH);

        dialogo.setVisible(true);
    }
}
