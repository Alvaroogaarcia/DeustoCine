package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.PeliculaDAO;
import dao.SesionDAO;
import dao.ValoracionDAO;
import domain.Cliente;
import domain.Sesion;
import domain.Valoracion;

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

        ValoracionDAO valoracionDAO = new ValoracionDAO();
        double promedio = valoracionDAO.obtenerPromedioValoracion(pelicula.getId());
        int totalReseñas = valoracionDAO.contarValoraciones(pelicula.getId());
        
        String valoracionTexto = totalReseñas > 0 ? 
            String.format("⭐ %.1f (%d)", promedio, totalReseñas) : "Sin valoraciones";
        // ================================================================

        String textoHtml = "<html><center><b>" + pelicula.getTitulo() + "</b><br>"
                + valoracionTexto + "<br>"  // ← NUEVA LÍNEA
                + sesion.getFecha() + " - " + sesion.getHora() + "<br>"
                + "Sala: " + sesion.getSala() + "</center></html>";
        panel.add(new JLabel(textoHtml, SwingConstants.CENTER), BorderLayout.SOUTH);

        JButton btnReseñas = new JButton("📝 Reseñas");
        btnReseñas.setBackground(new Color(52, 152, 219));
        btnReseñas.setForeground(Color.WHITE);
        btnReseñas.setFocusPainted(false);
        btnReseñas.addActionListener(e -> mostrarReseñas(pelicula));
        panel.add(btnReseñas, BorderLayout.NORTH);


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
        
        if (nombreImagen.contains("\\") || nombreImagen.contains("/")) {
            String[] partes = nombreImagen.split("[/\\\\]");
            nombreImagen = partes[partes.length - 1];
        }
        
        File archivo = new File(RUTA_IMAGENES + nombreImagen);
        if (!archivo.exists()) return null;
        
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


    private void mostrarReseñas(domain.Pelicula pelicula) {
        ValoracionDAO dao = new ValoracionDAO();
        JDialog dialogo = new JDialog(this, "Reseñas - " + pelicula.getTitulo(), true);
        dialogo.setSize(500, 450);
        dialogo.setLocationRelativeTo(this);
        dialogo.setLayout(new BorderLayout());
        
        JPanel panelTop = new JPanel();
        panelTop.setBackground(new Color(52, 73, 94));
        double promedio = dao.obtenerPromedioValoracion(pelicula.getId());
        int total = dao.contarValoraciones(pelicula.getId());
        JLabel lblPromedio = new JLabel(String.format("⭐ %.1f / 5.0 (%d reseñas)", promedio, total));
        lblPromedio.setFont(new Font("Arial", Font.BOLD, 18));
        lblPromedio.setForeground(Color.YELLOW);
        panelTop.add(lblPromedio);
        dialogo.add(panelTop, BorderLayout.NORTH);
        
        JPanel panelLista = new JPanel();
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));
        List<Valoracion> reseñas = dao.listarPorPelicula(pelicula.getId());
        
        if (reseñas.isEmpty()) {
            JLabel lblVacio = new JLabel("No hay reseñas aún");
            lblVacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelLista.add(Box.createVerticalStrut(50));
            panelLista.add(lblVacio);
        } else {
            for (Valoracion v : reseñas) {
                JPanel tarjeta = new JPanel(new BorderLayout(5, 5));
                tarjeta.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                tarjeta.setMaximumSize(new Dimension(450, 80));
                
                JLabel lblUsuario = new JLabel(v.getNombreUsuario() + " - " + v.getEstrellasVisuales());
                lblUsuario.setFont(new Font("Arial", Font.BOLD, 12));
                
                JTextArea txtComentario = new JTextArea(v.getComentario());
                txtComentario.setEditable(false);
                txtComentario.setLineWrap(true);
                txtComentario.setWrapStyleWord(true);
                
                tarjeta.add(lblUsuario, BorderLayout.NORTH);
                tarjeta.add(txtComentario, BorderLayout.CENTER);
                
                panelLista.add(tarjeta);
                panelLista.add(Box.createVerticalStrut(5));
            }
        }
        
        dialogo.add(new JScrollPane(panelLista), BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel();
        JButton btnValorar = new JButton("Escribir Reseña");
        btnValorar.addActionListener(e -> {
            dialogo.dispose();
            abrirVentanaValorar(pelicula);
        });
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dialogo.dispose());
        panelBotones.add(btnValorar);
        panelBotones.add(btnCerrar);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        
        dialogo.setVisible(true);
    }

    private void abrirVentanaValorar(domain.Pelicula pelicula) {
        JDialog dialogo = new JDialog(this, "Valorar - " + pelicula.getTitulo(), true);
        dialogo.setSize(400, 300);
        dialogo.setLocationRelativeTo(this);
        dialogo.setLayout(new BorderLayout());
        
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblEstrellas = new JLabel("Puntuación:");
        JSlider slider = new JSlider(1, 5, 3);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        
        JLabel lblEstrellasVisual = new JLabel("*****");
        lblEstrellasVisual.setFont(new Font("Arial", Font.PLAIN, 24));
        
        slider.addChangeListener(e -> {
            int val = slider.getValue();
            lblEstrellasVisual.setText("*".repeat(val));
        });
        
        JLabel lblComentario = new JLabel("Comentario:");
        JTextArea txtComentario = new JTextArea(5, 20);
        txtComentario.setLineWrap(true);
        txtComentario.setWrapStyleWord(true);
        txtComentario.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        panelCentral.add(lblEstrellas);
        panelCentral.add(slider);
        panelCentral.add(lblEstrellasVisual);
        panelCentral.add(Box.createVerticalStrut(10));
        panelCentral.add(lblComentario);
        panelCentral.add(new JScrollPane(txtComentario));
        
        dialogo.add(panelCentral, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton btnEnviar = new JButton("Enviar");
        btnEnviar.addActionListener(e -> {
            String comentario = txtComentario.getText().trim();
            
            if (comentario.isEmpty() || comentario.length() < 10) {
                JOptionPane.showMessageDialog(dialogo, "El comentario debe tener al menos 10 caracteres");
                return;
            }
            
            ValoracionDAO dao = new ValoracionDAO();
            boolean exito;
            
            if (dao.yaValoro(cliente.getEmail(), pelicula.getId())) {
                exito = dao.actualizar(cliente.getEmail(), pelicula.getId(), slider.getValue(), comentario);
            } else {
                exito = dao.insertar(cliente.getEmail(), pelicula.getId(), slider.getValue(), comentario);
            }
            
            if (exito) {
                JOptionPane.showMessageDialog(dialogo, "¡Reseña enviada!");
                dialogo.dispose();
                cargarSesiones("");
            } else {
                JOptionPane.showMessageDialog(dialogo, "Error al enviar");
            }
        });
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dialogo.dispose());
        
        panelBotones.add(btnEnviar);
        panelBotones.add(btnCancelar);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        
        dialogo.setVisible(true);
    }
}