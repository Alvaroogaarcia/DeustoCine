package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import dao.DescuentoDAO;
import domain.DescuentoPelicula;

public class Descuento extends JFrame{

	private JComboBox<String> cmbGenero;
    private JTextField txtPorcentaje;

    public Descuento() {

    	// Configuración de la ventana
    	setTitle("Crear Descuento");
        setSize(350, 220);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ---- Género ----
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Género:"), gbc);

        cmbGenero = new JComboBox<>(new String[]{
                "Accion", "Comedia", "Drama", "Terror",
                "Ciencia Ficcion", "Romance", "Animacion",
                "Thriller", "Otros"
        });

        gbc.gridx = 1;
        add(cmbGenero, gbc);

        // ---- Porcentaje ----
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Porcentaje (%):"), gbc);

        txtPorcentaje = new JTextField();
        gbc.gridx = 1;
        add(txtPorcentaje, gbc);

        // ---- Botón ----
        JButton btnCrear = new JButton("Crear Descuento");
        gbc.gridx = 1; gbc.gridy = 2;
        add(btnCrear, gbc);

        btnCrear.addActionListener(e -> crearDescuento());
        
    }

    private void crearDescuento() {

        String genero = (String) cmbGenero.getSelectedItem();
        String porcentajeStr = txtPorcentaje.getText().trim();

        if (porcentajeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un porcentaje");
            return;
        }

        double porcentaje;
        try {
            porcentaje = Double.parseDouble(porcentajeStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Porcentaje inválido");
            return;
        }

        if (porcentaje <= 0 || porcentaje > 100) {
            JOptionPane.showMessageDialog(this, "El porcentaje debe ser entre 1 y 100");
            return;
        }

        // Generar el código automáticamente
        String codigo = generarCodigo(genero);

        DescuentoPelicula descuento = new DescuentoPelicula(codigo, porcentaje);

        DescuentoDAO dao = new DescuentoDAO();

        if (dao.insertar(descuento)) {
            JOptionPane.showMessageDialog(this,
                    "Descuento creado!\nCódigo generado: " + codigo);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error: ya existe un descuento con ese código",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String generarCodigo(String genero) {
        char letra;

        switch (genero.toLowerCase()) {
            case "accion": letra = 'A'; break;
            case "comedia": letra = 'C'; break;
            case "drama": letra = 'D'; break;
            case "terror": letra = 'T'; break;
            case "ciencia ficcion": letra = 'S'; break;
            case "romance": letra = 'R'; break;
            case "animacion": letra = 'N'; break;
            case "thriller": letra = 'H'; break;
            default: letra = 'X';
        }

        Random random = new Random();
        int numero = 1000 + random.nextInt(9000);

        return letra + String.valueOf(numero);
    }

    public static void main(String[] args) {
        new Descuento().setVisible(true);
    }
}