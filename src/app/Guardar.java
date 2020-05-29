package app;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.*;

public class Guardar extends JDialog implements ActionListener {
    private JLabel lblNombre;
    private JTextField txfNombre;
    private JButton btnOk;
    private JButton btnCancelar;
    private String home = System.getProperty("user.home");

    public Guardar(Loteria l) {
        super(l);
        setLayout(new FlowLayout());
        setTitle("Guardar partida");

        lblNombre = new JLabel("Nome");
        add(lblNombre);

        txfNombre = new JTextField(20);
        txfNombre.addActionListener(this);
        add(txfNombre);

        btnOk = new JButton("OK");
        btnOk.addActionListener(this);
        add(btnOk);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        add(btnCancelar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnOk || e.getSource() == txfNombre) {
            if (txfNombre.getText().trim().length() > 0) {
                Loteria l = (Loteria) this.getOwner();
                int esp = 30 - txfNombre.getText().length();

                try (PrintWriter f = new PrintWriter(new FileWriter(home + "/.records.txt", true))) {
                    f.printf("%s%" + esp + "s", txfNombre.getText(), "Nº ");
                    for (int i = 0; i < l.aciertos.size(); i++) {
                        if (i < l.aciertos.size() - 1) {
                            f.print(l.aciertos.get(i) + ", ");
                        } else {
                            f.print(l.aciertos.get(i));
                        }
                    }
                    esp = 25 - l.aciertos.toString().length();
                    f.printf("%" + esp + "s Total: %d\n", " ", l.aciertos.size());
                } catch (IOException excep) {
                    JOptionPane.showMessageDialog(null, "Non se pode gardar o archivo", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Non introduciches ningún nome", "Información",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        }

        if (e.getSource() == btnCancelar) {
            dispose();
        }
    }

}