package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import dao.ClienteDAO;
import dao.DescuentoDAO;
import domain.Cliente;
import domain.DescuentoPelicula;


public class PagoEntrada extends JFrame{

    private static final long serialVersionUID = 1L;

    private JLabel lblPrecioBase;
    private JLabel lblPrecioFinal;
    private JLabel lblEstado;
    private JButton btnRuleta;
    private JButton btnPagar;

    private JPanel panelRuleta;
    private JPanel panelbotones;
    private JPanel panelAtras;
    private JPanel panelPrecio;
    private JLabel[] casillas;
    private JPanel panelbotones1;
    private JPanel panelGirar;

    private double precioBase = 10.0;
    private boolean ruletaUsada = false;
    private Cliente cliente;
    private double precioFinalActual;
    



    private String generoPelicula;
    private int idPelicula;


    // Para limitar la ruleta a una vez al día
    private static LocalDate ultimaFechaRuleta = null;


    private final String[] CASILLAS_TEXTOS = {
            "DESCUENTO", "DESCUENTO", "DESCUENTO", "NO HAY",
            "DESCUENTO", "DESCUENTO", "NO HAY", "DESCUENTO"
    };

    
    public PagoEntrada(Cliente cliente, int idPelicula, String generoPelicula) {
        if (cliente == null) {
            JOptionPane.showMessageDialog(null, "Error: debe iniciar sesión antes de comprar entradas");
            throw new IllegalArgumentException("Cliente no puede ser null");
        }
        this.cliente = cliente;
        this.idPelicula = idPelicula;         
        this.generoPelicula = generoPelicula;
        this.precioFinalActual = precioBase;
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
        
        
        panelAtras = new JPanel();
        panelbotones = new JPanel();
        panelPrecio = new JPanel();
        panelRuleta = new JPanel();
        panelbotones1 = new JPanel();
        panelGirar = new JPanel();
        
        this.add(panelAtras);
        panelAtras.setLayout(new BorderLayout());
        panelAtras.add(panelRuleta, BorderLayout.NORTH);
        panelAtras.add(panelPrecio, BorderLayout.CENTER);
        panelAtras.add(panelbotones, BorderLayout.SOUTH);
        
        panelbotones.setLayout(new BorderLayout());
        panelRuleta.setLayout(new FlowLayout());
        panelPrecio.setLayout(new FlowLayout());
        panelbotones1.setLayout(new FlowLayout());
        panelGirar.setLayout(new FlowLayout());
        
        panelbotones.add(panelbotones1, BorderLayout.CENTER);
        panelbotones.add(panelGirar, BorderLayout.NORTH);
        
        
        
        
       

        // Precio base
        lblPrecioBase = new JLabel("Precio base: " + String.format("%.2f €", precioBase));
        panelPrecio.add(lblPrecioBase);

        // Precio final
        lblPrecioFinal = new JLabel("Precio final: " + String.format("%.2f €", precioBase));
        
        panelPrecio.add(lblPrecioFinal);

        // Ruleta
        
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

        
        


        // Mensaje de estado
        lblEstado = new JLabel("Gira la ruleta conseguir tu descuento.");
        
        panelGirar.add(lblEstado);

     // Botón ruleta
        btnRuleta = new JButton("Girar ruleta");
        
        //Dimension tamBoton = new Dimension(50, 70);
        //btnRuleta.setPreferredSize(tamBoton);
        panelbotones1.add(btnRuleta);
        
        


        // Botón pagar
        btnPagar = new JButton("Pagar");
        //btnPagar.setPreferredSize(tamBoton);
        panelbotones1.add(btnPagar);


        btnRuleta.addActionListener(e -> lanzarRuleta());
        btnPagar.addActionListener(e -> procesarPago());

    }

    // Lanza el hilo que hace girar la ruleta
    private void lanzarRuleta() {
        if (ruletaUsada) {
            lblEstado.setText("Ya has usado la ruleta para esta compra.");
            return;
        }

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

                try { Thread.sleep(espera); } catch (InterruptedException e) {}
                espera += 10;
            }

            String textoCasilla = CASILLAS_TEXTOS[indice];
            final int indiceFinal = indice;
            final String textoFinal = textoCasilla;

            SwingUtilities.invokeLater(() -> {
                for (int i = 0; i < casillas.length; i++) {
                    casillas[i].setBackground(i == indiceFinal ? Color.GREEN : Color.LIGHT_GRAY);
                }
            });

            if (!"DESCUENTO".equalsIgnoreCase(textoFinal)) {
                SwingUtilities.invokeLater(() -> lblEstado.setText("No ha habido suerte, sin descuento esta vez."));
                return;
            }

            DescuentoDAO dao = new DescuentoDAO();
            DescuentoPelicula descuento = dao.obtenerDescuentoAleatorioPorGenero(generoPelicula);
            if (descuento == null) {
                SwingUtilities.invokeLater(() -> lblEstado.setText("No hay descuentos disponibles para este género."));
                return;
            }

            double porcentaje = descuento.getPorcentaje();
            this.precioFinalActual = precioBase * (1 - porcentaje / 100.0);

            SwingUtilities.invokeLater(() -> {
                lblPrecioFinal.setText("Precio final: " + String.format("%.2f €", precioFinalActual));
                lblEstado.setText("¡Descuento aplicado (" + porcentaje + "%, código " + descuento.getCodigo() + ")!");
            });
        });

        hilo.start();
    }
    
    private void procesarPago() {
        if (cliente.getSaldo() < precioFinalActual) {
            JOptionPane.showMessageDialog(this,
                    "Saldo insuficiente.\nSaldo actual: " + String.format("%.2f €", cliente.getSaldo()),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(this,
                "Precio final: " + String.format("%.2f €", precioFinalActual) +
                        "\nSaldo actual: " + String.format("%.2f €", cliente.getSaldo()) +
                        "\n¿Confirmar pago?",
                "Confirmar pago", JOptionPane.YES_NO_OPTION);

        if (opcion != JOptionPane.YES_OPTION) return;

        // Actualizar saldo
        cliente.setSaldo(cliente.getSaldo() - precioFinalActual);

        // Guardar película comprada
        cliente.agregarCompra(idPelicula);

        // Actualizar en BD
        new ClienteDAO().actualizarSaldoYCompras(cliente);

        JOptionPane.showMessageDialog(this, "Pago realizado con éxito 🎉");
        dispose();
    }



    private String puntosRuleta(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append('.');
        }
        return sb.toString();
    }
    
   
}

    