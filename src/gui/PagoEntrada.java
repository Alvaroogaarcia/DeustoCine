package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class PagoEntrada extends JFrame{

    private static final long serialVersionUID = 1L;

    private JLabel lblPrecioBase;
    private JLabel lblPrecioFinal;
    private JLabel lblEstado;
    private JButton btnRuleta;
    private JButton btnPagar;

    // Panel y casillas de la "ruleta"
    private JPanel panelRuleta;
    private JLabel[] casillas;

    private double precioBase;
    private boolean ruletaUsada = false;

    // Descuentos posibles
    private final int[] DESCUENTOS = {0, 5, 10, 15, 20, 25, 30, 40};

    public PagoEntrada() {
        this(8.50); // precio base por defecto
    }

    public PagoEntrada(double precioBase) {
        this.precioBase = precioBase;
        inicializarVentana();
    }

    private void inicializarVentana() {
        setTitle("Deusto Cine - Pago");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

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

        // --------- RULETA VISUAL ----------
        panelRuleta = new JPanel(); // por defecto FlowLayout, vale para poner las casillas en fila
        casillas = new JLabel[DESCUENTOS.length];

        for (int i = 0; i < DESCUENTOS.length; i++) {
            JLabel lbl = new JLabel(DESCUENTOS[i] + "%");
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
        // -----------------------------------

        // Mensaje de estado
        lblEstado = new JLabel("Pulsa \"Girar ruleta\" para intentar conseguir un descuento.");
        gbc.gridy = 3;
        add(lblEstado, gbc);

        gbc.gridwidth = 1;

        // Botón ruleta
        btnRuleta = new JButton("Girar ruleta");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(btnRuleta, gbc);

        // Botón pagar
        btnPagar = new JButton("Pagar");
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(btnPagar, gbc);

        // Listeners
        btnRuleta.addActionListener(e -> lanzarRuleta());
        btnPagar.addActionListener(e -> dispose());
    }

    // Lanza el hilo que hace girar la ruleta
    private void lanzarRuleta() {
    	if (ruletaUsada) {
            lblEstado.setText("Ya has usado la ruleta para esta compra.");
            return;
        }

        ruletaUsada = true;
        btnRuleta.setEnabled(false);

        Thread hilo = new Thread(() -> {

            int indice = 0;
            int espera = 40;   // empieza rápido
            int vueltas = 40;  // número de saltos
            Random random = new Random();

            // Animación: va recorriendo casillas y se frena
            for (int i = 0; i < vueltas; i++) {
                int anterior = indice;
                indice = (indice + 1) % casillas.length;
                int paso = i;

                final int idxActual = indice;
                final int idxAnterior = anterior;

                SwingUtilities.invokeLater(() -> {
                    // desmarcar anterior
                    casillas[idxAnterior].setBackground(Color.LIGHT_GRAY);
                    // marcar actual
                    casillas[idxActual].setBackground(Color.YELLOW);
                    lblEstado.setText("Girando ruleta" + puntosRuleta(paso % 4));
                });

                try {
                    Thread.sleep(espera);
                } catch (InterruptedException e) {
                    return;
                }

                espera += 10; // cada vez más lento
            }

            // Resultado final (usamos la casilla donde se ha parado)
            int descuentoFinal = DESCUENTOS[indice];
            double precioFinal = precioBase * (1 - descuentoFinal / 100.0);

            // Copias finales para poder usarlas dentro de la lambda
            final int indiceFinal = indice;
            final int descuentoFinalF = descuentoFinal;
            final double precioFinalF = precioFinal;

            SwingUtilities.invokeLater(() -> {
                // aseguramos que solo la casilla final queda marcada
                for (int i = 0; i < casillas.length; i++) {
                    casillas[i].setBackground(i == indiceFinal ? Color.GREEN : Color.LIGHT_GRAY);
                }

                lblPrecioFinal.setText("Precio final: " + String.format("%.2f €", precioFinalF));

                if (descuentoFinalF == 0) {
                    lblEstado.setText("No ha habido suerte, sin descuento esta vez.");
                } else {
                    lblEstado.setText("¡Enhorabuena! Descuento aplicado: " + descuentoFinalF + "%");
                }
            });
        });

        hilo.start();
    }
    // Para el texto "Girando ruleta..."
    private String puntosRuleta(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append('.');
        }
        return sb.toString();
    }

    // main de prueba independiente
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PagoEntrada().setVisible(true));
    }
}