package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import dao.DescuentoDAO;
import domain.DescuentoPelicula;


public class PagoEntrada extends JFrame{

    private static final long serialVersionUID = 1L;

    private JLabel lblPrecioBase;
    private JLabel lblPrecioFinal;
    private JLabel lblEstado;
    private JButton btnRuleta;
    private JButton btnPagar;

    private JPanel panelRuleta;
    private JLabel[] casillas;

    private double precioBase;
    private boolean ruletaUsada = false;


    private String generoPelicula;

    // Para limitar la ruleta a una vez al día
    private static LocalDate ultimaFechaRuleta = null;


    private final String[] CASILLAS_TEXTOS = {
            "NO HAY", "DESCUENTO", "NO HAY", "NO HAY",
            "DESCUENTO", "NO HAY", "NO HAY", "NO HAY"
    };

    public PagoEntrada(String generoPelicula) {
        this(10.0, generoPelicula);
    }

    public PagoEntrada() {
        this(10.0, null);
    }

    public PagoEntrada(double precioBase, String generoPelicula) {
        this.precioBase = precioBase;
        this.generoPelicula = generoPelicula;
        inicializarVentana();
    }
    
    private boolean puedeUsarRuletaHoy() {
        LocalDate hoy = LocalDate.now();
        if (ultimaFechaRuleta == null || !ultimaFechaRuleta.equals(hoy)) {
            ultimaFechaRuleta = hoy;
            return true;
        }
        return false;
    }

    private void inicializarVentana() {
        setTitle("Deusto Cine - Pago");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
       

        // Precio base
        lblPrecioBase = new JLabel("Precio base: " + String.format("%.2f €", precioBase));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblPrecioBase, gbc);

        // Precio final
        lblPrecioFinal = new JLabel("Precio final: " + String.format("%.2f €", precioBase));
        gbc.gridy = 1;
        add(lblPrecioFinal, gbc);

        // Ruleta
        panelRuleta = new JPanel(); 
        casillas = new JLabel[CASILLAS_TEXTOS.length];

        for (int i = 0; i < CASILLAS_TEXTOS.length; i++) {
            JLabel lbl = new JLabel(CASILLAS_TEXTOS[i]);
            lbl.setOpaque(true);
            lbl.setBackground(Color.LIGHT_GRAY);
            lbl.setForeground(Color.BLACK);
            lbl.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
            casillas[i] = lbl;
            panelRuleta.add(lbl);
        }

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(panelRuleta, gbc);


        // Mensaje de estado
        lblEstado = new JLabel("Pulsa \"Girar ruleta\" para intentar conseguir un descuento.");
        gbc.gridy = 3;
        add(lblEstado, gbc);

        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;   // que los botones no se estiren
        gbc.anchor = GridBagConstraints.CENTER;

     // Botón ruleta
        btnRuleta = new JButton("Girar ruleta");
        gbc.gridx = 0;
        gbc.gridy = 4;
        Dimension tamBoton = new Dimension(130, 30);
        btnRuleta.setPreferredSize(tamBoton);
        add(btnRuleta, gbc);

        // Botón pagar
        btnPagar = new JButton("Pagar");
        gbc.gridx = 1;
        gbc.gridy = 4;
        btnPagar.setPreferredSize(tamBoton);
        add(btnPagar, gbc);


        btnRuleta.addActionListener(e -> lanzarRuleta());
        btnPagar.addActionListener(e -> dispose());
    }

    // Lanza el hilo que hace girar la ruleta
    private void lanzarRuleta() {
        if (ruletaUsada) {
            lblEstado.setText("Ya has usado la ruleta para esta compra.");
            return;
        }

        // Limitar a una vez al día
        if (!puedeUsarRuletaHoy()) {
            lblEstado.setText("Ya has usado la ruleta hoy. Vuelve mañana.");
            return;
        }

        ruletaUsada = true;
        btnRuleta.setEnabled(false);

        Thread hilo = new Thread(() -> {

            int indice = 0;
            int espera = 40;   
            int vueltas = 40;  

            // Animación
            for (int i = 0; i < vueltas; i++) {
                int anterior = indice;
                indice = (indice + 1) % casillas.length;
                int paso = i;

                final int idxActual = indice;
                final int idxAnterior = anterior;

                SwingUtilities.invokeLater(() -> {
                    casillas[idxAnterior].setBackground(Color.LIGHT_GRAY);
                    casillas[idxActual].setBackground(Color.YELLOW);
                    lblEstado.setText("Girando ruleta" + puntosRuleta(paso % 4));
                });

                try {
                    Thread.sleep(espera);
                } catch (InterruptedException e) {
                    return;
                }

                espera += 10; 
            }

            // Resultado final: casilla donde se ha parado
            String textoCasilla = CASILLAS_TEXTOS[indice];

            final int indiceFinal = indice;
            final String textoFinal = textoCasilla;

            // Pintamos solo la casilla final en verde
            SwingUtilities.invokeLater(() -> {
                for (int i = 0; i < casillas.length; i++) {
                    casillas[i].setBackground(i == indiceFinal ? Color.GREEN : Color.LIGHT_GRAY);
                }
            });

            // Si no hay descuento, solo mostramos mensaje y salimos
            if (!"DESCUENTO".equalsIgnoreCase(textoFinal)) {
                SwingUtilities.invokeLater(() -> {
                    lblEstado.setText("No ha habido suerte, sin descuento esta vez.");
                });
                return;
            }

            // Si toca DESCUENTO: pedimos un descuento aleatorio de ese género a la BD
            DescuentoDAO dao = new DescuentoDAO();
            DescuentoPelicula descuento = dao.obtenerDescuentoAleatorioPorGenero(generoPelicula);

            if (descuento == null) {
                SwingUtilities.invokeLater(() -> {
                    lblEstado.setText("No hay descuentos disponibles para este género.");
                });
                return;
            }

            double porcentaje = descuento.getPorcentaje();
            double precioFinal = precioBase * (1 - porcentaje / 100.0);

            final double precioFinalF = precioFinal;
            final double porcentajeF = porcentaje;
            final String codigoF = descuento.getCodigo();

            SwingUtilities.invokeLater(() -> {
                lblPrecioFinal.setText("Precio final: " + String.format("%.2f €", precioFinalF));
                lblEstado.setText("¡Descuento aplicado (" + porcentajeF + "%, código " + codigoF + ")!");
            });
        });

        hilo.start();
    }

    private String puntosRuleta(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append('.');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PagoEntrada().setVisible(true));
    }
}