package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import dao.DescuentoDAO;
import domain.DescuentoPelicula;

public class Descuento extends JFrame{

	private JTextField txtId;
    private JTextField txtPorcentaje;

    public Descuento() {

        // Window configuration
        setTitle("Deusto Cine - Create Discount");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // UI font styling
        Font fuente = new Font("Segoe UI", Font.PLAIN, 14);
        UIManager.put("Label.font", fuente);
        UIManager.put("Button.font", fuente);
        UIManager.put("TextField.font", fuente);

        // Main container panel
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        setContentPane(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // --- Discount ID ---
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Discount ID:"), gbc);

        gbc.gridx = 1;
        txtId = new JTextField(15);
        panel.add(txtId, gbc);

        // Discount percentage
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Percentage (%):"), gbc);

        gbc.gridx = 1;
        txtPorcentaje = new JTextField(15);
        panel.add(txtPorcentaje, gbc);

        // Buttons
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;

        JButton btnCancel = new JButton("Cancel");
        JButton btnCreate = new JButton("Create");

        JPanel buttonPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 5));
        buttonPanel.add(btnCancel);
        buttonPanel.add(btnCreate);

        panel.add(buttonPanel, gbc);

        // Button actions
        btnCancel.addActionListener(e -> dispose());
        btnCreate.addActionListener(e -> createDiscount());

        // Pressing ENTER triggers "Create"
        getRootPane().setDefaultButton(btnCreate);
    }

    private void createDiscount() {
        String id = txtId.getText().trim();
        String porcentajeText = txtPorcentaje.getText().trim();

        // Basic validation
        if (id.isEmpty() || porcentajeText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        double porcentaje;
        try {
            porcentaje = Double.parseDouble(porcentajeText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Percentage must be a numeric value.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (porcentaje <= 0 || porcentaje > 100) {
            JOptionPane.showMessageDialog(this,
                    "Percentage must be between 1 and 100.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create domain object (no code field used here)
        DescuentoPelicula desc = new DescuentoPelicula(id, porcentaje);

        // Attempt to save using DAO
        DescuentoDAO dao = new DescuentoDAO();
        boolean inserted = dao.insertar(desc);

        if (!inserted) {
            JOptionPane.showMessageDialog(this,
                    "A discount with this ID already exists.",
                    "Duplicate ID",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Discount created successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

    public static void main(String[] args) {
        new Descuento().setVisible(true);
    }
}